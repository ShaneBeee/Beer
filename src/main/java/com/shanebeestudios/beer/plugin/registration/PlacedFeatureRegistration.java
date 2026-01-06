package com.shanebeestudios.beer.plugin.registration;

import com.shanebeestudios.beer.api.registration.PlacedFeatureDefinition;
import com.shanebeestudios.beer.api.utils.DumpRegistry;
import com.shanebeestudios.beer.api.utils.RegistryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.MatchingBlockTagPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider.Rule;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class PlacedFeatureRegistration {

    private static final List<PlacedFeatureDefinition> FEATURES = new ArrayList<>();

    public static void registerFeatures() {
        FEATURES.addAll(decor());
        FEATURES.addAll(delta());
        FEATURES.addAll(terrain());
        FEATURES.addAll(tree());
        FEATURES.addAll(vegetation());
    }

    public static void dumpToDatapack() {
        // Dump features to datapack files
        DumpRegistry.dumpDefinables(FEATURES);
    }

    private static List<PlacedFeatureDefinition> decor() {
        List<PlacedFeatureDefinition> features = new ArrayList<>();
        PlacedFeatureDefinition hanging_fence = PlacedFeatureDefinition.builder(PlacedFeatures.DECOR_HANGING_FENCE)
            .configuredFeature(Feature.BLOCK_COLUMN, new BlockColumnConfiguration(
                List.of(BlockColumnConfiguration.layer(
                        new WeightedListInt(WeightedList.<IntProvider>builder()
                            .add(UniformInt.of(1, 10), 2)
                            .add(UniformInt.of(1, 2), 3)
                            .add(UniformInt.of(1, 6), 10)
                            .build()),
                        WeightedStateProvider.simple(Blocks.DARK_OAK_FENCE)),
                    BlockColumnConfiguration.layer(ConstantInt.of(1),
                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                            .add(Blocks.SOUL_LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true), 1)
                            .add(Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true), 4)
                            .build()))),
                Direction.DOWN,
                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                true))
            .placementModifiers(CountPlacement.of(100),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(256)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(-1)),
                BiomeFilter.biome())
            .build();

        hanging_fence.register();
        features.add(hanging_fence);

        return features;
    }

    private static List<PlacedFeatureDefinition> delta() {
        List<PlacedFeatureDefinition> features = new ArrayList<>();

        PlacedFeatureDefinition seaPickle = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                BlockStateProvider.simple(Blocks.SEA_PICKLE.defaultBlockState().setValue(BlockStateProperties.PICKLES, 3))))
            .build();

        PlacedFeatureDefinition sandstoneWall = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.BLOCK_COLUMN, new BlockColumnConfiguration(
                List.of(BlockColumnConfiguration.layer(
                    new WeightedListInt(WeightedList.<IntProvider>builder()
                        .add(UniformInt.of(0, 19), 2)
                        .add(UniformInt.of(0, 2), 3)
                        .add(UniformInt.of(0, 6), 10)
                        .build()),
                    WeightedStateProvider.simple(Blocks.SANDSTONE_WALL))),
                Direction.UP,
                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                true))
            .placementModifiers(CountPlacement.of(1))
            .build();

        PlacedFeatureDefinition vegetationFeature = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                List.of(new WeightedPlacedFeature(seaPickle.getFeatureHolder(), 0.05f),
                    new WeightedPlacedFeature(sandstoneWall.getFeatureHolder(), 0.8f)),
                sandstoneWall.getFeatureHolder()))
            .placementModifiers(CountPlacement.of(1))
            .build();

        PlacedFeatureDefinition beach_delta = PlacedFeatureDefinition.builder(PlacedFeatures.DELTA_BEACH_DELTA)
            .configuredFeature(Feature.DELTA_FEATURE, new DeltaFeatureConfiguration(
                Blocks.WATER.defaultBlockState(),
                Blocks.DIORITE.defaultBlockState(),
                UniformInt.of(3, 7),
                UniformInt.of(0, 2)))
            .placementModifiers(CountPlacement.of(128),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(0)),
                BiomeFilter.biome())
            .build();

        beach_delta.register();
        features.add(beach_delta);

        PlacedFeatureDefinition coastal_delta = PlacedFeatureDefinition.builder(PlacedFeatures.DELTA_COASTAL_DELTA)
            .configuredFeature(Feature.DELTA_FEATURE, new DeltaFeatureConfiguration(
                Blocks.WATER.defaultBlockState(),
                Blocks.MOSS_BLOCK.defaultBlockState(),
                UniformInt.of(3, 7),
                UniformInt.of(1, 2)))
            .placementModifiers(CountPlacement.of(64),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(0)),
                BiomeFilter.biome())
            .build();

        coastal_delta.register();
        features.add(coastal_delta);

        PlacedFeatureDefinition lush_desert_delta = PlacedFeatureDefinition.builder(PlacedFeatures.DELTA_LUSH_DESERT_DELTA)
            .configuredFeature(Feature.DELTA_FEATURE, new DeltaFeatureConfiguration(
                Blocks.WATER.defaultBlockState(),
                Blocks.MOSS_BLOCK.defaultBlockState(),
                UniformInt.of(3, 7),
                UniformInt.of(1, 2)))
            .placementModifiers(CountPlacement.of(20),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(0)),
                BiomeFilter.biome())
            .build();

        lush_desert_delta.register();
        features.add(lush_desert_delta);

        PlacedFeatureDefinition dry_cave_delta = PlacedFeatureDefinition.builder(PlacedFeatures.DELTA_DRY_CAVE_DELTA)
            .configuredFeature(Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(
                BlockTags.LUSH_GROUND_REPLACEABLE,
                BlockStateProvider.simple(Blocks.SANDSTONE),
                vegetationFeature.getFeatureHolder(),
                CaveSurface.FLOOR,
                ConstantInt.of(3),
                0.8f,
                5,
                0.1f,
                UniformInt.of(4, 7),
                0.7f))

            .placementModifiers(CountPlacement.of(62),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(256)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), MatchingBlockTagPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(1)),
                BiomeFilter.biome())
            .build();

        dry_cave_delta.register();
        features.add(dry_cave_delta);

        PlacedFeatureDefinition dripleaf_swamp_delta = PlacedFeatureDefinition.builder(PlacedFeatures.DELTA_DRIPLEAF_SWAMP_DELTA)
            .configuredFeature(Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(
                BlockTags.DIRT,
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                    .add(Blocks.GRASS_BLOCK.defaultBlockState(), 10)
                    .add(Blocks.MUD.defaultBlockState(), 3)
                    .add(Blocks.CLAY.defaultBlockState(), 1)
                    .add(Blocks.MOSS_BLOCK.defaultBlockState(), 3)
                    .build()),
                Holder.direct(new PlacedFeature(RegistryUtils.getConfiguredFeatureReference(CaveFeatures.DRIPLEAF), List.of())),
                CaveSurface.FLOOR,
                ConstantInt.of(3),
                0.8f,
                5,
                0.2f,
                UniformInt.of(4, 7),
                0.7f))
            .placementModifiers(CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(0)),
                BiomeFilter.biome())
            .build();

        dripleaf_swamp_delta.register();
        features.add(dripleaf_swamp_delta);

        PlacedFeatureDefinition swamp_delta = PlacedFeatureDefinition.builder(PlacedFeatures.DELTA_SWAMP_DELTA)
            .configuredFeature(Feature.DELTA_FEATURE, new DeltaFeatureConfiguration(
                Blocks.WATER.defaultBlockState(),
                Blocks.MUD.defaultBlockState(),
                UniformInt.of(3, 7),
                UniformInt.of(1, 2)))
            .placementModifiers(CountPlacement.of(64),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(0)),
                BiomeFilter.biome())
            .build();

        swamp_delta.register();
        features.add(swamp_delta);

        return features;
    }

    private static List<PlacedFeatureDefinition> terrain() {
        List<PlacedFeatureDefinition> features = new ArrayList<>();

        PlacedFeatureDefinition brown_concrete_disk = PlacedFeatureDefinition.builder(PlacedFeatures.TERRAIN_BROWN_CONCRETE_DISK)
            .configuredFeature(Feature.DISK, new DiskConfiguration(
                new RuleBasedBlockStateProvider(
                    BlockStateProvider.simple(Blocks.DEAD_BRAIN_CORAL_BLOCK),
                    List.of(new Rule(
                        BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD),
                        BlockStateProvider.simple(Blocks.BROWN_CONCRETE_POWDER)))),
                BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD),
                UniformInt.of(2, 6),
                2))
            .placementModifiers(CountPlacement.of(100),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-10), VerticalAnchor.absolute(256)),
                BiomeFilter.biome())
            .build();

        brown_concrete_disk.register();
        features.add(brown_concrete_disk);

        PlacedFeatureDefinition cliff = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                BlockStateProvider.simple(Blocks.DIORITE)))
            .placementModifiers(
                BlockPredicateFilter.forPredicate(
                    BlockPredicate.anyOf(
                        BlockPredicate.allOf(
                            BlockPredicate.matchesBlocks(Blocks.STONE),
                            BlockPredicate.anyOf(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.AIR, Blocks.WATER))),
                        BlockPredicate.allOf(
                            BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.STONE),
                            BlockPredicate.anyOf(BlockPredicate.matchesBlocks(new BlockPos(0, 1, 0), Blocks.AIR, Blocks.DIORITE))),
                        BlockPredicate.allOf(
                            BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.STONE),
                            BlockPredicate.anyOf(
                                BlockPredicate.matchesBlocks(new BlockPos(1, 0, 0), Blocks.AIR),
                                BlockPredicate.matchesBlocks(new BlockPos(-1, 0, 0), Blocks.AIR),
                                BlockPredicate.matchesBlocks(new BlockPos(0, 0, 1), Blocks.AIR),
                                BlockPredicate.matchesBlocks(new BlockPos(0, 0, -1), Blocks.AIR))),
                        BlockPredicate.allOf(
                            BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT),
                            BlockPredicate.anyOf(
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(1, -1, 0), Blocks.AIR, Blocks.WATER),
                                    BlockPredicate.not(BlockPredicate.matchesBlocks(new BlockPos(-1, 0, 0), Blocks.GRASS_BLOCK))),
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(-1, -1, 0), Blocks.AIR, Blocks.WATER),
                                    BlockPredicate.not(BlockPredicate.matchesBlocks(new BlockPos(1, 0, 0), Blocks.GRASS_BLOCK))),
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(0, -1, 1), Blocks.AIR, Blocks.WATER),
                                    BlockPredicate.not(BlockPredicate.matchesBlocks(new BlockPos(0, 0, -1), Blocks.GRASS_BLOCK))),
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(0, -1, -1), Blocks.AIR, Blocks.WATER),
                                    BlockPredicate.not(BlockPredicate.matchesBlocks(new BlockPos(0, 0, 1), Blocks.GRASS_BLOCK)))))))
            )
            .build();

        PlacedFeatureDefinition diorite_cliffs = PlacedFeatureDefinition.builder(PlacedFeatures.TERRAIN_DIORITE_CLIFFS)
            .configuredFeature(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                256,
                5,
                10,
                cliff.getFeatureHolder()
            ))
            .placementModifiers(CountPlacement.of(128),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(0)),
                BiomeFilter.biome())
            .build();

        diorite_cliffs.register();
        features.add(diorite_cliffs);

        PlacedFeatureDefinition grass_to_sand = PlacedFeatureDefinition.builder(PlacedFeatures.TERRAIN_GRASS_TO_SAND)
            .configuredFeature(Feature.DISK, new DiskConfiguration(
                new RuleBasedBlockStateProvider(
                    BlockStateProvider.simple(Blocks.SAND),
                    List.of(new Rule(
                        BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.DIRT),
                        BlockStateProvider.simple(Blocks.SAND)))),
                BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.GRASS_BLOCK),
                UniformInt.of(2, 6),
                2))
            .placementModifiers(CountPlacement.of(30),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome())
            .build();

        grass_to_sand.register();
        features.add(grass_to_sand);

        PlacedFeatureDefinition sand_shore_disk = PlacedFeatureDefinition.builder(PlacedFeatures.TERRAIN_SAND_SHORE_DISK)
            .configuredFeature(ConfiguredFeatures.TERRAIN_SAND_SHORE_DISK)
            .placementModifiers(CountPlacement.of(50),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(ConstantHeight.of(VerticalAnchor.absolute(62))),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
                BiomeFilter.biome())
            .build();

        sand_shore_disk.register();
        features.add(sand_shore_disk);

        PlacedFeatureDefinition cliffFeature = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                    new WeightedStateProvider(WeightedList.<BlockState>builder()
                        .add(Blocks.COBBLESTONE.defaultBlockState(), 7)
                        .add(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3)
                        .add(Blocks.STONE_BRICKS.defaultBlockState(), 2)
                        .add(Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), 1)
                        .add(Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 1)
                        .build()
                    )
                )
            )
            .placementModifiers(
                BlockPredicateFilter.forPredicate(
                    BlockPredicate.anyOf(
                        BlockPredicate.allOf(
                            BlockPredicate.matchesBlocks(Blocks.STONE),
                            BlockPredicate.anyOf(
                                BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.AIR, Blocks.WATER)
                            )),
                        BlockPredicate.allOf(
                            BlockPredicate.matchesBlocks(Blocks.STONE, Blocks.DIRT),
                            BlockPredicate.anyOf(
                                BlockPredicate.matchesBlocks(new BlockPos(0, 1, 0), Blocks.AIR, Blocks.OAK_WOOD)
                            )),
                        BlockPredicate.allOf(
                            BlockPredicate.matchesBlocks(Blocks.STONE, Blocks.DIRT),
                            BlockPredicate.anyOf(
                                BlockPredicate.matchesBlocks(new BlockPos(1, 0, 0), Blocks.AIR),
                                BlockPredicate.matchesBlocks(new BlockPos(-1, 0, 0), Blocks.AIR),
                                BlockPredicate.matchesBlocks(new BlockPos(0, 0, 1), Blocks.AIR),
                                BlockPredicate.matchesBlocks(new BlockPos(0, 0, -1), Blocks.AIR)
                            )),
                        BlockPredicate.allOf(
                            BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT),
                            BlockPredicate.anyOf(
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(1, -1, 0), Blocks.AIR, Blocks.WATER),
                                    BlockPredicate.matchesBlocks(new BlockPos(-1, 0, 0), Blocks.GRASS_BLOCK)
                                ),
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(-1, -1, 0), Blocks.AIR, Blocks.WATER),
                                    BlockPredicate.matchesBlocks(new BlockPos(1, 0, 0), Blocks.GRASS_BLOCK)
                                ),
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(0, -1, 1), Blocks.AIR, Blocks.WATER),
                                    BlockPredicate.matchesBlocks(new BlockPos(0, 0, -1), Blocks.GRASS_BLOCK)
                                ),
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(0, -1, -1), Blocks.AIR, Blocks.WATER),
                                    BlockPredicate.matchesBlocks(new BlockPos(0, 0, 1), Blocks.GRASS_BLOCK)
                                )
                            )
                        )
                    )
                )
            )
            .build();

        PlacedFeatureDefinition stone_cliff = PlacedFeatureDefinition.builder(PlacedFeatures.TERRAIN_STONE_CLIFF)
            .configuredFeature(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                256,
                5,
                10,
                cliffFeature.getFeatureHolder()))
            .placementModifiers(CountPlacement.of(128),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(0)),
                BiomeFilter.biome())
            .build();

        stone_cliff.register();
        features.add(stone_cliff);

        PlacedFeatureDefinition stone_to_ice = PlacedFeatureDefinition.builder(PlacedFeatures.TERRAIN_STONE_TO_ICE)
            .configuredFeature(Feature.REPLACE_BLOBS, new ReplaceSphereConfiguration(
                Blocks.STONE.defaultBlockState(),
                Blocks.BLUE_ICE.defaultBlockState(),
                UniformInt.of(7, 12)))
            .placementModifiers(CountPlacement.of(2),
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(0),
                    VerticalAnchor.absolute(100)
                ),
                BlockPredicateFilter.forPredicate(
                    BlockPredicate.anyOf(
                        BlockPredicate.matchesBlocks(new BlockPos(1, 0, 0), Blocks.AIR),
                        BlockPredicate.matchesBlocks(new BlockPos(-1, 0, 0), Blocks.AIR),
                        BlockPredicate.matchesBlocks(new BlockPos(0, 0, 1), Blocks.AIR),
                        BlockPredicate.matchesBlocks(new BlockPos(0, 0, -1), Blocks.AIR)
                    )
                ),
                BiomeFilter.biome())
            .build();

        stone_to_ice.register();
        features.add(stone_to_ice);

        PlacedFeatureDefinition blobFeature = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.WATER)))
            .placementModifiers(BlockPredicateFilter.forPredicate(
                BlockPredicate.allOf(
                    BlockPredicate.matchesTag(BlockTags.DIRT),
                    BlockPredicate.matchesBlocks(new BlockPos(0, 1, 0), Blocks.AIR, Blocks.WATER),
                    BlockPredicate.matchesBlocks(new BlockPos(0, 0, -1), Blocks.DIRT, Blocks.WATER, Blocks.GRASS_BLOCK),
                    BlockPredicate.matchesBlocks(new BlockPos(0, 0, 1), Blocks.DIRT, Blocks.WATER, Blocks.GRASS_BLOCK),
                    BlockPredicate.matchesBlocks(new BlockPos(1, 0, 0), Blocks.DIRT, Blocks.WATER, Blocks.GRASS_BLOCK),
                    BlockPredicate.matchesBlocks(new BlockPos(-1, 0, 0), Blocks.DIRT, Blocks.WATER, Blocks.GRASS_BLOCK)
                )))
            .build();

        PlacedFeatureDefinition water_blob = PlacedFeatureDefinition.builder(PlacedFeatures.TERRAIN_WATER_BLOB)
            .configuredFeature(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                100,
                3,
                2,
                blobFeature.getFeatureHolder()))
            .placementModifiers(CountPlacement.of(10),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome())
            .build();

        water_blob.register();
        features.add(water_blob);

        return features;
    }

    private static List<PlacedFeatureDefinition> tree() {
        List<PlacedFeatureDefinition> features = new ArrayList<>();

        PlacedFeatureDefinition beachy_palm = PlacedFeatureDefinition.builder(PlacedFeatures.TREE_BEACHY_PALM)
            .configuredFeature(ConfiguredFeatures.TREE_PALM_TREE)
            .placementModifiers(CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.DEAD_BUSH.defaultBlockState(), BlockPos.ZERO)))
            .build();

        beachy_palm.register();
        features.add(beachy_palm);

        PlacedFeatureDefinition fallen_stripped_pale_oak = PlacedFeatureDefinition.builder(PlacedFeatures.TREE_FALLEN_STRIPPED_PALE_OAK)
            .configuredFeature(ConfiguredFeatures.TREE_FALLEN_STRIPPED_PALE_OAK)
            .placementModifiers(RarityFilter.onAverageOnceEvery(3),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome())
            .build();

        fallen_stripped_pale_oak.register();
        features.add(fallen_stripped_pale_oak);

        PlacedFeatureDefinition fallen_tall_oak = PlacedFeatureDefinition.builder(PlacedFeatures.TREE_FALLEN_TALL_OAK)
            .configuredFeature(ConfiguredFeatures.TREE_FALLEN_TALL_OAK)
            .placementModifiers(RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome())
            .build();

        fallen_tall_oak.register();
        features.add(fallen_tall_oak);

        PlacedFeatureDefinition fallen_warped_stem = PlacedFeatureDefinition.builder(PlacedFeatures.TREE_FALLEN_WARPED_STEM)
            .configuredFeature(Feature.SIMPLE_RANDOM_SELECTOR,
                new SimpleRandomFeatureConfiguration(HolderSet.direct(
                    PlacedFeatureDefinition.builder()
                        .configuredFeature(ConfiguredFeatures.TREE_FALLEN_WARPED_STEM)
                        .build().getFeatureHolder(),
                    PlacedFeatureDefinition.builder()
                        .configuredFeature(ConfiguredFeatures.TREE_FALLEN_STRIPPED_WARPED_STEM)
                        .build().getFeatureHolder())))
            .placementModifiers(CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome())
            .build();

        fallen_warped_stem.register();
        features.add(fallen_warped_stem);

        PlacedFeatureDefinition moss_garden = PlacedFeatureDefinition.builder(PlacedFeatures.TREE_MOSS_GARDEN)
            .configuredFeature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                SimpleStateProvider.simple(Blocks.WARPED_STEM.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y)),
                new DarkOakTrunkPlacer(8, 3, 1),
                SimpleStateProvider.simple(Blocks.JUNGLE_LEAVES.defaultBlockState()
                    .setValue(BlockStateProperties.DISTANCE, 7)
                    .setValue(BlockStateProperties.PERSISTENT, false)),
                new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                Optional.empty(),
                new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.of(0)))
                .decorators(List.of(new TrunkVineDecorator()))
                .dirt(SimpleStateProvider.simple(Blocks.DIRT))
                .ignoreVines()
                .build())
            .placementModifiers(CountPlacement.of(8),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)))
            .build();

        moss_garden.register();
        features.add(moss_garden);

        PlacedFeatureDefinition palm_beach_palm = PlacedFeatureDefinition.builder(PlacedFeatures.TREE_PALM_BEACH_PALM)
            .configuredFeature(ConfiguredFeatures.TREE_PALM_TREE)
            .placementModifiers(CountPlacement.of(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.DEAD_BUSH.defaultBlockState(), BlockPos.ZERO)),
                BiomeFilter.biome())
            .build();

        palm_beach_palm.register();
        features.add(palm_beach_palm);

        PlacedFeatureDefinition lush_desert_palm = PlacedFeatureDefinition.builder(PlacedFeatures.TREE_LUSH_DESERT_PALM)
            .configuredFeature(ConfiguredFeatures.TREE_PALM_TREE)
            .placementModifiers(RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.DEAD_BUSH.defaultBlockState(), BlockPos.ZERO)),
                BiomeFilter.biome())
            .build();

        lush_desert_palm.register();
        features.add(lush_desert_palm);

        PlacedFeatureDefinition tall_oak = PlacedFeatureDefinition.builder(PlacedFeatures.TREE_TALL_OAK_WITH_LITTER)
            .configuredFeature(ConfiguredFeatures.TREE_TALL_OAK_WITH_LITTER)
            .placementModifiers(CountPlacement.of(5),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(
                BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)))
            .build();

        tall_oak.register();
        features.add(tall_oak);

        PlacedFeatureDefinition tall_stripped_pale_oak = PlacedFeatureDefinition.builder(PlacedFeatures.TREE_TALL_STRIPPED_PALE_OAK)
            .configuredFeature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.STRIPPED_PALE_OAK_LOG),
                new StraightTrunkPlacer(10, 3, 0),
                SimpleStateProvider.simple(Blocks.FLOWERING_AZALEA_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.ZERO, 3),
                new TwoLayersFeatureSize(1, 0, 1))
                .decorators(List.of(new BeehiveDecorator(0.002f)))
                .dirt(BlockStateProvider.simple(Blocks.DIRT))
                .ignoreVines()
                .build())
            .placementModifiers(CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING))
            .build();

        tall_stripped_pale_oak.register();
        features.add(tall_stripped_pale_oak);

        return features;
    }

    private static List<PlacedFeatureDefinition> vegetation() {
        List<PlacedFeatureDefinition> features = new ArrayList<>();

        PlacedFeatureDefinition azalea_bush_or_scrub = PlacedFeatureDefinition.builder(PlacedFeatures.VEGETATION_AZALEA_BUSH_OR_SCRUB)
            .configuredFeature(ConfiguredFeatures.VEGETATION_AZALEA_BUSH_OR_SCRUB)
            .placementModifiers(NoiseBasedCountPlacement.of(25, 30, 0),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                    BlockPredicate.matchesBlocks(Blocks.AIR),
                    BlockPredicate.wouldSurvive(Blocks.AZALEA.defaultBlockState(), BlockPos.ZERO)
                )),
                BiomeFilter.biome())
            .build();

        azalea_bush_or_scrub.register();
        features.add(azalea_bush_or_scrub);

        PlacedFeatureDefinition desert_azalea_scrub = PlacedFeatureDefinition.builder(PlacedFeatures.VEGETATION_LUSH_DESERT_AZALEA_SCRUB)
            .configuredFeature(ConfiguredFeatures.VEGETATION_AZALEA_SCRUB)
            .placementModifiers(CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                    BlockPredicate.matchesBlocks(Blocks.AIR),
                    BlockPredicate.matchesTag(new BlockPos(0, -1, 0), BlockTags.AZALEA_GROWS_ON)
                )),
                BiomeFilter.biome())
            .build();

        desert_azalea_scrub.register();
        features.add(desert_azalea_scrub);

        PlacedFeatureDefinition patch = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                    .add(Blocks.MOSS_CARPET.defaultBlockState(), 25)
                    .add(Blocks.SHORT_GRASS.defaultBlockState(), 25)
                    .add(Blocks.TALL_GRASS.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), 10)
                    .build())
            ))
            .build();

        PlacedFeatureDefinition moss_patch = PlacedFeatureDefinition.builder(PlacedFeatures.VEGETATION_MOSS_PATCH)
            .configuredFeature(Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(
                BlockTags.MOSS_REPLACEABLE,
                BlockStateProvider.simple(Blocks.MOSS_BLOCK),
                patch.getFeatureHolder(),
                CaveSurface.FLOOR,
                ConstantInt.of(1),
                0.0f,
                5,
                0.3f,
                UniformInt.of(1, 2),
                0.75f))
            .placementModifiers(CountPlacement.of(3),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome())
            .build();

        moss_patch.register();
        features.add(moss_patch);

        PlacedFeatureDefinition cherry_petals = PlacedFeatureDefinition.builder(PlacedFeatures.VEGETATION_PATCH_CHERRY_PETALS)
            .configuredFeature(VegetationFeatures.FLOWER_CHERRY)
            .placementModifiers(NoiseThresholdCountPlacement.of(-0.8f, 15, 4),
                RarityFilter.onAverageOnceEvery(32),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                BiomeFilter.biome())
            .build();

        cherry_petals.register();
        features.add(cherry_petals);

        PlacedFeatureDefinition cliff_grass = PlacedFeatureDefinition.builder(PlacedFeatures.VEGETATION_PATCH_CLIFF_GRASS)
            .configuredFeature(ConfiguredFeatures.VEGETATION_PATCH_CLIFF_GRASS)
            .placementModifiers(CountPlacement.of(50),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(ConstantHeight.of(VerticalAnchor.aboveBottom(256))),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome())
            .build();

        cliff_grass.register();
        features.add(cliff_grass);

        PlacedFeatureDefinition balePatch = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.HAY_BLOCK)))
            .placementModifiers(BlockPredicateFilter.forPredicate(
                BlockPredicate.allOf(
                    BlockPredicate.matchesBlocks(Blocks.AIR),
                    BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.GRASS_BLOCK)
                )))
            .build();

        PlacedFeatureDefinition hay_bale = PlacedFeatureDefinition.builder(PlacedFeatures.VEGETATION_PATCH_HAY_BALE)
            .configuredFeature(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                96,
                7,
                3,
                balePatch.getFeatureHolder()))
            .placementModifiers(RarityFilter.onAverageOnceEvery(50),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                BiomeFilter.biome())
            .build();

        hay_bale.register();
        features.add(hay_bale);

        PlacedFeatureDefinition dripleafPatch = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(SimpleStateProvider.simple(Blocks.SMALL_DRIPLEAF)))
            .placementModifiers(BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)))
            .build();

        PlacedFeatureDefinition small_dripleaf = PlacedFeatureDefinition.builder(PlacedFeatures.VEGETATION_PATCH_SMALL_DRIPLEAF)
            .configuredFeature(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                10,
                7,
                3,
                dripleafPatch.getFeatureHolder()))
            .placementModifiers(CountPlacement.of(4),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome())
            .build();

        small_dripleaf.register();
        features.add(small_dripleaf);

        PlacedFeatureDefinition leavesPatch = PlacedFeatureDefinition.builder()
            .configuredFeature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                    .add(Blocks.AZALEA_LEAVES.defaultBlockState()
                            .setValue(BlockStateProperties.WATERLOGGED, true)
                            .setValue(BlockStateProperties.PERSISTENT, true),
                        20)
                    .add(Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState()
                            .setValue(BlockStateProperties.WATERLOGGED, true)
                            .setValue(BlockStateProperties.PERSISTENT, true),
                        6)
                    .add(Blocks.MANGROVE_ROOTS.defaultBlockState()
                            .setValue(BlockStateProperties.WATERLOGGED, true),
                        1)
                    .build())))
            .placementModifiers(BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(BlockPos.ZERO, Fluids.WATER)))
            .build();

        PlacedFeatureDefinition water_leaves = PlacedFeatureDefinition.builder(PlacedFeatures.VEGETATION_PATCH_WATER_LEAVES)
            .configuredFeature(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                32,
                3,
                0,
                leavesPatch.getFeatureHolder()
            ))
            .placementModifiers(CountPlacement.of(5),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(ConstantHeight.of(VerticalAnchor.absolute(62))),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(BlockPos.ZERO, Fluids.WATER)),
                BiomeFilter.biome())
            .build();

        water_leaves.register();
        features.add(water_leaves);

        PlacedFeatureDefinition rooted_dirt_blob = PlacedFeatureDefinition.builder(PlacedFeatures.VEGETATION_ROOT_DIRT_BLOB)
            .configuredFeature(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                List.of(new WeightedPlacedFeature(PlacedFeatureDefinition.builder()
                    .configuredFeature(Feature.FOREST_ROCK, new BlockStateConfiguration(Blocks.COARSE_DIRT.defaultBlockState()))
                    .build().getFeatureHolder(), 0.5f)),
                PlacedFeatureDefinition.builder()
                    .configuredFeature(Feature.FOREST_ROCK, new BlockStateConfiguration(Blocks.ROOTED_DIRT.defaultBlockState()))
                    .build().getFeatureHolder()))
            .placementModifiers(CountPlacement.of(UniformInt.of(0, 1)),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                BiomeFilter.biome())
            .build();

        rooted_dirt_blob.register();
        features.add(rooted_dirt_blob);

        return features;
    }

}
