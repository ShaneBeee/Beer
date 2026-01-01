package com.shanebeestudios.beer.plugin.biomes.continental;

import com.shanebeestudios.beer.api.utils.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;

public class OceanBiomes {

    public static Biome getBiome(BiomeParameterPoint paramPoint) {
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        return switch (temp) {
            case 0 -> Biome.FROZEN_OCEAN;
            case 1 -> Biome.COLD_OCEAN;
            case 2 -> Biome.OCEAN;
            case 3 -> Biome.LUKEWARM_OCEAN;
            default -> Biome.WARM_OCEAN;
        };
    }

}
