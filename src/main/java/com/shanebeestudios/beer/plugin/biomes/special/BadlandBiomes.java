package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.api.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;

public class BadlandBiomes {

    public static Biome getBiome(BiomeParameterPoint point) {
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(point);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(point);
        return switch (humidity) {
            case 0,1 -> weirdness == 1 ? Biome.ERODED_BADLANDS : Biome.BADLANDS;
            case 2 -> Biome.BADLANDS;
            default -> Biome.WOODED_BADLANDS;
        };
    }

}
