package com.shanebeestudios.beer.api.biomes.continental;

import com.shanebeestudios.beer.api.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;

public class DeepOceanBiomes {

    public static Biome getBiome(BiomeParameterPoint paramPoint) {
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        return switch (temp) {
            case 0 -> Biome.DEEP_FROZEN_OCEAN;
            case 1 -> Biome.DEEP_COLD_OCEAN;
            case 2 -> Biome.DEEP_OCEAN;
            case 3 -> Biome.DEEP_LUKEWARM_OCEAN;
            default -> Biome.WARM_OCEAN;
        };
    }

}
