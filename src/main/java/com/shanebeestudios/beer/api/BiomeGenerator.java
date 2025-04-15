package com.shanebeestudios.beer.api;

import org.bukkit.Registry;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BiomeGenerator extends BiomeProvider {

    // Unused
    //minecraft:beach
    //minecraft:cherry_grove
    //minecraft:dark_forest
    //minecraft:deep_dark
    //minecraft:dripstone_caves
    //minecraft:flower_forest
    //minecraft:frozen_peaks
    //minecraft:grove
    //minecraft:ice_spikes
    //minecraft:jagged_peaks
    //minecraft:meadow
    //minecraft:mushroom_fields
    //minecraft:old_growth_pine_taiga
    //minecraft:old_growth_spruce_taiga
    //minecraft:pale_garden
    //minecraft:savanna
    //minecraft:savanna_plateau
    //minecraft:sparse_jungle
    //minecraft:stony_peaks
    //minecraft:stony_shore
    //minecraft:sunflower_plains
    //minecraft:swamp
    //minecraft:windswept_forest
    //minecraft:windswept_gravelly_hills
    //minecraft:windswept_hills

    private static final List<Biome> AVAILABLE_BIOMES = new ArrayList<>();

    static {
        AVAILABLE_BIOMES.addAll(BeerBiomes.BEER_BIOMES);
//        AVAILABLE_BIOMES.add(Biome.BADLANDS);
//        AVAILABLE_BIOMES.add(Biome.BAMBOO_JUNGLE);
//        AVAILABLE_BIOMES.add(Biome.BIRCH_FOREST);
//        AVAILABLE_BIOMES.add(Biome.COLD_OCEAN);
//        AVAILABLE_BIOMES.add(Biome.DEEP_COLD_OCEAN);
//        AVAILABLE_BIOMES.add(Biome.DEEP_FROZEN_OCEAN);
//        AVAILABLE_BIOMES.add(Biome.DEEP_LUKEWARM_OCEAN);
//        AVAILABLE_BIOMES.add(Biome.DEEP_OCEAN);
//        AVAILABLE_BIOMES.add(Biome.DESERT);
//        AVAILABLE_BIOMES.add(Biome.ERODED_BADLANDS);
//        AVAILABLE_BIOMES.add(Biome.FOREST);
//        AVAILABLE_BIOMES.add(Biome.FROZEN_OCEAN);
//        AVAILABLE_BIOMES.add(Biome.FROZEN_RIVER);
//        AVAILABLE_BIOMES.add(Biome.JUNGLE);
//        AVAILABLE_BIOMES.add(Biome.LUKEWARM_OCEAN);
//        AVAILABLE_BIOMES.add(Biome.LUSH_CAVES);
//        AVAILABLE_BIOMES.add(Biome.MANGROVE_SWAMP);
//        AVAILABLE_BIOMES.add(Biome.OCEAN);
//        AVAILABLE_BIOMES.add(Biome.OLD_GROWTH_BIRCH_FOREST);
//        AVAILABLE_BIOMES.add(Biome.RIVER);
//        AVAILABLE_BIOMES.add(Biome.SNOWY_BEACH);
//        AVAILABLE_BIOMES.add(Biome.SNOWY_PLAINS);
//        AVAILABLE_BIOMES.add(Biome.SNOWY_SLOPES);
//        AVAILABLE_BIOMES.add(Biome.SNOWY_TAIGA);
//        AVAILABLE_BIOMES.add(Biome.TAIGA);
//        AVAILABLE_BIOMES.add(Biome.WARM_OCEAN);
//        AVAILABLE_BIOMES.add(Biome.WINDSWEPT_SAVANNA);
//        AVAILABLE_BIOMES.add(Biome.WOODED_BADLANDS);
        for (Biome biome : Registry.BIOME) {
            if (biome.getKey().namespace().equals("minecraft")) {
                AVAILABLE_BIOMES.add(biome);
            }
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        // This one is never actually called
        return null;
    }

    @Override
    public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z, @NotNull BiomeParameterPoint param) {
        return BiomeDistributor.getBiome(param);
    }

    @Override
    public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return AVAILABLE_BIOMES;
    }

}
