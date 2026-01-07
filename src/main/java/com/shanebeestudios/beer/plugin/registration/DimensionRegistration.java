package com.shanebeestudios.beer.plugin.registration;

import com.shanebeestudios.beer.api.registration.DimensionDefinition;
import com.shanebeestudios.beer.api.utils.BiomeDefaults;
import com.shanebeestudios.beer.api.utils.DumpRegistry;
import com.shanebeestudios.beer.plugin.biomes.continental.CoastalBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.DeepOceanBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.FarInlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.MidInlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.NearInlandBiomes;
import com.shanebeestudios.beer.plugin.biomes.continental.OceanBiomes;
import com.shanebeestudios.beer.plugin.biomes.special.CaveBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("SameParameterValue")
public class DimensionRegistration {

    private final DimensionDefinition definition;

    public DimensionRegistration() {
        DimensionDefinition.Builder builder = DimensionDefinition.overworldBuilder(Dimensions.BEER_WORLD);

        // CAVE BIOMES
        builder.addPoint(Biomes.DRIPSTONE_CAVES, Climate.Parameter.span(0.8f, 1.0f),
            BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE,
            Climate.Parameter.span(0.2f, 0.9f), BiomeDefaults.FULL_RANGE, 0);

        builder.addPoint(Biomes.DEEP_DARK, BiomeDefaults.FULL_RANGE,
            BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, Climate.Parameter.span(-1.0f, -0.375f),
            Climate.Parameter.span(1.1f, 2.0f), BiomeDefaults.FULL_RANGE, 0);

        for (int tempIndex = 0; tempIndex < BiomeDefaults.TEMPERATURES.length; tempIndex++) {
            Climate.Parameter temp = BiomeDefaults.TEMPERATURES[tempIndex];
            for (int humidityIndex = 0; humidityIndex < BiomeDefaults.HUMIDITIES.length; humidityIndex++) {
                Climate.Parameter humidity = BiomeDefaults.HUMIDITIES[humidityIndex];

                ResourceKey<Biome> biomeKey = CaveBiomes.getBiome(tempIndex, humidityIndex);
                if (biomeKey == null) continue;

                builder.addPoint(biomeKey, BiomeDefaults.FULL_RANGE, temp, humidity, BiomeDefaults.FULL_RANGE,
                    Climate.Parameter.span(0.2f, 0.9f), BiomeDefaults.FULL_RANGE, 0);
            }
        }

        // OVERWORLD BIOMES
        for (int contIndex = 0; contIndex < BiomeDefaults.CONTINENTALNESS.length; contIndex++) {
            Climate.Parameter continentalness = BiomeDefaults.CONTINENTALNESS[contIndex];

            for (int tempIndex = 0; tempIndex < BiomeDefaults.TEMPERATURES.length; tempIndex++) {
                Climate.Parameter temp = BiomeDefaults.TEMPERATURES[tempIndex];

                for (int humidityIndex = 0; humidityIndex < BiomeDefaults.HUMIDITIES.length; humidityIndex++) {
                    Climate.Parameter humidity = BiomeDefaults.HUMIDITIES[humidityIndex];

                    for (int erosionIndex = 0; erosionIndex < BiomeDefaults.EROSIONS.length; erosionIndex++) {
                        Climate.Parameter erosion = BiomeDefaults.EROSIONS[erosionIndex];

                        for (int pvIndex = 0; pvIndex < BiomeDefaults.PVS.length; pvIndex++) {

                            for (int weirdIndex = 0; weirdIndex < BiomeDefaults.PVS[pvIndex].length; weirdIndex++) {
                                Climate.Parameter weirdness = BiomeDefaults.PVS[pvIndex][weirdIndex];

                                ResourceKey<Biome> biomeKey = getBiome(contIndex, tempIndex, humidityIndex,
                                    weirdIndex, pvIndex, erosionIndex);

                                builder.addPoint(biomeKey, continentalness, temp, humidity, erosion,
                                    BiomeDefaults.FULL_RANGE, weirdness, 0);
                            }
                        }
                    }
                }
            }
        }

        // REGISTER
        DimensionDefinition definition = builder.build();
        definition.register();
        this.definition = definition;
    }

    public void dumpToRegistry() {
        // Dump dimensions to datapack files
        DumpRegistry.dumpDefinable(this.definition);
    }

    public static @NotNull ResourceKey<Biome> getBiome(int continent, int temp, int humidity, int weirdness, int pv, int erosion) {
        return switch (continent) {
            case 0 -> Biomes.MUSHROOM_FIELDS;
            case 1 -> DeepOceanBiomes.getBiome(temp, humidity, weirdness);
            case 2 -> OceanBiomes.getBiome(temp, humidity, weirdness);
            case 3 -> CoastalBiomes.getBiome(temp, humidity, weirdness, pv, erosion);
            case 4 -> NearInlandBiomes.getBiome(temp, humidity, weirdness, pv, erosion);
            case 5 -> MidInlandBiomes.getBiome(temp, humidity, weirdness, erosion, pv);
            default -> FarInlandBiomes.getBiome(temp, humidity, weirdness, pv, erosion);
        };
    }

}
