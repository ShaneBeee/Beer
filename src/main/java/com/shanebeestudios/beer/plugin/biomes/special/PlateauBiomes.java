package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.biomes.BeerBiomesOld;
import com.shanebeestudios.beer.api.utils.ParamPoints;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;
import org.jetbrains.annotations.NotNull;

public class PlateauBiomes {

    public static @NotNull Biome getBiome(BiomeParameterPoint paramPoint) {
        int humidity = ParamPoints.HUMIDITY.getFixedPoint(paramPoint);
        int temperature = ParamPoints.TEMPERATURE.getFixedPoint(paramPoint);
        int weirdness = ParamPoints.WEIRDNESS.getFixedPoint(paramPoint);
        return switch (temperature) {
            case 0 -> getFrozen(humidity, weirdness);
            case 1 -> getCold(humidity, weirdness);
            case 2 -> getTemperate(humidity, weirdness);
            case 3 -> getWarm(humidity, weirdness);
            default -> getHot(paramPoint);
        };
    }

    private static @NotNull Biome getFrozen(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> weirdness == 1 ? Biome.ICE_SPIKES : Biome.SNOWY_PLAINS;
            case 1, 2 -> Biome.SNOWY_PLAINS;
            default -> Biome.SNOWY_TAIGA;
        };
    }

    private static @NotNull Biome getCold(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> weirdness == 1 ? Biome.CHERRY_GROVE : Biome.MEADOW;
            case 1 -> Biome.MEADOW;
            case 2 -> weirdness == 1 ? Biome.MEADOW : Biome.FOREST;
            case 3 -> weirdness == 1 ? Biome.MEADOW : Biome.TAIGA;
            default -> weirdness == 1 ? Biome.OLD_GROWTH_PINE_TAIGA : Biome.OLD_GROWTH_SPRUCE_TAIGA;
        };
    }

    private static @NotNull Biome getTemperate(int humidity, int weirdness) {
        return switch (humidity) {
            case 0, 1 -> weirdness == 1 ? Biome.CHERRY_GROVE : Biome.MEADOW;
            case 2 -> weirdness == 1 ? Biome.FOREST : BeerBiomesOld.FOREST_MOSS_GARDEN;
            case 3 -> weirdness == 1 ? Biome.BIRCH_FOREST : Biome.MEADOW;
            default -> weirdness == 1 ? Biome.PALE_GARDEN : Biome.DARK_FOREST;
        };
    }

    private static @NotNull Biome getWarm(int humidity, int weirdness) {
        return switch (humidity) {
            case 0, 1 -> Biome.SAVANNA_PLATEAU;
            case 2, 3 -> Biome.FOREST;
            default -> Biome.JUNGLE;
        };
    }

    private static @NotNull Biome getHot(BiomeParameterPoint paramPoint) {
        return BadlandBiomes.getBiome(paramPoint);
    }

}
