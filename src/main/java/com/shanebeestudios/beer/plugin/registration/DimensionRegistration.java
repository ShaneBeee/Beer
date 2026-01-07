package com.shanebeestudios.beer.plugin.registration;

import com.shanebeestudios.beer.api.registration.DimensionDefinition;
import com.shanebeestudios.beer.api.registration.DimensionDefinition.Builder;
import com.shanebeestudios.beer.api.utils.BiomeDefaults;
import com.shanebeestudios.beer.api.utils.DumpRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate.Parameter;

@SuppressWarnings("SameParameterValue")
public class DimensionRegistration {

    private final DimensionDefinition definition;
    private final ResourceKey<Biome>[][] MIDDLE_BIOMES;
    private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT;
    private final ResourceKey<Biome>[][] PLATEAU_BIOMES;
    private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT;
    private final ResourceKey<Biome>[][] SHATTERED_BIOMES;

    @SuppressWarnings("unchecked")
    public DimensionRegistration() {
        Builder builder = DimensionDefinition.overworldBuilder(Dimensions.BEER_WORLD);
        this.MIDDLE_BIOMES = new ResourceKey[][]{
            {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.TAIGA},
            {BeerBiomes.PLAINS_PLAINS, BeerBiomes.PLAINS_PLAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA},
            {Biomes.FLOWER_FOREST, BeerBiomes.PLAINS_LUSH_PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST},
            {Biomes.SAVANNA, Biomes.SAVANNA, Biomes.FOREST, Biomes.JUNGLE, Biomes.JUNGLE},
            {Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, BeerBiomes.DESERT_LUSH_DESERT, BeerBiomes.DESERT_LUSH_DESERT}
        };
        this.MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
            {Biomes.ICE_SPIKES, null, Biomes.SNOWY_TAIGA, null, null},
            {null, null, null, null, Biomes.OLD_GROWTH_PINE_TAIGA},
            {Biomes.SUNFLOWER_PLAINS, null, null, Biomes.OLD_GROWTH_BIRCH_FOREST, null},
            {null, null, BeerBiomes.PLAINS_LUSH_PLAINS, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE},
            {null, null, null, null, null}
        };
        this.PLATEAU_BIOMES = new ResourceKey[][]{
            {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA},
            {Biomes.MEADOW, Biomes.MEADOW, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA},
            {Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.PALE_GARDEN},
            {Biomes.SAVANNA_PLATEAU, Biomes.SAVANNA_PLATEAU, Biomes.FOREST, Biomes.FOREST, Biomes.JUNGLE},
            {Biomes.BADLANDS, Biomes.BADLANDS, Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.WOODED_BADLANDS}
        };
        this.PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
            {Biomes.ICE_SPIKES, null, null, null, null},
            {Biomes.CHERRY_GROVE, null, Biomes.MEADOW, Biomes.MEADOW, Biomes.OLD_GROWTH_PINE_TAIGA},
            {Biomes.CHERRY_GROVE, Biomes.CHERRY_GROVE, Biomes.FOREST, BeerBiomes.FOREST_LUSH_FOREST, BeerBiomes.FOREST_MOSS_GARDEN},
            {null, null, null, null, null},
            {Biomes.ERODED_BADLANDS, Biomes.ERODED_BADLANDS, null, null, null}
        };
        this.SHATTERED_BIOMES = new ResourceKey[][]{
            {Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST},
            {Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST},
            {Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST},
            {null, null, null, null, null},
            {null, null, null, null, null}
        };

        this.definition = build(builder);
    }

    public void dumpToRegistry() {
        // Dump dimensions to datapack files
        DumpRegistry.dumpDefinable(this.definition);
    }

    private DimensionDefinition build(Builder builder) {
        addNonInlandBiomes(builder);
        addInlandBiomes(builder);
        addUndergroundBiomes(builder);


        DimensionDefinition definition = builder.build();
        definition.register();
        return definition;
    }

    private void addOcean(Builder builder, ResourceKey<Biome> biome, Parameter continentalness, Parameter temp) {
        builder.addPoint(biome, continentalness, temp, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, 0);
    }

    private void addNonInlandBiomes(Builder builder) {
        Parameter ocean = BiomeDefaults.OCEAN_CONTINENTALNESS;
        Parameter deep = BiomeDefaults.DEEP_OCEAN_CONTINENTALNESS;

        addOcean(builder, Biomes.MUSHROOM_FIELDS, BiomeDefaults.MUSHROOM_FIELDS_CONTINENTALNESS, BiomeDefaults.FULL_RANGE);
        addOcean(builder, Biomes.FROZEN_OCEAN, ocean, BiomeDefaults.TEMPERATURES[0]);
        addOcean(builder, Biomes.DEEP_FROZEN_OCEAN, deep, BiomeDefaults.TEMPERATURES[0]);
        addOcean(builder, Biomes.COLD_OCEAN, ocean, BiomeDefaults.TEMPERATURES[1]);
        addOcean(builder, Biomes.DEEP_COLD_OCEAN, deep, BiomeDefaults.TEMPERATURES[1]);
        addOcean(builder, Biomes.OCEAN, ocean, BiomeDefaults.TEMPERATURES[2]);
        addOcean(builder, Biomes.DEEP_OCEAN, deep, BiomeDefaults.TEMPERATURES[2]);
        addOcean(builder, Biomes.LUKEWARM_OCEAN, ocean, BiomeDefaults.TEMPERATURES[3]);
        addOcean(builder, Biomes.DEEP_LUKEWARM_OCEAN, deep, BiomeDefaults.TEMPERATURES[3]);
        addOcean(builder, Biomes.WARM_OCEAN, ocean, BiomeDefaults.TEMPERATURES[4]);
        addOcean(builder, Biomes.WARM_OCEAN, deep, BiomeDefaults.TEMPERATURES[4]);
    }

    private void addInlandBiomes(Builder builder) {
        addMidSlice(builder, Parameter.span(-1.0F, -0.93333334F));
        addHighSlice(builder, Parameter.span(-0.93333334F, -0.7666667F));
        addPeaks(builder, Parameter.span(-0.7666667F, -0.56666666F));
        addHighSlice(builder, Parameter.span(-0.56666666F, -0.4F));
        addMidSlice(builder, Parameter.span(-0.4F, -0.26666668F));
        addLowSlice(builder, Parameter.span(-0.26666668F, -0.05F));
        addValleys(builder, Parameter.span(-0.05F, 0.05F));
        addLowSlice(builder, Parameter.span(0.05F, 0.26666668F));
        addMidSlice(builder, Parameter.span(0.26666668F, 0.4F));
        addHighSlice(builder, Parameter.span(0.4F, 0.56666666F));
        addPeaks(builder, Parameter.span(0.56666666F, 0.7666667F));
        addHighSlice(builder, Parameter.span(0.7666667F, 0.93333334F));
        addMidSlice(builder, Parameter.span(0.93333334F, 1.0F));
    }

    private ResourceKey<Biome> getMiddle(int tempIndex, int humidityIndex, Parameter weirdness) {
        if (weirdness.max() < 0L) {
            return this.MIDDLE_BIOMES[tempIndex][humidityIndex];
        } else {
            ResourceKey<Biome> biomeKey = this.MIDDLE_BIOMES_VARIANT[tempIndex][humidityIndex];

            return biomeKey == null ? this.MIDDLE_BIOMES[tempIndex][humidityIndex] : biomeKey;
        }
    }

    private void addPeaks(Builder builder, Parameter weirdness) {
        for (int tempIndex = 0; tempIndex < BiomeDefaults.TEMPERATURES.length; ++tempIndex) {
            Parameter temp = BiomeDefaults.TEMPERATURES[tempIndex];

            for (int humidityIndex = 0; humidityIndex < BiomeDefaults.HUMIDITIES.length; ++humidityIndex) {
                Parameter humidity = BiomeDefaults.HUMIDITIES[humidityIndex];
                ResourceKey<Biome> pickMiddleBiome = getMiddle(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHot = getMiddleBiomeOrBadlandsIfHot(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold = getMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> pickPlateauBiome = getPlateau(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> pickShatteredBiome = getShattered(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> maybedPickWindsweptSavannaBiome = getWindsweptSavanna(tempIndex, humidityIndex, weirdness, pickShatteredBiome);
                ResourceKey<Biome> pickPeakBiome = getPeak(tempIndex, humidityIndex, weirdness);

                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[0], weirdness, 0, pickPeakBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[1], weirdness, 0, pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[1], weirdness, 0, pickPeakBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS), Parameter.span(BiomeDefaults.EROSIONS[2], BiomeDefaults.EROSIONS[3]), weirdness, 0, pickMiddleBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[2], weirdness, 0, pickPlateauBiome);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[3], weirdness, 0, pickMiddleBiomeOrBadlandsIfHot);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.FAR_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[3], weirdness, 0, pickPlateauBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[4], weirdness, 0, pickMiddleBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[5], weirdness, 0, maybedPickWindsweptSavannaBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[5], weirdness, 0, pickShatteredBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[6], weirdness, 0, pickMiddleBiome);
            }
        }
    }

    private void addHighSlice(Builder builder, Parameter weirdness) {
        for (int tempIndex = 0; tempIndex < BiomeDefaults.TEMPERATURES.length; ++tempIndex) {
            Parameter temp = BiomeDefaults.TEMPERATURES[tempIndex];

            for (int humidityIndex = 0; humidityIndex < BiomeDefaults.HUMIDITIES.length; ++humidityIndex) {
                Parameter humidity = BiomeDefaults.HUMIDITIES[humidityIndex];
                ResourceKey<Biome> resourcekey = getMiddle(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey1 = getMiddleBiomeOrBadlandsIfHot(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey2 = getMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey3 = getPlateau(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey4 = getShattered(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey5 = getWindsweptSavanna(tempIndex, humidityIndex, weirdness, resourcekey);
                ResourceKey<Biome> resourcekey6 = getSlope(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey7 = getPeak(tempIndex, humidityIndex, weirdness);

                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.COAST_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[1]), weirdness, 0, resourcekey);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[0], weirdness, 0, resourcekey6);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[0], weirdness, 0, resourcekey7);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[1], weirdness, 0, resourcekey2);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[1], weirdness, 0, resourcekey6);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS), Parameter.span(BiomeDefaults.EROSIONS[2], BiomeDefaults.EROSIONS[3]), weirdness, 0, resourcekey);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[2], weirdness, 0, resourcekey3);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[3], weirdness, 0, resourcekey1);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.FAR_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[3], weirdness, 0, resourcekey3);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[4], weirdness, 0, resourcekey);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[5], weirdness, 0, resourcekey5);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[5], weirdness, 0, resourcekey4);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[6], weirdness, 0, resourcekey);
            }
        }
    }

    private void addMidSlice(Builder builder, Parameter weirdness) {
        addSurfaceBiome(builder, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.COAST_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[2]), weirdness, 0, Biomes.STONY_SHORE);

        for (int tempIndex = 0; tempIndex < BiomeDefaults.TEMPERATURES.length; ++tempIndex) {
            Parameter temp = BiomeDefaults.TEMPERATURES[tempIndex];

            for (int humidityIndex = 0; humidityIndex < BiomeDefaults.HUMIDITIES.length; ++humidityIndex) {
                Parameter humidity = BiomeDefaults.HUMIDITIES[humidityIndex];
                ResourceKey<Biome> middleBiome = getMiddle(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> middleBiomeOrBadlandsIfHot = getMiddleBiomeOrBadlandsIfHot(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> middleBiomeOrBadlandsIfHotOrSlopeIfCold = getMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> shatteredBiome = getShattered(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> plateauBiome = getPlateau(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> beachBiome = getBeach(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> windsweptSavannaBiome = getWindsweptSavanna(tempIndex, humidityIndex, weirdness, middleBiome);
                ResourceKey<Biome> shatteredCoastBiome = getShatteredCoast(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> slopeBiome = getSlope(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> swampBiome = getSwampBiome(tempIndex, humidityIndex, weirdness);


                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[0], weirdness, 0, slopeBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.MID_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[1], weirdness, 0, middleBiomeOrBadlandsIfHotOrSlopeIfCold);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.FAR_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[1], weirdness, 0, tempIndex == 0 ? slopeBiome : plateauBiome);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[2], weirdness, 0, middleBiome);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[2], weirdness, 0, middleBiomeOrBadlandsIfHot);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.FAR_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[2], weirdness, 0, plateauBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[3], weirdness, 0, middleBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[3], weirdness, 0, middleBiomeOrBadlandsIfHot);
                if (weirdness.max() < 0L) {
                    addSurfaceBiome(builder, temp, humidity, BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.EROSIONS[4], weirdness, 0, beachBiome);
                    addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[4], weirdness, 0, middleBiome);
                } else {
                    addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[4], weirdness, 0, middleBiome);
                }

                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.EROSIONS[5], weirdness, 0, shatteredCoastBiome);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[5], weirdness, 0, windsweptSavannaBiome);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[5], weirdness, 0, shatteredBiome);
                if (weirdness.max() < 0L) {
                    addSurfaceBiome(builder, temp, humidity, BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.EROSIONS[6], weirdness, 0, beachBiome);
                } else {
                    addSurfaceBiome(builder, temp, humidity, BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.EROSIONS[6], weirdness, 0, middleBiome);
                }

                if (tempIndex == 0) {
                    addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[6], weirdness, 0, middleBiome);
                }

                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[6], weirdness, 0, swampBiome);
            }
        }
    }

    private void addLowSlice(Builder builder, Parameter weirdness) {
        addSurfaceBiome(builder, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.COAST_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[2]), weirdness, 0, Biomes.STONY_SHORE);

        for (int tempIndex = 0; tempIndex < BiomeDefaults.TEMPERATURES.length; ++tempIndex) {
            Parameter temp = BiomeDefaults.TEMPERATURES[tempIndex];

            for (int humidityIndex = 0; humidityIndex < BiomeDefaults.HUMIDITIES.length; ++humidityIndex) {
                Parameter humidity = BiomeDefaults.HUMIDITIES[humidityIndex];
                ResourceKey<Biome> resourcekey = getMiddle(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey1 = getMiddleBiomeOrBadlandsIfHot(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey2 = getMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey3 = getBeach(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> resourcekey4 = getWindsweptSavanna(tempIndex, humidityIndex, weirdness, resourcekey);
                ResourceKey<Biome> resourcekey5 = getShatteredCoast(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> swampBiome = getSwampBiome(tempIndex, humidityIndex, weirdness);

                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[1]), weirdness, 0, resourcekey1);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[1]), weirdness, 0, resourcekey2);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[2], BiomeDefaults.EROSIONS[3]), weirdness, 0, resourcekey);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), Parameter.span(BiomeDefaults.EROSIONS[2], BiomeDefaults.EROSIONS[3]), weirdness, 0, resourcekey1);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.COAST_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[3], BiomeDefaults.EROSIONS[4]), weirdness, 0, resourcekey3);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[4], weirdness, 0, resourcekey);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.EROSIONS[5], weirdness, 0, resourcekey5);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.EROSIONS[5], weirdness, 0, resourcekey4);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[5], weirdness, 0, resourcekey);
                addSurfaceBiome(builder, temp, humidity, BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.EROSIONS[6], weirdness, 0, resourcekey3);
                if (tempIndex == 0) {
                    addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[6], weirdness, 0, resourcekey);
                }
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[6], weirdness, 0, swampBiome);
            }
        }
    }

    private void addValleys(Builder builder, Parameter weirdness) {
        addSurfaceBiome(builder, BiomeDefaults.TEMPERATURES[0], BiomeDefaults.FULL_RANGE, BiomeDefaults.COAST_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[1]), weirdness, 0, weirdness.max() < 0L ? Biomes.STONY_SHORE : Biomes.FROZEN_RIVER);
        addSurfaceBiome(builder, BiomeDefaults.UNFROZEN_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.COAST_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[1]), weirdness, 0, weirdness.max() < 0L ? Biomes.STONY_SHORE : Biomes.RIVER);
        addSurfaceBiome(builder, BiomeDefaults.TEMPERATURES[0], BiomeDefaults.FULL_RANGE, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[1]), weirdness, 0, Biomes.FROZEN_RIVER);
        addSurfaceBiome(builder, BiomeDefaults.UNFROZEN_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.NEAR_INLAND_CONTINENTALNESS, Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[1]), weirdness, 0, Biomes.RIVER);
        addSurfaceBiome(builder, BiomeDefaults.TEMPERATURES[0], BiomeDefaults.FULL_RANGE, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), Parameter.span(BiomeDefaults.EROSIONS[2], BiomeDefaults.EROSIONS[5]), weirdness, 0, Biomes.FROZEN_RIVER);
        addSurfaceBiome(builder, BiomeDefaults.UNFROZEN_RANGE, BiomeDefaults.FULL_RANGE, Parameter.span(BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), Parameter.span(BiomeDefaults.EROSIONS[2], BiomeDefaults.EROSIONS[5]), weirdness, 0, Biomes.RIVER);
        addSurfaceBiome(builder, BiomeDefaults.TEMPERATURES[0], BiomeDefaults.FULL_RANGE, BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.EROSIONS[6], weirdness, 0, Biomes.FROZEN_RIVER);
        addSurfaceBiome(builder, BiomeDefaults.UNFROZEN_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.COAST_CONTINENTALNESS, BiomeDefaults.EROSIONS[6], weirdness, 0, Biomes.RIVER);

        addSurfaceBiome(builder, BiomeDefaults.TEMPERATURES[0], BiomeDefaults.FULL_RANGE, Parameter.span(BiomeDefaults.INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[6], weirdness, 0, Biomes.FROZEN_RIVER);

        for (int tempIndex = 0; tempIndex < BiomeDefaults.TEMPERATURES.length; ++tempIndex) {
            Parameter temp = BiomeDefaults.TEMPERATURES[tempIndex];

            for (int humidityIndex = 0; humidityIndex < BiomeDefaults.HUMIDITIES.length; ++humidityIndex) {
                Parameter humidity = BiomeDefaults.HUMIDITIES[humidityIndex];
                ResourceKey<Biome> middleBiomeKey = getMiddleBiomeOrBadlandsIfHot(tempIndex, humidityIndex, weirdness);
                ResourceKey<Biome> swampBiomeKey = getSwampBiome(tempIndex, humidityIndex, weirdness);

                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.MID_INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[1]), weirdness, 0, middleBiomeKey);
                addSurfaceBiome(builder, temp, humidity, Parameter.span(BiomeDefaults.INLAND_CONTINENTALNESS, BiomeDefaults.FAR_INLAND_CONTINENTALNESS), BiomeDefaults.EROSIONS[6], weirdness, 0, swampBiomeKey);
            }
        }
    }

    private void addUndergroundBiomes(Builder builder) {
        addUndergroundBiome(builder, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, Parameter.span(0.8F, 1.0F), BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, 0, Biomes.DRIPSTONE_CAVES);
        addUndergroundBiome(builder, BiomeDefaults.FULL_RANGE, Parameter.span(0.7F, 1.0F), BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, 0, Biomes.LUSH_CAVES);
        addBottomBiome(builder, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, BiomeDefaults.FULL_RANGE, Parameter.span(BiomeDefaults.EROSIONS[0], BiomeDefaults.EROSIONS[1]), BiomeDefaults.FULL_RANGE, 0, Biomes.DEEP_DARK);
    }

    private ResourceKey<Biome> getSwampBiome(int tempIndex, int humityIndex, Parameter weirdness) {
        if (tempIndex <= 2) {
            return Biomes.SWAMP;
        }
        if (humityIndex <= 2) {
            return Biomes.MANGROVE_SWAMP;
        }
        return BeerBiomes.SWAMP_DRIPLEAF_SWAMP;
    }

    private ResourceKey<Biome> getMiddleBiomeOrBadlandsIfHot(int temperatureIndex, int humidityIndex, Parameter weirdness) {
        return temperatureIndex == 4 ? getBadland(humidityIndex, weirdness) : getMiddle(temperatureIndex, humidityIndex, weirdness);
    }

    private ResourceKey<Biome> getMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(int temperatureIndex, int humidityIndex, Parameter weirdness) {
        return temperatureIndex == 0 ? getSlope(temperatureIndex, humidityIndex, weirdness) : getMiddleBiomeOrBadlandsIfHot(temperatureIndex, humidityIndex, weirdness);
    }

    private ResourceKey<Biome> getWindsweptSavanna(int temperatureIndex, int humidityIndex, Parameter weirdness, ResourceKey<Biome> underlyingBiome) {
        return temperatureIndex > 1 && humidityIndex < 4 && weirdness.max() >= 0L ? Biomes.WINDSWEPT_SAVANNA : underlyingBiome;
    }

    private ResourceKey<Biome> getShatteredCoast(int temperatureIndex, int humidityIndex, Parameter weirdness) {
        ResourceKey<Biome> resourcekey = weirdness.max() >= 0L ? getMiddle(temperatureIndex, humidityIndex, weirdness) : getBeach(temperatureIndex, humidityIndex, weirdness);

        return getWindsweptSavanna(temperatureIndex, humidityIndex, weirdness, resourcekey);
    }

    private ResourceKey<Biome> getBeach(int temperatureIndex, int humidityIndex, Parameter weirdness) {
        return switch (temperatureIndex) {
            case 0, 1 -> BeerBiomes.COAST_FROZEN_BEACH;
            case 2 -> weirdness.max() < 0 ? BeerBiomes.COAST_COAST : BeerBiomes.COAST_BEACHY_COAST;
            case 3 -> humidityIndex <= 2 ? BeerBiomes.COAST_PALM_BEACH : BeerBiomes.COAST_LUSH_COAST;
            default -> humidityIndex <= 2 ? BeerBiomes.COAST_DRY_COAST : BeerBiomes.COAST_LUSH_COAST;
        };
    }

    private ResourceKey<Biome> getBadland(int humidityIndex, Parameter weirdness) {
        return humidityIndex < 2 ? (weirdness.max() < 0L ? Biomes.BADLANDS : Biomes.ERODED_BADLANDS) : (humidityIndex < 3 ? Biomes.BADLANDS : Biomes.WOODED_BADLANDS);
    }

    private ResourceKey<Biome> getPlateau(int temperatureIndex, int humidityIndex, Parameter weirdness) {
        if (weirdness.max() >= 0L) {
            ResourceKey<Biome> resourcekey = this.PLATEAU_BIOMES_VARIANT[temperatureIndex][humidityIndex];

            if (resourcekey != null) {
                return resourcekey;
            }
        }

        return this.PLATEAU_BIOMES[temperatureIndex][humidityIndex];
    }

    private ResourceKey<Biome> getPeak(int temperatureIndex, int humidityIndex, Parameter weirdness) {
        return temperatureIndex <= 2 ? (weirdness.max() < 0L ? Biomes.JAGGED_PEAKS : Biomes.FROZEN_PEAKS) : (temperatureIndex == 3 ? Biomes.STONY_PEAKS : getBadland(humidityIndex, weirdness));
    }

    private ResourceKey<Biome> getSlope(int temperatureIndex, int humidityIndex, Parameter weirdness) {
        return temperatureIndex >= 3 ? getPlateau(temperatureIndex, humidityIndex, weirdness) : (humidityIndex <= 1 ? Biomes.SNOWY_SLOPES : Biomes.GROVE);
    }

    private ResourceKey<Biome> getShattered(int temperatureIndex, int humidityIndex, Parameter weirdness) {
        ResourceKey<Biome> resourcekey = this.SHATTERED_BIOMES[temperatureIndex][humidityIndex];

        return resourcekey == null ? getMiddle(temperatureIndex, humidityIndex, weirdness) : resourcekey;
    }

    private void addSurfaceBiome(Builder builder, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, long offset, ResourceKey<Biome> biomeKey) {
        builder.addPoint(biomeKey, continentalness, temperature, humidity, erosion, Parameter.point(0), weirdness, offset);
        builder.addPoint(biomeKey, continentalness, temperature, humidity, erosion, Parameter.point(1.0F), weirdness, offset);
    }

    private void addUndergroundBiome(Builder builder, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, long offset, ResourceKey<Biome> biomeKey) {
        builder.addPoint(biomeKey, continentalness, temperature, humidity, erosion, Parameter.span(0.2F, 0.9F), weirdness, offset);
    }

    private void addBottomBiome(Builder builder, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness, long offset, ResourceKey<Biome> biomeKey) {
        builder.addPoint(biomeKey, continentalness, temperature, humidity, erosion, Parameter.point(1.1F), weirdness, offset)
        ;
    }

}
