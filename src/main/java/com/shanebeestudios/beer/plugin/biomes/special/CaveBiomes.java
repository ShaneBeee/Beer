package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.biomes.BeerBiomes;
import com.shanebeestudios.beer.api.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;

public class CaveBiomes {

    public static Biome getBiome(BiomeParameterPoint paramPoint) {
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        if (temp <= 1) {
            return BeerBiomes.CAVE_ICE_CAVE;
        } else if (temp >= 3) {
            if (humidity <= 1) return BeerBiomes.CAVE_DRY_CAVE;
            else if (humidity >= 3) return Biome.LUSH_CAVES;
        }
        return null;
    }

}
