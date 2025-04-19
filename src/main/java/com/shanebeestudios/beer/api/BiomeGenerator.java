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

    private static final List<Biome> AVAILABLE_BIOMES = new ArrayList<>();

    static {
        AVAILABLE_BIOMES.addAll(BeerBiomes.BEER_BIOMES);
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
