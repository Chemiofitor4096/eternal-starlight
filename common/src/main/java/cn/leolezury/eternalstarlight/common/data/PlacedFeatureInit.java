package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeatureInit {
    public static final ResourceKey<PlacedFeature> STONE_SPIKE = create("stone_spike");
    public static final ResourceKey<PlacedFeature> STONE_ORE = create("stone_ore");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_ORE = create("deepslate_ore");
    public static final ResourceKey<PlacedFeature> GLOWING_VOIDSTONE_ORE = create("glowing_voidstone_ore");
    public static final ResourceKey<PlacedFeature> GLOWING_NIGHTSHADE_MUD_ORE = create("glowing_nightshade_mud_ore");
    public static final ResourceKey<PlacedFeature> NIGHTSHADE_DIRT_ORE = create("nightshade_dirt_ore");
    public static final ResourceKey<PlacedFeature> FALLEN_LUNAR_LOG = create("fallen_lunar_log");
    public static final ResourceKey<PlacedFeature> FALLEN_NORTHLAND_LOG = create("fallen_northland_log");
    public static final ResourceKey<PlacedFeature> FALLEN_STARLIGHT_MANGROVE_LOG = create("fallen_starlight_mangrove_log");
    public static final ResourceKey<PlacedFeature> STARLIGHT_CRYSTAL = create("starlight_crystal");
    public static final ResourceKey<PlacedFeature> CAVE_VINE = create("cave_vine");
    public static final ResourceKey<PlacedFeature> LUNAR_TREE_CHECKED = create("lunar_tree_checked");
    public static final ResourceKey<PlacedFeature> LUNAR_HUGE_TREE_CHECKED = create("lunar_huge_tree_checked");
    public static final ResourceKey<PlacedFeature> NORTHLAND_TREE_CHECKED = create("northland_tree_checked");
    public static final ResourceKey<PlacedFeature> STARLIGHT_MANGROVE_TREE_CHECKED = create("starlight_mangrove_tree_checked");
    public static final ResourceKey<PlacedFeature> HUGE_GLOWING_MUSHROOM_CHECKED = create("huge_glowing_mushroom_checked");
    public static final ResourceKey<PlacedFeature> NORTHLAND_ON_SNOW = create("northland_on_snow");
    public static final ResourceKey<PlacedFeature> SL_FOREST = create("sl_forest");
    public static final ResourceKey<PlacedFeature> SL_DENSE_FOREST = create("sl_dense_forest");
    public static final ResourceKey<PlacedFeature> SL_SWAMP_FOREST = create("sl_swamp_forest");
    public static final ResourceKey<PlacedFeature> SL_PERMAFROST_FOREST = create("sl_permafrost_forest");
    public static final ResourceKey<PlacedFeature> STARLIGHT_FLOWER = create("starlight_flower");
    public static final ResourceKey<PlacedFeature> SL_GRASS = create("sl_grass");
    public static final ResourceKey<PlacedFeature> SWAMP_GRASS = create("swamp_grass");
    public static final ResourceKey<PlacedFeature> SWAMP_LAKE = create("swamp_lake");
    public static final ResourceKey<PlacedFeature> SWAMP_WATER = create("swamp_water");
    public static final ResourceKey<PlacedFeature> CAVE_SPRING = create("cave_spring");
    public static final ResourceKey<PlacedFeature> SWAMP_SILVER_ORE = create("swamp_silver_ore");

    //structure features
    public static final ResourceKey<PlacedFeature> CURSED_GARDEN_EXTRA_HEIGHT = create("cursed_garden_extra_height");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        BlockPredicate snowPredicate = BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW);
        List<PlacementModifier> onSnow = List.of(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8), BlockPredicateFilter.forPredicate(snowPredicate));

        register(context, STONE_SPIKE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STONE_SPIKE), RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, STONE_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STONE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));
        register(context, DEEPSLATE_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.DEEPSLATE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
        register(context, GLOWING_VOIDSTONE_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.GLOWING_VOIDSTONE_ORE), commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(-64))));
        register(context, GLOWING_NIGHTSHADE_MUD_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.GLOWING_NIGHTSHADE_MUD_ORE), commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(160))));
        register(context, NIGHTSHADE_DIRT_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.NIGHTSHADE_DIRT_ORE), commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(160))));
        register(context, FALLEN_LUNAR_LOG, configuredFeatures.getOrThrow(ConfiguredFeatureInit.FALLEN_LUNAR_LOG), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, FALLEN_NORTHLAND_LOG, configuredFeatures.getOrThrow(ConfiguredFeatureInit.FALLEN_NORTHLAND_LOG), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, FALLEN_STARLIGHT_MANGROVE_LOG, configuredFeatures.getOrThrow(ConfiguredFeatureInit.FALLEN_STARLIGHT_MANGROVE_LOG), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, STARLIGHT_CRYSTAL, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STARLIGHT_CRYSTAL), RarityFilter.onAverageOnceEvery(10), CountPlacement.of(188), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)));
        register(context, CAVE_VINE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.CAVE_VINE), CountPlacement.of(188), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)));
        register(context, LUNAR_TREE_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.LUNAR), PlacementUtils.filteredByBlockSurvival(BlockInit.LUNAR_SAPLING.get()));
        register(context, LUNAR_HUGE_TREE_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.LUNAR_HUGE), PlacementUtils.filteredByBlockSurvival(BlockInit.LUNAR_SAPLING.get()));
        register(context, NORTHLAND_TREE_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.NORTHLAND), PlacementUtils.filteredByBlockSurvival(BlockInit.NORTHLAND_SAPLING.get()));
        register(context, STARLIGHT_MANGROVE_TREE_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STARLIGHT_MANGROVE), PlacementUtils.filteredByBlockSurvival(BlockInit.STARLIGHT_MANGROVE_SAPLING.get()));
        register(context, HUGE_GLOWING_MUSHROOM_CHECKED, configuredFeatures.getOrThrow(ConfiguredFeatureInit.HUGE_GLOWING_MUSHROOM), PlacementUtils.filteredByBlockSurvival(BlockInit.GLOWING_MUSHROOM.get()));
        register(context, NORTHLAND_ON_SNOW, configuredFeatures.getOrThrow(ConfiguredFeatureInit.NORTHLAND), onSnow);
        register(context, SL_FOREST, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SL_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        register(context, SL_DENSE_FOREST, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SL_DENSE_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(15, 0.1F, 2)));
        register(context, SL_PERMAFROST_FOREST, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SL_PERMAFROST_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 2)));
        register(context, SL_SWAMP_FOREST, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SL_SWAMP_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.1F, 1)));
        register(context, STARLIGHT_FLOWER, configuredFeatures.getOrThrow(ConfiguredFeatureInit.STARLIGHT_FLOWER), RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
        register(context, SL_GRASS, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SL_GRASS), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        register(context, SWAMP_GRASS, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SWAMP_GRASS), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        register(context, SWAMP_LAKE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SWAMP_LAKE), RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        register(context, SWAMP_WATER, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SWAMP_WATER), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        register(context, CAVE_SPRING, configuredFeatures.getOrThrow(ConfiguredFeatureInit.CAVE_SPRING), RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.top())), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 32), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5));
        register(context, SWAMP_SILVER_ORE, configuredFeatures.getOrThrow(ConfiguredFeatureInit.SWAMP_SILVER_ORE), commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));

        // structure features
        register(context, CURSED_GARDEN_EXTRA_HEIGHT, configuredFeatures.getOrThrow(ConfiguredFeatureInit.CURSED_GARDEN_EXTRA_HEIGHT)); // no placement modifier
    }


    private static ResourceKey<PlacedFeature> create(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
    public static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier1) {
        return List.of(modifier, InSquarePlacement.spread(), modifier1, BiomeFilter.biome());
    }
    public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }
}