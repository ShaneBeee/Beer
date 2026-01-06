package com.shanebeestudios.beer.api.utils;

import net.minecraft.world.level.biome.Climate.Parameter;

public class BiomeDefaults {

    public static final Parameter FULL_RANGE = Parameter.span(-1.0F, 1.0F);

    public static final Parameter[] TEMPERATURES = new Parameter[]{
        Parameter.span(-1.0F, -0.45F),
        Parameter.span(-0.45F, -0.15F),
        Parameter.span(-0.15F, 0.2F),
        Parameter.span(0.2F, 0.55F),
        Parameter.span(0.55F, 1.0F)
    };

    public static final Parameter[] HUMIDITIES = new Parameter[]{
        Parameter.span(-1.0F, -0.35F),
        Parameter.span(-0.35F, -0.1F),
        Parameter.span(-0.1F, 0.1F),
        Parameter.span(0.1F, 0.3F),
        Parameter.span(0.3F, 1.0F)
    };

    public static final Parameter[] EROSIONS = new Parameter[]{
        Parameter.span(-1.0F, -0.78F),
        Parameter.span(-0.78F, -0.375F),
        Parameter.span(-0.375F, -0.2225F),
        Parameter.span(-0.2225F, 0.05F),
        Parameter.span(0.05F, 0.45F),
        Parameter.span(0.45F, 0.55F),
        Parameter.span(0.55F, 1.0F)
    };
    public static final Parameter UNFROZEN_RANGE = Parameter.span(TEMPERATURES[1], TEMPERATURES[4]);

    public static final Parameter MUSHROOM_FIELDS_CONTINENTALNESS = Parameter.span(-1.2F, -1.05F);
    public static final Parameter DEEP_OCEAN_CONTINENTALNESS = Parameter.span(-1.05F, -0.455F);
    public static final Parameter OCEAN_CONTINENTALNESS = Parameter.span(-0.455F, -0.19F);
    public static final Parameter COAST_CONTINENTALNESS = Parameter.span(-0.19F, -0.11F);
    public static final Parameter INLAND_CONTINENTALNESS = Parameter.span(-0.11F, 0.55F);
    public static final Parameter NEAR_INLAND_CONTINENTALNESS = Parameter.span(-0.11F, 0.03F);
    public static final Parameter MID_INLAND_CONTINENTALNESS = Parameter.span(0.03F, 0.3F);
    public static final Parameter FAR_INLAND_CONTINENTALNESS = Parameter.span(0.3F, 1.0F);

}
