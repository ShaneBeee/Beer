package com.shanebeestudios.beer.plugin.biomes;

import com.shanebeestudios.beer.api.ParamPoints;
import com.shanebeestudios.beer.plugin.biomes.special.CaveBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.CoastalBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.DeepOceanBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.FarInlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.MidInlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.NearInlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.OceanBiomes;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;
import org.jetbrains.annotations.NotNull;

public class BiomeDistributor {

    public static @NotNull Biome getBiome(BiomeParameterPoint paramPoint) {
        if (ParamPoints.DEPTH.getFixedPoint(paramPoint) > 10) {
            Biome biome = CaveBiomes.getBiome(paramPoint);
            if (biome != null) return biome;
        }
        int continent = ParamPoints.CONTINENTALNESS.getFixedPoint(paramPoint);
        return switch (continent) {
            case 0 -> Biome.MUSHROOM_FIELDS;
            case 1 -> DeepOceanBiomes.getBiome(paramPoint);
            case 2 -> OceanBiomes.getBiome(paramPoint);
            case 3 -> CoastalBiomes.getBiome(paramPoint);
            case 4 -> NearInlandBiomes.getBiome(paramPoint);
            case 5 -> MidInlandBiomes.getBiome(paramPoint);
            default -> FarInlandBiomes.getBiome(paramPoint);
        };
    }

}
