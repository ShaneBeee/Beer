package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.biomes.BeerBiomes;
import com.shanebeestudios.beer.api.utils.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;

public class MiddleBiomes {

    public static Biome getBiome(BiomeParameterPoint paramPoint) {
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        int temperature = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (temperature) {
            case 0 -> getFrozen(humidity, weirdness);
            case 1 -> getCold(humidity, weirdness);
            case 2 -> getTemperate(humidity, weirdness);
            case 3 -> getWarm(humidity, weirdness);
            default -> getHot(humidity, weirdness);
        };
    }

    private static Biome getFrozen(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> weirdness == 1 ? Biome.ICE_SPIKES : Biome.SNOWY_PLAINS;
            case 1 -> Biome.SNOWY_PLAINS;
            case 2 -> weirdness == 1 ? Biome.SNOWY_TAIGA : Biome.SNOWY_PLAINS;
            case 3 -> Biome.SNOWY_TAIGA;
            default -> Biome.TAIGA;
        };
    }

    private static Biome getCold(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> BeerBiomes.PLAINS_DRY_PLAINS;
            case 1 -> BeerBiomes.PLAINS_PLAINS;
            case 2 -> BeerBiomes.FOREST_TALL_OAK;
            case 3 -> Biome.TAIGA;
            default -> weirdness == 1 ? Biome.OLD_GROWTH_PINE_TAIGA : Biome.OLD_GROWTH_SPRUCE_TAIGA;
        };
    }

    private static Biome getTemperate(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> weirdness == 1 ? Biome.SUNFLOWER_PLAINS : Biome.FLOWER_FOREST;
            case 1 -> BeerBiomes.PLAINS_PLAINS;
            case 2 -> Biome.FOREST;
            case 3 -> weirdness == 1 ? Biome.OLD_GROWTH_BIRCH_FOREST : Biome.BIRCH_FOREST;
            default -> weirdness == 1 ? BeerBiomes.PLAINS_LUSH_PLAINS : Biome.DARK_FOREST;
        };
    }

    private static Biome getWarm(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> Biome.SAVANNA;
            case 1 -> BeerBiomes.PLAINS_DRY_PLAINS;
            case 2 -> weirdness == 1 ? BeerBiomes.PLAINS_PLAINS : BeerBiomes.FOREST_LUSH_FOREST;
            case 3 -> weirdness == 1 ? Biome.SPARSE_JUNGLE : Biome.JUNGLE;
            default -> weirdness == 1 ? Biome.BAMBOO_JUNGLE : Biome.JUNGLE;
        };
    }

    private static Biome getHot(int humidity, int weirdness) {
        return humidity <= 2 ? BeerBiomes.DESERT_DRY_DESERT : BeerBiomes.DESERT_LUSH_DESERT;
    }

}
