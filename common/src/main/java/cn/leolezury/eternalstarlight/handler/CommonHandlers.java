package cn.leolezury.eternalstarlight.handler;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.block.SLPortalBlock;
import cn.leolezury.eternalstarlight.datagen.DimensionInit;
import cn.leolezury.eternalstarlight.entity.misc.AetherSentMeteor;
import cn.leolezury.eternalstarlight.init.BlockInit;
import cn.leolezury.eternalstarlight.init.EnchantmentInit;
import cn.leolezury.eternalstarlight.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.manager.book.BookManager;
import cn.leolezury.eternalstarlight.manager.book.chapter.ChapterManager;
import cn.leolezury.eternalstarlight.manager.gatekeeper.TheGatekeeperNameManager;
import cn.leolezury.eternalstarlight.util.ESUtil;
import cn.leolezury.eternalstarlight.util.SLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CommonHandlers {
    private static TheGatekeeperNameManager gatekeeperNameManager;
    private static BookManager bookManager;
    private static ChapterManager chapterManager;

    public static String getGatekeeperName() {
        return gatekeeperNameManager.getTheGatekeeperName();
    }
    public static BookManager getBookManager() {
        return bookManager;
    }
    public static ChapterManager getChapterManager() {
        return chapterManager;
    }

    public static void onRightClickBlock(Level level, Player player, InteractionHand hand, BlockPos pos) {
        if (!level.isClientSide && player.getItemInHand(hand).is(Items.GLOWSTONE_DUST) && level.getBlockState(pos).is(SLTags.Blocks.PORTAL_FRAME_BLOCKS)) {
            if (level.dimension() == DimensionInit.STARLIGHT_KEY || level.dimension() == Level.OVERWORLD) {
                for (Direction direction : Direction.Plane.VERTICAL) {
                    BlockPos framePos = pos.relative(direction);
                    if (((SLPortalBlock) BlockInit.STARLIGHT_PORTAL.get()).trySpawnPortal(level, framePos)) {
                        level.playSound(player, framePos, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0F, 1.0F);
                        player.swing(hand);
                    }
                }
            }
        }
    }

    public static float onLivingHurt(LivingEntity entity, DamageSource source, float amount) {
        if (!entity.level().isClientSide) {
            int poisoningLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.POISONING.get(), entity);
            if (poisoningLevel > 0 && source.getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * poisoningLevel, poisoningLevel - 1));
            }
        }
        if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThermalSpringStoneArmorItem
                || entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ThermalSpringStoneArmorItem
                || entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ThermalSpringStoneArmorItem
                || entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ThermalSpringStoneArmorItem
        ) {
            if (source.getDirectEntity() instanceof LivingEntity livingEntity) {
                livingEntity.setSecondsOnFire(10);
            }
            if (source.is(DamageTypeTags.IS_FIRE)) {
                return amount / 2f;
            }
        }

        if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getItemInHand(InteractionHand.MAIN_HAND).is(SLTags.Items.THERMAL_SPRINGSTONE_WEAPONS)) {
            entity.setSecondsOnFire(10);
        }


        if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof AethersentArmorItem
                && entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof AethersentArmorItem
                && entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof AethersentArmorItem
                && entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof AethersentArmorItem
        ) {
            if (source.getEntity() instanceof LivingEntity livingEntity && livingEntity.level() instanceof ServerLevel serverLevel) {
                Vec3 location = livingEntity.position();
                AetherSentMeteor.createMeteorShower(serverLevel, entity, livingEntity, location.x, location.y, location.z, 200, true);
            }
        }

        return amount;
    }

    public static void onLivingTick(LivingEntity livingEntity) {
        if (livingEntity.tickCount % 20 == 0) {
            int coolDown = ESUtil.getPersistentData(livingEntity).getInt("MeteorCoolDown");
            if (coolDown > 0) {
                ESUtil.getPersistentData(livingEntity).putInt("MeteorCoolDown", coolDown - 1);
            }
        }
    }

    /*@SubscribeEvent
    public static void onAddMobEffect(MobEffectEvent.Applicable event) {
        if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SwampSilverArmorItem
                && entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SwampSilverArmorItem
                && entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SwampSilverArmorItem
                && entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SwampSilverArmorItem
        ) {
            if (!event.getEffectInstance().getEffect().isBeneficial()) {
                event.setResult(Event.Result.DENY);
            }
        }
    }*/

    public static void onArrowHit(Projectile projectile, HitResult result) {
        if (ESUtil.getPersistentData(projectile).contains(EternalStarlight.MOD_ID + ":starfall") && projectile.level() instanceof ServerLevel serverLevel) {
            Vec3 location = result.getLocation();
            AetherSentMeteor.createMeteorShower(serverLevel, projectile.getOwner() instanceof LivingEntity livingEntity ? livingEntity : null, result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity ? livingEntity : null, location.x, location.y, location.z, 200, false);
        }
    }

    public interface AddReloadListenerStrategy {
        void add(PreparableReloadListener listener);
    }

    public static void onAddReloadListener(AddReloadListenerStrategy strategy) {
        gatekeeperNameManager = new TheGatekeeperNameManager();
        bookManager = new BookManager();
        chapterManager = new ChapterManager();
        strategy.add(gatekeeperNameManager);
        strategy.add(bookManager);
        strategy.add(chapterManager);
    }
}