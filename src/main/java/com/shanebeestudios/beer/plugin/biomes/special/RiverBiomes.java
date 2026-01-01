package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.biomes.BeerBiomes;
import com.shanebeestudios.beer.api.utils.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;

public class RiverBiomes {

    public static Biome getBiome(BiomeParameterPoint paramPoint) {
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        return switch (temp) {
            case 0 -> Biome.FROZEN_RIVER;
            case 3 -> BeerBiomes.RIVER_LUSH_RIVER;
            case 4 -> BeerBiomes.RIVER_DESERT_RIVER;
            default -> BeerBiomes.RIVER_TEMPERATE_RIVER;
        };
    }

}
