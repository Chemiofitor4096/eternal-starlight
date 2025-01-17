package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage;

import cn.leolezury.eternalstarlight.common.registry.ESTreePlacers;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.function.BiFunction;

public class SpheroidFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<SpheroidFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance).apply(instance, SpheroidFoliagePlacer::new));

	public static final BiFunction<LevelSimulatedReader, BlockPos, Boolean> VALID_TREE_POS = TreeFeature::validTreePos;

	public SpheroidFoliagePlacer(IntProvider horizontalRadius, IntProvider yOffset) {
		super(horizontalRadius, yOffset);
	}

	@Override
	protected FoliagePlacerType<SpheroidFoliagePlacer> type() {
		return ESTreePlacers.FOLIAGE_SPHEROID.get();
	}

	public static void placeFoliage(LevelSimulatedReader level, FoliageSetter setter, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, BlockPos pos, BlockStateProvider config, RandomSource random) {
		if (predicate.apply(level, pos)) {
			setter.set(pos, config.getState(random, pos));
		}
		for (Direction direction : Direction.values()) {
			if (random.nextInt(5) == 0) {
				if (predicate.apply(level, pos.relative(direction))) {
					setter.set(pos.relative(direction), config.getState(random, pos.relative(direction)));
				}
			}
		}
	}

	public static void placeSpheroidFoliage(LevelSimulatedReader level, FoliageSetter setter, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, RandomSource random, BlockPos centerPos, float xzRadius, float yRadius, BlockStateProvider provider) {
		for (int x = 0; x <= xzRadius; x++) {
			for (int z = 0; z <= xzRadius; z++) {
				for (int y = 0; y <= yRadius; y++) {
					if (ESMathUtil.isPointInEllipsoid(x, y, z, xzRadius, yRadius, xzRadius)) {
						placeFoliage(level, setter, predicate, centerPos.offset(x, y, z), provider, random);
						placeFoliage(level, setter, predicate, centerPos.offset(x, -y, z), provider, random);
						placeFoliage(level, setter, predicate, centerPos.offset(-x, y, z), provider, random);
						placeFoliage(level, setter, predicate, centerPos.offset(-x, -y, z), provider, random);
						placeFoliage(level, setter, predicate, centerPos.offset(x, y, -z), provider, random);
						placeFoliage(level, setter, predicate, centerPos.offset(x, -y, -z), provider, random);
						placeFoliage(level, setter, predicate, centerPos.offset(-x, y, -z), provider, random);
						placeFoliage(level, setter, predicate, centerPos.offset(-x, -y, -z), provider, random);
					}
				}
			}
		}
	}

	@Override
	protected void createFoliage(LevelSimulatedReader levelReader, FoliageSetter setter, RandomSource random, TreeConfiguration baseTreeFeatureConfig, int trunkHeight, FoliageAttachment foliage, int foliageHeight, int radius, int offset) {
		BlockPos center = foliage.pos().above(offset);
		placeSpheroidFoliage(levelReader, setter, VALID_TREE_POS, random, center, foliage.radiusOffset() + this.radius.sample(random), foliage.radiusOffset() + 1.5F + random.nextInt(2), baseTreeFeatureConfig.foliageProvider);
	}

	@Override
	public int foliageHeight(RandomSource random, int i, TreeConfiguration treeConfiguration) {
		return 0;
	}

	@Override
	protected boolean shouldSkipLocation(RandomSource random, int i0, int i1, int i2, int i3, boolean bool) {
		return false;
	}
}


