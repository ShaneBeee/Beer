package com.shanebeestudios.beer.api.registration.feature;

import com.shanebeestudios.beer.api.utils.DumpRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;

import java.util.ArrayList;
import java.util.List;

public class ConfiguredFeatureRegistration {

    public static void registerFeatures() {
        List<ConfiguredFeatureDefinition> features = new ArrayList<>();

        features.addAll(terrain());
        features.addAll(vegetation());

        // Dump features to datapack files
        for (ConfiguredFeatureDefinition feature : features) {
            DumpRegistry.dumpObject(feature.getIdentifier(), feature.getFeature());
        }

    }

    private static List<ConfiguredFeatureDefinition> terrain() {
        List<ConfiguredFeatureDefinition> features = new ArrayList<>();

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
