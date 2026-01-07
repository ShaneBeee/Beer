package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.registration.BeerBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

public class PlateauBiomes {

    public static @NotNull ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness) {
        return switch (temp) {
            case 0 -> getFrozen(humidity, weirdness);
            case 1 -> getCold(humidity, weirdness);
            case 2 -> getTemperate(humidity, weirdness);
            case 3 -> getWarm(humidity, weirdness);
            default -> getHot(temp, humidity, weirdness);
        };
    }

    private static @NotNull ResourceKey<Biome> getFrozen(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> weirdness == 1 ? Biomes.ICE_SPIKES : Biomes.SNOWY_PLAINS;
            case 1, 2 -> Biomes.SNOWY_PLAINS;
            default -> Biomes.SNOWY_TAIGA;
        };
    }

    private static @NotNull ResourceKey<Biome> getCold(int humidity, int weirdness) {
        return switch (humidity) {
            case 0 -> weirdness == 1 ? Biomes.CHERRY_GROVE : Biomes.MEADOW;
            case 1 -> Biomes.MEADOW;
            case 2 -> weirdness == 1 ? Biomes.MEADOW : Biomes.FOREST;
            case 3 -> weirdness == 1 ? Biomes.MEADOW : Biomes.TAIGA;
            default -> weirdness == 1 ? Biomes.OLD_GROWTH_PINE_TAIGA : Biomes.OLD_GROWTH_SPRUCE_TAIGA;
        };
    }

    private static @NotNull ResourceKey<Biome> getTemperate(int humidity, int weirdness) {
        return switch (humidity) {
            case 0, 1 -> weirdness == 1 ? Biomes.CHERRY_GROVE : Biomes.MEADOW;
            case 2 -> weirdness == 1 ? Biomes.FOREST : BeerBiomes.FOREST_MOSS_GARDEN;
            case 3 -> weirdness == 1 ? Biomes.BIRCH_FOREST : Biomes.MEADOW;
            default -> weirdness == 1 ? Biomes.PALE_GARDEN : Biomes.DARK_FOREST;
        };
    }

    private static @NotNull ResourceKey<Biome> getWarm(int humidity, int weirdness) {
        return switch (humidity) {
            case 0, 1 -> Biomes.SAVANNA_PLATEAU;
            case 2, 3 -> Biomes.FOREST;
            default -> Biomes.JUNGLE;
        };
    }

    private static @NotNull ResourceKey<Biome> getHot(int temp, int humidity, int weirdness) {
        return BadlandBiomes.getBiome(temp, humidity, weirdness);
    }

}
