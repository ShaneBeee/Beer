package com.shanebeestudios.beer.api.registration.feature;

import com.shanebeestudios.beer.api.utils.DumpRegistry;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

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

        return features;
    }

}
