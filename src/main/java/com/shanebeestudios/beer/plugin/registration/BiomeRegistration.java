package com.shanebeestudios.beer.plugin.registration;

import com.shanebeestudios.beer.api.registration.BiomeDefinition;
import com.shanebeestudios.beer.api.utils.DumpRegistry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.attribute.AmbientMoodSettings;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BiomeRegistration {

    private static final List<BiomeDefinition> BIOMES = new ArrayList<>();

    public static void registerBiomes() {
        BIOMES.addAll(caveBiomes());
        BIOMES.addAll(coastBiomes());
        BIOMES.addAll(desertBiomes());
        BIOMES.addAll(forestBiomes());
        BIOMES.addAll(plainsBiomes());
        BIOMES.addAll(riverBiomes());
        BIOMES.addAll(swampBiomes());
    }

    public static void dumpToRegistry() {
        // Dump biomes to datapack files
        DumpRegistry.dumpDefinables(BIOMES);
        DumpRegistry.dumpDefinablesTags(BIOMES);
    }

    private static List<BiomeDefinition> caveBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition dry_cave = BiomeDefinition.builder(Biomes.CAVE_DRY_CAVE)
            .temperature(0.5f)
            .downfall(0.5f)
            .hasPrecipitation(true)

            // Special Effects
            .waterColor(4566514)

            // Attributes
            // Colors
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 8103167)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 267827)

            // Sounds
            .setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, new AmbientSounds(
                Optional.of(SoundEvents.AMBIENT_CAVE),
                Optional.of(AmbientMoodSettings.LEGACY_CAVE_SETTINGS),
                List.of()
            ))
            .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(
                new Music(SoundEvents.MUSIC_BIOME_LUSH_CAVES, 12000, 24000, false)))
            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)

            // Particles
            .particle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), 0.005f)

            // Carvers
            .addDefaultOverworldCarvers()

            // Features
            .addDefaultUndergroundOreFeatures()
            .features(null,
                List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface",
                    PlacedFeatures.DELTA_DRY_CAVE_DELTA,
                    PlacedFeatures.TERRAIN_BROWN_CONCRETE_DISK),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                null,
                null,
                null,
                null,
                List.of("minecraft:spring_water", "minecraft:spring_lava"),
                List.of("minecraft:patch_tall_grass_2",
                    PlacedFeatures.DECOR_HANGING_FENCE),
                List.of("minecraft:freeze_top_layer")
            )

            // Spawners
            .addMobSpawn(MobCategory.AMBIENT, EntityType.BAT, 10, 8, 8)
            .addMobSpawn(MobCategory.AXOLOTLS, EntityType.AXOLOTL, 10, 4, 6)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 100, 4, 4)
            .addMobSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.TROPICAL_FISH, 25, 8, 8)

            .addToTag(BiomeTags.IS_OVERWORLD)

            .build();
        dry_cave.register();
        biomes.add(dry_cave);

        BiomeDefinition ice_cave = BiomeDefinition.builder(Biomes.CAVE_ICE_CAVE)
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(0.8f)

            .waterColor(6003155)
            .foliageColorOverride(442658)
            .grassColorOverride(6017902)

            .setAttribute(EnvironmentAttributes.SKY_COLOR, 5634012)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 16564102)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 8846572)
            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)

            .particle(ParticleTypes.ASH, 0.5f)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.DELTA_COASTAL_DELTA,
                    PlacedFeatures.TERRAIN_STONE_TO_ICE),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:freeze_top_layer"))

            .addMobSpawn(MobCategory.MONSTER, EntityType.ILLUSIONER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 3, 1, 1)
            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 10, 1, 2)
            .addMobSpawn(MobCategory.CREATURE, EntityType.ARMADILLO, 10, 1, 2)

            .addToTag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS, BiomeTags.IS_OVERWORLD)

            .build();
        ice_cave.register();
        biomes.add(ice_cave);

        return biomes;

    }

    private static List<BiomeDefinition> coastBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition beachy_beach = BiomeDefinition.builder(Biomes.COAST_BEACHY_COAST)
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.4f)

            .waterColor(4159204)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_DIORITE_CLIFFS,
                    PlacedFeatures.TERRAIN_GRASS_TO_SAND),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(PlacedFeatures.TREE_BEACHY_PALM,
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane"),
                null)

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 15, 2, 2)

            .addToTag(BiomeTags.IS_BEACH, BiomeTags.HAS_SHIPWRECK_BEACHED, BiomeTags.IS_OVERWORLD)

            .build();
        beachy_beach.register();
        biomes.add(beachy_beach);

        BiomeDefinition coast = BiomeDefinition.builder(Biomes.COAST_COAST)
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(0.5f)
            .waterColor(6003155)
            .foliageColorOverride(442658)
            .grassColorOverride(11060330)

            .particle(ParticleTypes.CHERRY_LEAVES, 0.0005f)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 5634012)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 16564102)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 8846572)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_DIORITE_CLIFFS,
                    PlacedFeatures.DELTA_COASTAL_DELTA),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(PlacedFeatures.VEGETATION_PATCH_WATER_LEAVES,
                    PlacedFeatures.TREE_PALM_BEACH_PALM,
                    "wythers:vegetation/placed_random_patch/large_ferns_dense_forests",
                    PlacedFeatures.VEGETATION_AZALEA_BUSH_OR_SCRUB,
                    "wythers:vegetation/placed_random_patch/dark_oak_roots",
                    "wythers:terrain/placed_random_patch/mossify_grass",
                    "wythers:vegetation/bushes_mediterranean",
                    "wythers:vegetation/placed_random_patch/mediterranean_lilacs",
                    "minecraft:patch_grass_savanna",
                    "minecraft:seagrass_normal"),
                null)

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.MONSTER, EntityType.ILLUSIONER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 3, 1, 1)
            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 10, 1, 2)
            .addMobSpawn(MobCategory.CREATURE, EntityType.ARMADILLO, 10, 1, 2)

            .addToTag(BiomeTags.IS_BEACH, BiomeTags.HAS_SHIPWRECK_BEACHED, BiomeTags.IS_OVERWORLD)

            .build();
        coast.register();
        biomes.add(coast);

        BiomeDefinition dry_coast = BiomeDefinition.builder(Biomes.COAST_DRY_COAST)
            .hasPrecipitation(false)
            .temperature(2.0f)
            .downfall(0.05f)
            .waterColor(4159204)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_DIORITE_CLIFFS),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(PlacedFeatures.TREE_PALM_BEACH_PALM,
                    "wythers:vegetation/placed_random_patch/dark_oak_roots",
                    "wythers:terrain/placed_random_patch/mossify_grass",
                    "minecraft:patch_grass_savanna"),
                null)

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.MONSTER, EntityType.ILLUSIONER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 3, 1, 1)
            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 10, 1, 2)
            .addMobSpawn(MobCategory.CREATURE, EntityType.ARMADILLO, 10, 1, 2)

            .addToTag(BiomeTags.IS_BEACH, BiomeTags.HAS_SHIPWRECK_BEACHED, BiomeTags.IS_OVERWORLD)

            .build();
        dry_coast.register();
        biomes.add(dry_coast);

        BiomeDefinition frozen_beach = BiomeDefinition.builder(Biomes.COAST_FROZEN_BEACH)
            .hasPrecipitation(true)
            .temperature(0.1f)
            .downfall(0.0f)
            .waterColor(4020182)

            .particle(ParticleTypes.WHITE_ASH, 0.2f)

            .setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS,
                new AmbientSounds(
                    Optional.empty(),
                    Optional.of(new AmbientMoodSettings(SoundEvents.AMBIENT_CAVE,
                        6000, 8, 2.0f)),
                    List.of()))

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 8364543)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_DIORITE_CLIFFS,
                    PlacedFeatures.TERRAIN_GRASS_TO_SAND),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(PlacedFeatures.TREE_PALM_BEACH_PALM,
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane"),
                List.of("minecraft:freeze_top_layer"))

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 15, 2, 2)

            .addToTag(BiomeTags.IS_BEACH, BiomeTags.HAS_SHIPWRECK_BEACHED, BiomeTags.SPAWNS_COLD_VARIANT_FROGS, BiomeTags.IS_OVERWORLD)

            .build();
        frozen_beach.register();
        biomes.add(frozen_beach);

        BiomeDefinition lush_coast = BiomeDefinition.builder(Biomes.COAST_LUSH_COAST)
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(0.8f)
            .waterColor(6003155)
            .foliageColorOverride(442658)
            .grassColorOverride(6017902)

            .particle(ParticleTypes.CHERRY_LEAVES, 0.0005f)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 5634012)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 16564102)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 8846572)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_DIORITE_CLIFFS,
                    PlacedFeatures.DELTA_COASTAL_DELTA),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(PlacedFeatures.VEGETATION_PATCH_WATER_LEAVES,
                    PlacedFeatures.TREE_PALM_BEACH_PALM,
                    "wythers:vegetation/placed_random_patch/large_ferns_dense_forests",
                    PlacedFeatures.VEGETATION_AZALEA_BUSH_OR_SCRUB,
                    "wythers:vegetation/placed_random_patch/dark_oak_roots",
                    "wythers:terrain/placed_random_patch/mossify_grass",
                    "wythers:vegetation/bushes_mediterranean",
                    "wythers:vegetation/placed_random_patch/mediterranean_lilacs",
                    "wythers:vegetation/placed_random_patch/dripleaves_bayou",
                    "minecraft:patch_grass_savanna",
                    "minecraft:seagrass_normal"),
                null)

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 3, 1, 1)
            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 10, 1, 2)

            .addToTag(BiomeTags.IS_BEACH, BiomeTags.HAS_SHIPWRECK_BEACHED, BiomeTags.SPAWNS_WARM_VARIANT_FROGS, BiomeTags.IS_OVERWORLD)

            .build();
        lush_coast.register();
        biomes.add(lush_coast);

        BiomeDefinition palm_beach = BiomeDefinition.builder(Biomes.COAST_PALM_BEACH)
            .hasPrecipitation(false)
            .temperature(2.0f)
            .downfall(0.0f)
            .foliageColorOverride(442658)
            .grassColorOverride(6017902)
            .waterColor(6003155)

            .setAttribute(EnvironmentAttributes.SKY_COLOR, 5634012)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 16564102)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 8846572)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_DIORITE_CLIFFS,
                    PlacedFeatures.TERRAIN_GRASS_TO_SAND,
                    PlacedFeatures.DELTA_BEACH_DELTA),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(PlacedFeatures.TREE_PALM_BEACH_PALM,
                    "minecraft:patch_waterlily",
                    PlacedFeatures.VEGETATION_PATCH_SMALL_DRIPLEAF),
                null)

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 15, 2, 2)

            .addToTag(BiomeTags.IS_BEACH, BiomeTags.HAS_SHIPWRECK_BEACHED, BiomeTags.SPAWNS_WARM_VARIANT_FROGS, BiomeTags.IS_OVERWORLD)

            .build();

        palm_beach.register();
        biomes.add(palm_beach);

        return biomes;
    }

    private static List<BiomeDefinition> desertBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition dry_desert = BiomeDefinition.builder(Biomes.DESERT_DRY_DESERT)
            .hasPrecipitation(false)
            .temperature(2.0f)
            .downfall(0.0f)

            .waterColor(4112789)
            .foliageColorOverride(9285927)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEvents.MUSIC_BIOME_DESERT))
            .setAttribute(EnvironmentAttributes.SNOW_GOLEM_MELTS, true)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7788235)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 13880215)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 2326625)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_GRASS_TO_SAND),
                List.of("minecraft:lake_lava_underground"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:fossil_upper",
                    "minecraft:fossil_lower",
                    "minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                List.of("minecraft:desert_well"),
                null,
                null,
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("minecraft:flower_default",
                    "minecraft:patch_grass_badlands",
                    "minecraft:patch_dry_grass_desert",
                    "minecraft:patch_dead_bush_2",
                    "minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:patch_sugar_cane_desert",
                    "minecraft:patch_pumpkin",
                    "minecraft:patch_cactus_desert"),
                List.of("minecraft:freeze_top_layer"))

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.AMBIENT, EntityType.BAT, 10, 8, 8)
            .addMobSpawn(MobCategory.CREATURE, EntityType.RABBIT, 12, 2, 3)
            .addMobSpawn(MobCategory.CREATURE, EntityType.CAMEL, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 19, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 50, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.HUSK, 80, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.PARCHED, 50, 4, 4)

            .addToTag(BiomeTags.HAS_VILLAGE_DESERT, BiomeTags.HAS_DESERT_PYRAMID, BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS, BiomeTags.IS_OVERWORLD)

            .build();
        dry_desert.register();
        biomes.add(dry_desert);

        BiomeDefinition lush_desert = BiomeDefinition.builder(Biomes.DESERT_LUSH_DESERT)
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(0.8f)

            .waterColor(6003155)
            .foliageColorOverride(442658)
            .grassColorOverride(6017902)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEvents.MUSIC_BIOME_DESERT))
            .setAttribute(EnvironmentAttributes.SNOW_GOLEM_MELTS, true)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7788235)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 13880215)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 2326625)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_GRASS_TO_SAND),
                List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:fossil_upper",
                    "minecraft:fossil_lower",
                    "minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                List.of("minecraft:desert_well"),
                null,
                null,
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of(PlacedFeatures.TREE_LUSH_DESERT_PALM,
                    PlacedFeatures.VEGETATION_LUSH_DESERT_AZALEA_SCRUB,
                    "wythers:vegetation/placed_random_patch/dark_oak_roots",
                    "wythers:terrain/placed_random_patch/mossify_grass",
                    "minecraft:flower_default",
                    "minecraft:patch_grass_badlands",
                    "minecraft:patch_dry_grass_desert",
                    "minecraft:patch_dead_bush_2",
                    "minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:patch_sugar_cane_desert",
                    "minecraft:patch_pumpkin",
                    "minecraft:patch_cactus_desert"),
                List.of("minecraft:freeze_top_layer"))

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.AMBIENT, EntityType.BAT, 10, 8, 8)
            .addMobSpawn(MobCategory.CREATURE, EntityType.RABBIT, 12, 2, 3)
            .addMobSpawn(MobCategory.CREATURE, EntityType.CAMEL, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 19, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 50, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.HUSK, 80, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.PARCHED, 50, 4, 4)

            .addToTag(BiomeTags.HAS_VILLAGE_DESERT, BiomeTags.HAS_DESERT_PYRAMID, BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS, BiomeTags.IS_OVERWORLD)

            .build();
        lush_desert.register();
        biomes.add(lush_desert);

        return biomes;
    }

    private static List<BiomeDefinition> forestBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition moss_garden = BiomeDefinition.builder(Biomes.FOREST_MOSS_GARDEN)
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(0.8f)
            .foliageColorOverride(55551)
            .grassColorOverride(6980207)
            .waterColor(7768221)

            .particle(ParticleTypes.FIREFLY, 0.01f)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 0.0f)
            .setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, new AmbientSounds(
                Optional.empty(),
                Optional.of(new AmbientMoodSettings(SoundEvents.AMBIENT_CAVE, 6000, 8, 2.0f)),
                List.of()))
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 12171705)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 8484720)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 5597568)

            .addDefaultUndergroundOreFeatures()
            .features(null, List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                null,
                null,
                null,
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of(PlacedFeatures.TREE_MOSS_GARDEN,
                    PlacedFeatures.TREE_FALLEN_WARPED_STEM,
                    PlacedFeatures.VEGETATION_MOSS_PATCH,
                    "minecraft:patch_grass_forest"),
                List.of("minecraft:freeze_top_layer"))

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.AMBIENT, EntityType.BAT, 10, 8, 8)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)
            .addMobSpawn(MobCategory.WATER_CREATURE, EntityType.GLOW_SQUID, 10, 4, 6)

            .addToTag(BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD)

            .build();
        moss_garden.register();
        biomes.add(moss_garden);

        BiomeDefinition tall_oak = BiomeDefinition.builder(Biomes.FOREST_TALL_OAK)
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(0.8f)

            .addDefaultUndergroundOreFeatures()
            .features(null,
                List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                null,
                null,
                null,
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("minecraft:forest_flowers",
                    PlacedFeatures.TREE_TALL_OAK_WITH_LITTER,
                    PlacedFeatures.TREE_FALLEN_TALL_OAK,
                    "minecraft:patch_bush",
                    "minecraft:flower_default",
                    "minecraft:patch_grass_forest",
                    "minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:patch_pumpkin",
                    "minecraft:patch_sugar_cane",
                    "minecraft:patch_firefly_bush_near_water"),
                List.of("minecraft:freeze_top_layer"))
            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.AMBIENT, EntityType.BAT, 10, 8, 8)
            .addMobSpawn(MobCategory.CREATURE, EntityType.SHEEP, 12, 4, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.PIG, 10, 4, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.CHICKEN, 10, 4, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.COW, 8, 4, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.WOLF, 5, 2, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 2, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)
            .addMobSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.GLOW_SQUID, 10, 4, 6)

            .addToTag(BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD)

            .build();

        tall_oak.register();
        biomes.add(tall_oak);

        BiomeDefinition lush_forest = BiomeDefinition.builder(Biomes.FOREST_LUSH_FOREST)
            .hasPrecipitation(true)
            .temperature(2f)
            .downfall(0.2f)
            .waterColor(3832426)
            .foliageColorOverride(9285927)

            .setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, new AmbientSounds(
                Optional.empty(),
                Optional.of(new AmbientMoodSettings(
                    SoundEvents.AMBIENT_CAVE,
                    6000,
                    8,
                    2.0)),
                List.of()
            ))

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7782102)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 13880215)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 5077600)

            .addDefaultUndergroundOreFeatures()
            .features(null,
                List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:fossil_upper",
                    "minecraft:fossil_lower",
                    "minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                null,
                null,
                null,
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("wythers:vegetation/placed_random_patch/flooded_savanna_water_plants",
                    "wythers:vegetation/placed_random_patch/flowers_tropical_forest",
                    "wythers:vegetation/trees_tropical_forest",
                    "minecraft:patch_tall_grass",
                    "minecraft:patch_grass_savanna",
                    "minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:patch_sugar_cane",
                    "minecraft:seagrass_swamp"),
                List.of("minecraft:freeze_top_layer"))

            .addDefaultOverworldCarvers()

            .addDefaultMonsterSpawns(true)
            .addDefaultFarmAnimalsSpawns()
            .addDefaultCaveSpawns()
            .addMobSpawn(MobCategory.MONSTER, EntityType.OCELOT, 2, 1, 3)
            .addMobSpawn(MobCategory.CREATURE, EntityType.WOLF, 10, 2, 5)

            .addToTag(BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD)

            .build();

        lush_forest.register();
        biomes.add(lush_forest);

        return biomes;
    }

    private static List<BiomeDefinition> plainsBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition dry_plains = BiomeDefinition.builder(Biomes.PLAINS_DRY_PLAINS)
            .hasPrecipitation(false)
            .temperature(2.0f)
            .downfall(0.05f)
            .waterColor(5336976)

            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 16379351)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 2171215)

            .addDefaultOverworldCarvers()
            .features(List.of(PlacedFeatures.TERRAIN_STONE_CLIFF),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(PlacedFeatures.VEGETATION_PATCH_CLIFF_GRASS,
                    PlacedFeatures.TERRAIN_SAND_SHORE_DISK,
                    "minecraft:patch_grass_plain",
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane",
                    PlacedFeatures.VEGETATION_ROOT_DIRT_BLOB),
                null)

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.CREATURE, EntityType.COW, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.SHEEP, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.PIG, 10, 2, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 2, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)

            .addToTag(BiomeTags.HAS_VILLAGE_PLAINS, BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS, BiomeTags.IS_OVERWORLD)

            .build();
        dry_plains.register();
        biomes.add(dry_plains);

        BiomeDefinition lush_plains = BiomeDefinition.builder(Biomes.PLAINS_LUSH_PLAINS)
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.5f)
            .waterColor(4159204)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_STONE_CLIFF),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(PlacedFeatures.VEGETATION_PATCH_CLIFF_GRASS,
                    PlacedFeatures.TERRAIN_SAND_SHORE_DISK,
                    PlacedFeatures.TREE_TALL_STRIPPED_PALE_OAK,
                    "minecraft:flower_plains",
                    "minecraft:patch_grass_plain",
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane",
                    PlacedFeatures.VEGETATION_PATCH_HAY_BALE,
                    PlacedFeatures.VEGETATION_PATCH_CHERRY_PETALS,
                    PlacedFeatures.VEGETATION_ROOT_DIRT_BLOB,
                    PlacedFeatures.TREE_FALLEN_STRIPPED_PALE_OAK),
                null)

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.CREATURE, EntityType.COW, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.SHEEP, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.PIG, 10, 2, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 2, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)

            .addToTag(BiomeTags.HAS_VILLAGE_PLAINS, BiomeTags.IS_OVERWORLD)

            .build();
        lush_plains.register();
        biomes.add(lush_plains);

        BiomeDefinition plains = BiomeDefinition.builder(Biomes.PLAINS_PLAINS)
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.2f)
            .waterColor(4159204)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.TERRAIN_STONE_CLIFF),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(PlacedFeatures.VEGETATION_PATCH_CLIFF_GRASS,
                    PlacedFeatures.TERRAIN_SAND_SHORE_DISK,
                    "minecraft:flower_plains",
                    "minecraft:patch_grass_plain",
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane",
                    PlacedFeatures.VEGETATION_PATCH_HAY_BALE,
                    PlacedFeatures.VEGETATION_PATCH_CHERRY_PETALS,
                    PlacedFeatures.VEGETATION_ROOT_DIRT_BLOB),
                null)

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.CREATURE, EntityType.COW, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.SHEEP, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.PIG, 10, 2, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 2, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)

            .addToTag(BiomeTags.HAS_VILLAGE_PLAINS, BiomeTags.IS_OVERWORLD)

            .build();
        plains.register();
        biomes.add(plains);

        return biomes;
    }

    private static List<BiomeDefinition> riverBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition desert_river = BiomeDefinition.builder(Biomes.RIVER_DESERT_RIVER)
            .hasPrecipitation(false)
            .temperature(2.0f)
            .downfall(0.0f)
            .waterColor(4112789)
            .foliageColorOverride(9285927)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7788235)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 13880215)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 2326625)

            .addDefaultUndergroundOreFeatures()
            .features(null,
                List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:fossil_upper",
                    "minecraft:fossil_lower",
                    "minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                List.of("minecraft:desert_well"),
                null,
                null,
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("wythers:vegetation/trees_desert_lakes",
                    "minecraft:patch_grass_savanna",
                    "minecraft:flower_default",
                    "minecraft:patch_dead_bush_2",
                    "minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:patch_sugar_cane_desert"),
                List.of("minecraft:freeze_top_layer"))

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 19, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.HUSK, 80, 4, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.RABBIT, 4, 2, 3)
            .addMobSpawn(MobCategory.AMBIENT, EntityType.BAT, 10, 8, 8)
            .addMobSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.GLOW_SQUID, 10, 4, 6)

            .addToTag(BiomeTags.IS_RIVER, BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS, BiomeTags.IS_OVERWORLD)

            .build();
        desert_river.register();
        biomes.add(desert_river);

        BiomeDefinition lush_river = BiomeDefinition.builder(Biomes.RIVER_LUSH_RIVER)
            .hasPrecipitation(true)
            .temperature(2f)
            .downfall(0.2f)
            .waterColor(3832426)
            .foliageColorOverride(9285927)

            .setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, new AmbientSounds(
                Optional.empty(),
                Optional.of(new AmbientMoodSettings(
                    SoundEvents.AMBIENT_CAVE,
                    6000,
                    8,
                    2.0)),
                List.of()
            ))

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7782102)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 13880215)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 5077600)

            .addDefaultUndergroundOreFeatures()
            .features(null,
                List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:fossil_upper",
                    "minecraft:fossil_lower",
                    "minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                null,
                null,
                null,
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("wythers:vegetation/placed_random_patch/flooded_savanna_water_plants",
                    "wythers:vegetation/placed_random_patch/flowers_tropical_forest",
                    "wythers:vegetation/trees_tropical_forest",
                    "minecraft:patch_tall_grass",
                    "minecraft:patch_grass_savanna",
                    "minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:patch_sugar_cane",
                    "minecraft:seagrass_swamp"),
                List.of("minecraft:freeze_top_layer"))

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 19, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 1, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.OCELOT, 2, 1, 3)
            .addMobSpawn(MobCategory.CREATURE, EntityType.PIG, 10, 4, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.CHICKEN, 10, 4, 4)
            .addMobSpawn(MobCategory.AMBIENT, EntityType.BAT, 10, 8, 8)
            .addMobSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.GLOW_SQUID, 10, 4, 6)
            .addMobSpawn(MobCategory.WATER_AMBIENT, EntityType.PUFFERFISH, 15, 1, 3)
            .addMobSpawn(MobCategory.WATER_AMBIENT, EntityType.COD, 25, 8, 8)

            .addToTag(BiomeTags.IS_RIVER, BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS, BiomeTags.IS_OVERWORLD)

            .build();
        lush_river.register();
        biomes.add(lush_river);

        BiomeDefinition temperate_river = BiomeDefinition.builder(Biomes.RIVER_TEMPERATE_RIVER)
            .hasPrecipitation(true)
            .temperature(0.5f)
            .downfall(0.5f)
            .waterColor(6388580)

            .setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS,
                new AmbientSounds(
                    Optional.empty(),
                    Optional.of(new AmbientMoodSettings(
                        SoundEvents.AMBIENT_CAVE,
                        6000,
                        8,
                        2.0
                    )),
                    List.of()
                ))

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7391487)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 2302743)

            .addDefaultUndergroundOreFeatures()
            .features(null,
                List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                null,
                null,
                null,
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:seagrass_river"),
                List.of("minecraft:freeze_top_layer"))

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 1, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.DROWNED, 100, 1, 1)
            .addMobSpawn(MobCategory.AMBIENT, EntityType.BAT, 10, 8, 8)
            .addMobSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.GLOW_SQUID, 10, 4, 6)
            .addMobSpawn(MobCategory.WATER_AMBIENT, EntityType.SALMON, 5, 1, 5)

            .addToTag(BiomeTags.IS_RIVER, BiomeTags.IS_OVERWORLD)

            .build();
        temperate_river.register();
        biomes.add(temperate_river);

        return biomes;
    }

    private static List<BiomeDefinition> swampBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition dripleaf_swamp = BiomeDefinition.builder(Biomes.SWAMP_DRIPLEAF_SWAMP)
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.2f)
            .waterColor(6388580)
            .foliageColorOverride(6975545)
            .grassColorModifier(GrassColorModifier.SWAMP)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 2302743)

            .addDefaultUndergroundOreFeatures()
            .features(List.of(PlacedFeatures.DELTA_DRIPLEAF_SWAMP_DELTA,
                    PlacedFeatures.DELTA_COASTAL_DELTA,
                    PlacedFeatures.TERRAIN_WATER_BLOB),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:trees_swamp",
                    "minecraft:flower_swamp",
                    "minecraft:patch_grass_normal",
                    "minecraft:patch_dead_bush",
                    "minecraft:patch_waterlily",
                    "minecraft:seagrass_swamp"),
                null)

            .addDefaultOverworldCarvers()

            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 10, 2, 5)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 2, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)
            .addMobSpawn(MobCategory.WATER_CREATURE, EntityType.DROWNED, 2, 1, 2)

            .addToTag(BiomeTags.HAS_SWAMP_HUT, BiomeTags.IS_OVERWORLD)

            .build();

        dripleaf_swamp.register();
        biomes.add(dripleaf_swamp);

        return biomes;
    }

}
