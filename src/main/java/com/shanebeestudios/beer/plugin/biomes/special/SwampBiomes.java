package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.biomes.BeerBiomesOld;
import com.shanebeestudios.beer.api.utils.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;

public class SwampBiomes {

    public static Biome getBiome(BiomeParameterPoint paramPoint) {
        int pv = ParamPoints.PEAKS_AND_VALLEYS.getFixedPoint(paramPoint);
        int erosion = ParamPoints.EROSION.getFixedPoint(paramPoint);
        int temp = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        if (pv <= 1 && erosion == 6) {
            if (temp == 1 || temp == 2) {
                return BeerBiomesOld.SWAMP_DRIPLEAF_SWAMP;
            } else if (temp == 3 || temp == 4) {
                return Biome.MANGROVE_SWAMP;
            }
        }
        return null;
    }

}
