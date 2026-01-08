package com.shanebeestudios.beer.plugin.biomes.special;

import com.shanebeestudios.beer.plugin.registration.BeerBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

public class ShatteredBiomes {

    public static @NotNull ResourceKey<Biome> getBiome(int temp, int humidity, int weirdness) {
        return switch (temp) {
            case 0, 1 -> getCold(temp, humidity, weirdness);
            case 2 -> getTemperate(temp, humidity, weirdness);
            case 3 -> getWarm(temp, humidity, weirdness);
            default -> getHot(temp, humidity, weirdness);
        };
    }

    private static @NotNull ResourceKey<Biome> getCold(int temp, int humidity, int weirdness) {
        return switch (humidity) {
            case 0, 1 -> Biomes.WINDSWEPT_GRAVELLY_HILLS;
            case 2 -> Biomes.WINDSWEPT_HILLS;
            default -> Biomes.WINDSWEPT_FOREST;
        };
    }

    private static @NotNull ResourceKey<Biome> getTemperate(int temp, int humidity, int weirdness) {
        return switch (humidity) {
            case 0, 1, 2 -> Biomes.WINDSWEPT_HILLS;
            default -> Biomes.WINDSWEPT_FOREST;
        };
    }

    private static @NotNull ResourceKey<Biome> getWarm(int temp, int humidity, int weirdness) {
        return switch (humidity) {
            case 0, 1 -> Biomes.SAVANNA;
            case 2 -> weirdness == 1 ? BeerBiomes.PLAINS_TEMPERATE_PLAINS : Biomes.FOREST;
            case 3 -> weirdness == 1 ? Biomes.SPARSE_JUNGLE : Biomes.JUNGLE;
            default -> weirdness == 1 ? Biomes.BAMBOO_JUNGLE : Biomes.JUNGLE;
        };
    }

    private static @NotNull ResourceKey<Biome> getHot(int temp, int humidity, int weirdness) {
        return humidity <= 2 ? BeerBiomes.DESERT_DRY_DESERT : BeerBiomes.DESERT_LUSH_DESERT;
    }

}
