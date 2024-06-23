package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MeleeAttackPhase;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviourUser;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Tangled extends Monster implements MultiBehaviourUser {
    private static final int MELEE_ID = 1;

    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState meleeAnimationState = new AnimationState();

    protected static final EntityDataAccessor<Integer> BEHAVIOUR_STATE = SynchedEntityData.defineId(Tangled.class, EntityDataSerializers.INT);
    public int getBehaviourState() {
        return entityData.get(BEHAVIOUR_STATE);
    }
    public void setBehaviourState(int attackState) {
        entityData.set(BEHAVIOUR_STATE, attackState);
    }

    protected static final EntityDataAccessor<Integer> BEHAVIOUR_TICKS = SynchedEntityData.defineId(Tangled.class, EntityDataSerializers.INT);
    public int getBehaviourTicks() {
        return entityData.get(BEHAVIOUR_TICKS);
    }
    public void setBehaviourTicks(int behaviourTicks) {
        entityData.set(BEHAVIOUR_TICKS, behaviourTicks);
    }

    private final BehaviourManager<Tangled> behaviourManager = new BehaviourManager<>(this, List.of(
            new MeleeAttackPhase<Tangled>(MELEE_ID, 1, 20, 10).with(2, 15)
    ));

    public Tangled(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BEHAVIOUR_STATE, 0)
                .define(BEHAVIOUR_TICKS, 0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false) {
            @Override
            protected void checkAndPerformAttack(LivingEntity livingEntity) {

            }
        });
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (getTarget() != null && !getTarget().isAlive()) {
            setTarget(null);
        }
        if (!isNoAi()) {
            this.behaviourManager.tick();
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag && entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
        }
        return flag;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            idleAnimationState.startIfStopped(tickCount);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(BEHAVIOUR_STATE) && getBehaviourState() != 0) {
            if (getBehaviourState() == MELEE_ID) {
                meleeAnimationState.start(tickCount);
            } else {
                meleeAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void tickDeath() {
        if (!level().isClientSide && this.deathTime == 0 && getRandom().nextBoolean()) {
            TangledSkull skull = new TangledSkull(ESEntities.TANGLED_SKULL.get(), level());
            skull.setPos(getX(), getY(0.75), getZ());
            skull.setTarget(getTarget());
            skull.setLastHurtByMob(getTarget());
            level().addFreshEntity(skull);
        }
        ++this.deathTime;
        if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte)60);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
        super.dropCustomDeathLoot(serverLevel, damageSource, bl);
        Entity entity = damageSource.getEntity();
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                ItemStack itemStack = ESItems.TANGLED_SKULL.get().getDefaultInstance();
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(itemStack);
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }
}