package com.shanebeestudios.beer.plugin.registration;

import com.shanebeestudios.beer.api.registration.ConfiguredFeatureDefinition;
import com.shanebeestudios.beer.api.registration.PlacedFeatureDefinition;
import com.shanebeestudios.beer.api.utils.DumpRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FallenTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;

import java.util.ArrayList;
import java.util.List;

public class ConfiguredFeatureRegistration {

    public static void registerFeatures() {
        List<ConfiguredFeatureDefinition> features = new ArrayList<>();

        features.addAll(tree());
        features.addAll(vegetation());

        // Dump features to datapack files
        DumpRegistry.dumpDefinables(features);

    }

    private static List<ConfiguredFeatureDefinition> tree() {
        List<ConfiguredFeatureDefinition> features = new ArrayList<>();

        ConfiguredFeatureDefinition fallen_stripped_pale_oak = ConfiguredFeatureDefinition.builder("beer:tree/fallen_stripped_pale_oak")
            .feature(Feature.FALLEN_TREE)
            .config(new FallenTreeConfiguration.FallenTreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.STRIPPED_PALE_OAK_LOG),
                UniformInt.of(4, 7))
                .build())
            .build();

        ConfiguredFeatures.TREE_FALLEN_STRIPPED_PALE_OAK = fallen_stripped_pale_oak.register();
        features.add(fallen_stripped_pale_oak);

        ConfiguredFeatureDefinition fallen_warped_stem = ConfiguredFeatureDefinition.builder("beer:tree/fallen_warped_stem")
            .feature(Feature.FALLEN_TREE)
            .config(new FallenTreeConfiguration.FallenTreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.WARPED_STEM),
                UniformInt.of(4, 7))
                .build())
            .build();

        ConfiguredFeatures.TREE_FALLEN_WARPED_STEM = fallen_warped_stem.register();
        features.add(fallen_warped_stem);

        ConfiguredFeatureDefinition fallen_warped_stem_stripped = ConfiguredFeatureDefinition.builder("beer:tree/fallen_stripped_warped_stem")
            .feature(Feature.FALLEN_TREE)
            .config(new FallenTreeConfiguration.FallenTreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.STRIPPED_WARPED_STEM),
                UniformInt.of(4, 7))
                .build())
            .build();

        ConfiguredFeatures.TREE_FALLEN_STRIPPED_WARPED_STEM = fallen_warped_stem_stripped.register();
        features.add(fallen_warped_stem_stripped);

        ConfiguredFeatureDefinition palm_tree = ConfiguredFeatureDefinition.builder("beer:tree/palm_tree")
            .feature(Feature.TREE)
            .config(new TreeConfiguration.TreeConfigurationBuilder(
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

        ConfiguredFeatures.TREE_PALM_TREE = palm_tree.register();
        features.add(palm_tree);

        return features;
    }

    private static List<ConfiguredFeatureDefinition> vegetation() {
        List<ConfiguredFeatureDefinition> features = new ArrayList<>();

        // PREDICATES
        BlockPredicateFilter fernPredicate = BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.FERN.defaultBlockState(), BlockPos.ZERO));

        // AZALEA SCRUB
        ConfiguredFeatureDefinition azalea_scrub = ConfiguredFeatureDefinition.builder("beer:vegetation/azalea_scrub")
            .feature(Feature.TREE)
            .config(new TreeConfiguration.TreeConfigurationBuilder(
                SimpleStateProvider.simple(Blocks.MANGROVE_ROOTS),
                new StraightTrunkPlacer(1, 1, 0),
                SimpleStateProvider.simple(Blocks.ACACIA_LEAVES.defaultBlockState()
                    .setValue(BlockStateProperties.PERSISTENT, true)),
                new FancyFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 2),
                new TwoLayersFeatureSize(0, 0, 0))
                .forceDirt()
                .build())
            .build();

        ConfiguredFeatures.VEGETATION_AZALEA_SCRUB = azalea_scrub.register();
        features.add(azalea_scrub);

        // FLOWERING AZALEA SCRUB
        ConfiguredFeatureDefinition flowering_azalea_scrub = ConfiguredFeatureDefinition.builder("beer:vegetation/flowering_azalea_scrub")
            .feature(Feature.TREE)
            .config(new TreeConfiguration.TreeConfigurationBuilder(
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

        ConfiguredFeatures.VEGETATION_FLOWERING_AZALEA_SCRUB = flowering_azalea_scrub.register();
        features.add(flowering_azalea_scrub);

        // AZALEA BUSH
        ConfiguredFeatureDefinition azalea_bush = ConfiguredFeatureDefinition.builder("beer:vegetation/azalea_bush")
            .feature(Feature.RANDOM_SELECTOR)
            .config(new RandomFeatureConfiguration(List.of(
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
        ConfiguredFeatures.VEGETATION_AZALEA_BUSH = azalea_bush.register();
        features.add(azalea_bush);

        // RETURN
        return features;
    }

}
