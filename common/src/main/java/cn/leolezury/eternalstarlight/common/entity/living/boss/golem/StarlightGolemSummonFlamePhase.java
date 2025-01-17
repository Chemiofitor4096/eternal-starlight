package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.server.level.ServerLevel;

public class StarlightGolemSummonFlamePhase extends BehaviorPhase<StarlightGolem> {
	public static final int ID = 2;

	public StarlightGolemSummonFlamePhase() {
		super(ID, 2, 210, 250);
	}

	@Override
	public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null;
	}

	@Override
	public void onStart(StarlightGolem entity) {

	}

	@Override
	public void tick(StarlightGolem entity) {
		if (entity.getBehaviorTicks() % 30 == 0) {
			if (entity.level() instanceof ServerLevel serverLevel) {
				ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 45, 50, 0.24f, 0.24f, 4.5f, 5).send(serverLevel);
			}
			entity.spawnEnergizedFlame(2, 15, true);
		}
	}

	@Override
	public boolean canContinue(StarlightGolem entity) {
		return true;
	}

	@Override
	public void onStop(StarlightGolem entity) {

	}
}
