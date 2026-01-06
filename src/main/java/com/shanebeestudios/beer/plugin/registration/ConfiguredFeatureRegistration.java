package com.shanebeestudios.beer.plugin.registration;

import com.shanebeestudios.beer.api.registration.ConfiguredFeatureDefinition;
import com.shanebeestudios.beer.api.registration.PlacedFeatureDefinition;
import com.shanebeestudios.beer.api.utils.DumpRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FallenTreeConfiguration.FallenTreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.PlaceOnGroundDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;

import java.util.ArrayList;
import java.util.List;

public class ConfiguredFeatureRegistration {

    private static final List<ConfiguredFeatureDefinition> FEATURES = new ArrayList<>();

    public static void registerFeatures() {
        FEATURES.addAll(delta());
        FEATURES.addAll(terrain());
        FEATURES.addAll(tree());
        FEATURES.addAll(vegetation());
    }

    public static void dumpToDatapack() {
        // Dump features to datapack files
        DumpRegistry.dumpDefinables(FEATURES);
    }

    private static List<ConfiguredFeatureDefinition> delta() {
        List<ConfiguredFeatureDefinition> features = new ArrayList<>();

        ConfiguredFeatureDefinition moss_delta = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.DELTA_MOSS_DELTA)
            .config(Feature.DELTA_FEATURE, new DeltaFeatureConfiguration(
                Blocks.WATER.defaultBlockState(),
                Blocks.MOSS_BLOCK.defaultBlockState(),
                UniformInt.of(3, 7),
                UniformInt.of(1, 2)))
            .build();

        moss_delta.register();
        features.add(moss_delta);

        return features;
    }

    private static List<ConfiguredFeatureDefinition> terrain() {
        List<ConfiguredFeatureDefinition> features = new ArrayList<>();

        ConfiguredFeatureDefinition sand_shore_disk = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.TERRAIN_SAND_SHORE_DISK)
            .config(Feature.DISK, new DiskConfiguration(
                RuleBasedBlockStateProvider.simple(Blocks.SAND),
                BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT),
                UniformInt.of(3, 5),
                1))
            .build();

        sand_shore_disk.register();
        features.add(sand_shore_disk);

        return features;
    }

    private static List<ConfiguredFeatureDefinition> tree() {
        List<ConfiguredFeatureDefinition> features = new ArrayList<>();

        ConfiguredFeatureDefinition fallen_stripped_pale_oak = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.TREE_FALLEN_STRIPPED_PALE_OAK)
            .config(Feature.FALLEN_TREE, new FallenTreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.STRIPPED_PALE_OAK_LOG),
                UniformInt.of(4, 7))
                .build())
            .build();

        fallen_stripped_pale_oak.register();
        features.add(fallen_stripped_pale_oak);

        ConfiguredFeatureDefinition fallen_warped_stem = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.TREE_FALLEN_WARPED_STEM)
            .config(Feature.FALLEN_TREE, new FallenTreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.WARPED_STEM),
                UniformInt.of(4, 7))
                .build())
            .build();

        fallen_warped_stem.register();
        features.add(fallen_warped_stem);

        ConfiguredFeatureDefinition fallen_warped_stem_stripped = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.TREE_FALLEN_STRIPPED_WARPED_STEM)
            .config(Feature.FALLEN_TREE, new FallenTreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.STRIPPED_WARPED_STEM),
                UniformInt.of(4, 7))
                .build())
            .build();

        fallen_warped_stem_stripped.register();
        features.add(fallen_warped_stem_stripped);

        ConfiguredFeatureDefinition fallen_tall_oak = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.TREE_FALLEN_TALL_OAK)
            .config(Feature.FALLEN_TREE, new FallenTreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                UniformInt.of(6, 8))
                .build())
            .build();

        fallen_tall_oak.register();
        features.add(fallen_tall_oak);

        ConfiguredFeatureDefinition palm_tree = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.TREE_PALM_TREE)
            .config(Feature.TREE, new TreeConfigurationBuilder(
                SimpleStateProvider.simple(Blocks.JUNGLE_WOOD),
                new ForkingTrunkPlacer(5, 2, 3),
                SimpleStateProvider.simple(Blocks.JUNGLE_LEAVES.defaultBlockState()
                    .setValue(BlockStateProperties.WATERLOGGED, false)
                    .setValue(BlockStateProperties.PERSISTENT, false)
                    .setValue(BlockStateProperties.DISTANCE, 7)),
                new AcaciaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 2))
                .dirt(SimpleStateProvider.simple(Blocks.JUNGLE_WOOD))
                .decorators(List.of(
                    new CocoaDecorator(0.2f),
                    new BeehiveDecorator(0.03f)))
                .forceDirt()
                .build())
            .build();

        palm_tree.register();
        features.add(palm_tree);

        Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        WeightedList.Builder<BlockState> a1 = WeightedList.builder();
        WeightedList.Builder<BlockState> a2 = WeightedList.builder();

        BlockState litter = Blocks.LEAF_LITTER.defaultBlockState();
        for (int i = 1; i < 5; i++) {
            for (Direction direction : directions) {
                BlockState blockState = litter
                    .setValue(BlockStateProperties.SEGMENT_AMOUNT, i)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
                if (i < 4) {
                    a1.add(blockState, 1);
                }
                a2.add(blockState, 1);
            }
        }

        ConfiguredFeatureDefinition tall_oak = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.TREE_TALL_OAK_WITH_LITTER)
            .config(Feature.TREE, new TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(7, 3, 2),
                BlockStateProvider.simple(Blocks.OAK_LEAVES.defaultBlockState()
                    .setValue(BlockStateProperties.DISTANCE, 7)),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.ZERO, 3),
                new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Blocks.OAK_LOG))
                .forceDirt()
                .decorators(List.of(new PlaceOnGroundDecorator(96, 4, 2,
                    new WeightedStateProvider(a1.build())),
                    new PlaceOnGroundDecorator(150, 2, 2,
                        new WeightedStateProvider(a2.build()))))
                .build())
            .build();

        tall_oak.register();
        features.add(tall_oak);

        return features;
    }

    private static List<ConfiguredFeatureDefinition> vegetation() {
        List<ConfiguredFeatureDefinition> features = new ArrayList<>();

        // PREDICATES
        BlockPredicateFilter fernPredicate = BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.FERN.defaultBlockState(), BlockPos.ZERO));

        // AZALEA SCRUB
        ConfiguredFeatureDefinition azalea_scrub = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.VEGETATION_AZALEA_SCRUB)
            .config(Feature.TREE, new TreeConfigurationBuilder(
                SimpleStateProvider.simple(Blocks.MANGROVE_ROOTS),
                new StraightTrunkPlacer(1, 1, 0),
                SimpleStateProvider.simple(Blocks.ACACIA_LEAVES.defaultBlockState()
                    .setValue(BlockStateProperties.PERSISTENT, true)),
                new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 2),
                new TwoLayersFeatureSize(0, 0, 0))
                .forceDirt()
                .build())
            .build();

        azalea_scrub.register();
        features.add(azalea_scrub);

        // FLOWERING AZALEA SCRUB
        ConfiguredFeatureDefinition flowering_azalea_scrub = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.VEGETATION_FLOWERING_AZALEA_SCRUB)
            .config(Feature.TREE, new TreeConfigurationBuilder(
                SimpleStateProvider.simple(Blocks.MANGROVE_ROOTS),
                new StraightTrunkPlacer(1, 1, 0),
                new WeightedStateProvider(WeightedList.<BlockState>builder()
                    .add(Blocks.AZALEA_LEAVES.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true), 2)
                    .add(Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true), 1)
                    .build()),
                new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 2),
                new TwoLayersFeatureSize(0, 0, 0))
                .forceDirt()
                .build())
            .build();

        flowering_azalea_scrub.register();
        features.add(flowering_azalea_scrub);

        // AZALEA BUSH
        ConfiguredFeatureDefinition azalea_bush = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.VEGETATION_AZALEA_BUSH_OR_SCRUB)
            .config(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacedFeatureDefinition.builder()
                    .configuredFeature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.AZALEA)))
                    .placementModifiers(fernPredicate)
                    .build()
                    .getFeatureHolder(),
                    0.2f),
                new WeightedPlacedFeature(PlacedFeatureDefinition.builder()
                    .configuredFeature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.FLOWERING_AZALEA)))
                    .placementModifiers(fernPredicate)
                    .build()
                    .getFeatureHolder(),
                    0.1f),
                new WeightedPlacedFeature(PlacedFeatureDefinition.builder()
                    .configuredFeature(ConfiguredFeatures.VEGETATION_FLOWERING_AZALEA_SCRUB)
                    .placementModifiers(fernPredicate)
                    .build()
                    .getFeatureHolder(),
                    0.25f)),
                PlacedFeatureDefinition.builder()
                    .configuredFeature(ConfiguredFeatures.VEGETATION_AZALEA_SCRUB)
                    .placementModifiers(fernPredicate)
                    .build()
                    .getFeatureHolder()))
            .build();
        azalea_bush.register();
        features.add(azalea_bush);

        ConfiguredFeatureDefinition patch_cliff_grass = ConfiguredFeatureDefinition.builder(ConfiguredFeatures.VEGETATION_PATCH_CLIFF_GRASS)
            .config(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                256,
                3,
                3,
                PlacedFeatureDefinition.builder()
                    .configuredFeature(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.GRASS_BLOCK)))
                    .placementModifiers(BlockPredicateFilter.forPredicate(
                        BlockPredicate.allOf(
                            BlockPredicate.matchesBlocks(new BlockPos(0, 1, 0), Blocks.AIR),
                            BlockPredicate.matchesBlocks(BlockPos.ZERO, Blocks.STONE, Blocks.GRANITE, Blocks.GRASS_BLOCK, Blocks.DEEPSLATE, Blocks.CALCITE),
                            BlockPredicate.not(
                                BlockPredicate.anyOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(1, -1, 0), Blocks.AIR),
                                    BlockPredicate.matchesBlocks(new BlockPos(-1, -1, 0), Blocks.AIR),
                                    BlockPredicate.matchesBlocks(new BlockPos(0, -1, 1), Blocks.AIR),
                                    BlockPredicate.matchesBlocks(new BlockPos(0, -1, -1), Blocks.AIR)
                                )
                            ),
                            BlockPredicate.anyOf(
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(1, 1, 0), Blocks.AIR),
                                    BlockPredicate.matchesBlocks(new BlockPos(1, 0, 0), Blocks.STONE, Blocks.GRANITE, Blocks.GRASS_BLOCK, Blocks.DEEPSLATE, Blocks.CALCITE)
                                ),
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(-1, 1, 0), Blocks.AIR),
                                    BlockPredicate.matchesBlocks(new BlockPos(-1, 0, 0), Blocks.STONE, Blocks.GRANITE, Blocks.GRASS_BLOCK, Blocks.DEEPSLATE, Blocks.CALCITE)

                                ),
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(0, 1, 1), Blocks.AIR),
                                    BlockPredicate.matchesBlocks(new BlockPos(0, 0, 1), Blocks.STONE, Blocks.GRANITE, Blocks.GRASS_BLOCK, Blocks.DEEPSLATE, Blocks.CALCITE)
                                ),
                                BlockPredicate.allOf(
                                    BlockPredicate.matchesBlocks(new BlockPos(0, 1, -1), Blocks.AIR),
                                    BlockPredicate.matchesBlocks(new BlockPos(0, 0, -1), Blocks.STONE, Blocks.GRANITE, Blocks.GRASS_BLOCK, Blocks.DEEPSLATE, Blocks.CALCITE)
                                )

                            )

                        )
                    ))
                    .build().getFeatureHolder()
            ))
            .build();

        patch_cliff_grass.register();
        features.add(patch_cliff_grass);

        // RETURN
        return features;
    }

}
