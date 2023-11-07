package cn.leolezury.eternalstarlight.common.world.gen.system.provider;

import cn.leolezury.eternalstarlight.common.world.gen.system.area.WorldArea;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.BiomeTransformers;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.HeightTransformers;

public class ESWorldGenProvider extends AbstractWorldGenProvider {
    public ESWorldGenProvider(int maxHeight, int minHeight) {
        super(maxHeight, minHeight);
    }

    @Override
    int[] getLandBiomes() {
        return new int[]{BiomeDataRegistry.STARLIGHT_FOREST, BiomeDataRegistry.STARLIGHT_DENSE_FOREST, BiomeDataRegistry.DARK_SWAMP, BiomeDataRegistry.STARLIGHT_PERMAFROST_FOREST};
    }

    @Override
    int[] getOceanBiomes() {
        return new int[]{BiomeDataRegistry.STARLIT_SEA};
    }

    @Override
    void doBiomesTransformation(WorldArea area) {
        area.transformBiomes(BiomeTransformers.ADD_OCEAN, 0);
        area.transformBiomes(BiomeTransformers.APPLY_BIOMES, 0);
        area.transformBiomes(BiomeTransformers.DUPLICATE, 0);
        scaleAndProcessEdges(area, 0);
        scaleAndProcessEdges(area, 1);
        scaleAndProcessEdges(area, 2);
        scaleAndProcessEdges(area, 3);
        scaleAndProcessEdges(area, 4);
        scaleAndProcessEdges(area, 5);
        area.transformBiomes(BiomeTransformers.ASSIMILATE_LONELY, 0);
        area.transformBiomes(BiomeTransformers.ADD_BEACHES, 0);
        area.transformBiomes(BiomeTransformers.ADD_RIVER, 0);
        for (int i = 0; i < 2; i++) {
            area.transformBiomes(BiomeTransformers.ADD_RIVER_TRANSITION, 0);
        }
        area.transformBiomes(BiomeTransformers.DUPLICATE, 0);
        area.transformBiomes(BiomeTransformers.DUPLICATE, 0);
    }

    @Override
    void doHeightsTransformation(WorldArea area) {
        area.transformHeights(HeightTransformers.SMOOTH_LARGE, 0);
        area.transformHeights(HeightTransformers.NOISE, 0);
        area.transformHeights(HeightTransformers.SMOOTH_SMALL, 0);
    }
}