package cn.leolezury.eternalstarlight.common.block.entity.spawner;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class StarlightGolemSpawnerBlockEntity extends BossSpawnerBlockEntity<StarlightGolem> {
	public StarlightGolemSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.STARLIGHT_GOLEM_SPAWNER.get(), ESEntities.STARLIGHT_GOLEM.get(), pos, state);
	}

	@Override
	protected boolean spawnBoss(ServerLevelAccessor accessor) {
		if (!ESConfig.INSTANCE.mobsConfig.starlightGolem.canSpawn()) {
			return false;
		}
		return super.spawnBoss(accessor);
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return ESParticles.ENERGY.get();
	}
}
