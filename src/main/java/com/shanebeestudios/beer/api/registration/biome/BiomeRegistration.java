package com.shanebeestudios.beer.api.registration.biome;

import com.shanebeestudios.beer.api.utils.DumpRegistry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
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

    public static void generateBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        biomes.addAll(caveBiomes());
        biomes.addAll(coastBiomes());
        biomes.addAll(forestBiomes());
        biomes.addAll(plainsBiomes());
        biomes.addAll(riverBiomes());
        biomes.addAll(swampBiomes());

        // Register biomes to server
        for (BiomeDefinition biomeDefinition : biomes) {
            biomeDefinition.register();
        }

        // Dump biomes to datapack files
        for (BiomeDefinition biome : biomes) {
            DumpRegistry.dumpObject(biome.getIdentifier(), biome.getBiome());
        }

    }

    private static List<BiomeDefinition> caveBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition dry_cave = BiomeDefinition.builder("beer:cave/dry_cave")
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
            .carvers("minecraft:cave",
                "minecraft:cave_extra_underground",
                "minecraft:canyon")

            // Features
            .features(
                null,
                List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface",
                    "beer:delta/dry_cave_delta",
                    "beer:terrain/brown_concrete_disk"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_gravel",
                    "minecraft:ore_granite_upper",
                    "minecraft:ore_granite_lower",
                    "minecraft:ore_diorite_upper",
                    "minecraft:ore_diorite_lower",
                    "minecraft:ore_andesite_upper",
                    "minecraft:ore_andesite_lower",
                    "minecraft:ore_tuff",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:ore_iron_upper",
                    "minecraft:ore_iron_middle",
                    "minecraft:ore_iron_small",
                    "minecraft:ore_gold",
                    "minecraft:ore_gold_lower",
                    "minecraft:ore_redstone",
                    "minecraft:ore_redstone_lower",
                    "minecraft:ore_diamond",
                    "minecraft:ore_diamond_medium",
                    "minecraft:ore_diamond_large",
                    "minecraft:ore_diamond_buried",
                    "minecraft:ore_lapis",
                    "minecraft:ore_lapis_buried",
                    "minecraft:ore_copper",
                    "minecraft:underwater_magma",
                    "minecraft:ore_clay",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                List.of("minecraft:spring_water", "minecraft:spring_lava"),
                List.of("minecraft:glow_lichen", "minecraft:patch_tall_grass_2", "beer:decor/hanging_fence"),
                List.of("minecraft:freeze_top_layer")
            )

            // Spawners
            .addMobSpawn(MobCategory.AMBIENT, EntityType.BAT, 10, 8, 8)
            .addMobSpawn(MobCategory.AXOLOTLS, EntityType.AXOLOTL, 10, 4, 6)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1,1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 4, 4)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 100, 4, 4)
            .addMobSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.TROPICAL_FISH, 25, 8, 8)

            .build();

        BiomeDefinition ice_cave = BiomeDefinition.builder("beer:cave/ice_cave")
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

            .features(List.of("beer:delta/coastal_delta"),
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                null,
                List.of("beer:terrain/stone_to_ice"))

            .addMobSpawn(MobCategory.MONSTER, EntityType.ILLUSIONER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 3, 1, 1)
            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 10, 1, 2)
            .addMobSpawn(MobCategory.CREATURE, EntityType.ARMADILLO, 10, 1, 2)

            .build();

        biomes.add(ice_cave);
        biomes.add(dry_cave);

        return biomes;

    }

    private static List<BiomeDefinition> coastBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition beachy_beach = BiomeDefinition.builder("beer:coast/beachy_beach")
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.4f)

            .waterColor(4159204)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)

            .features(List.of("beer:terrain/diorite_cliffs",
                    "beer:terrain/grass_to_sand"),
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("beer:tree/beachy_palm",
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane"),
                null)

            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 15, 2, 2)

            .build();

        BiomeDefinition coast = BiomeDefinition.builder("beer:coast/coast")
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

            .features(List.of("beer:terrain/diorite_cliffs",
                    "beer:delta/coastal_delta"),
                null,
                null,
                null,
                null,
                null,
                List.of("beer:vegetation/patch_water_leaves",
                    "minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("beer:tree/palm_beach_palm",
                    "wythers:vegetation/placed_random_patch/large_ferns_dense_forests",
                    "beer:vegetation/azalea_bush",
                    "wythers:vegetation/placed_random_patch/dark_oak_roots",
                    "wythers:terrain/placed_random_patch/mossify_grass",
                    "wythers:vegetation/bushes_mediterranean",
                    "wythers:vegetation/placed_random_patch/mediterranean_lilacs",
                    "minecraft:glow_lichen",
                    "minecraft:patch_grass_savanna",
                    "minecraft:seagrass_normal"),
                null)

            .addMobSpawn(MobCategory.MONSTER, EntityType.ILLUSIONER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 3, 1, 1)
            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 10, 1, 2)
            .addMobSpawn(MobCategory.CREATURE, EntityType.ARMADILLO, 10, 1, 2)

            .build();

        BiomeDefinition dry_coast = BiomeDefinition.builder("beer:coast/dry_coast")
            .hasPrecipitation(true)
            .temperature(2.0f)
            .downfall(0.05f)
            .waterColor(4159204)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)

            .features(List.of("beer:terrain/diorite_cliffs"),
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("beer:tree/palm_beach_palm",
                    "wythers:vegetation/placed_random_patch/dark_oak_roots",
                    "wythers:terrain/placed_random_patch/mossify_grass",
                    "minecraft:glow_lichen",
                    "minecraft:patch_grass_savanna"),
                null)

            .addMobSpawn(MobCategory.MONSTER, EntityType.ILLUSIONER, 1, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 3, 1, 1)
            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 10, 1, 2)
            .addMobSpawn(MobCategory.CREATURE, EntityType.ARMADILLO, 10, 1, 2)

            .build();

        BiomeDefinition frozen_beach = BiomeDefinition.builder("beer:coast/frozen_beach")
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

            .features(List.of("beer:terrain/diorite_cliffs",
                    "beer:terrain/grass_to_sand"),
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("beer:tree/beachy_palm",
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane"),
                List.of("minecraft:freeze_top_layer"))

            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 15, 2, 2)
            .build();

        BiomeDefinition lush_coast = BiomeDefinition.builder("beer:coast/lush_coast")
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

            .features(List.of("beer:terrain/diorite_cliffs",
                    "beer:delta/coastal_delta"),
                null,
                null,
                null,
                null,
                null,
                List.of("beer:vegetation/patch_water_leaves",
                    "minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("beer:tree/palm_beach_palm",
                    "wythers:vegetation/placed_random_patch/large_ferns_dense_forests",
                    "beer:vegetation/azalea_bush",
                    "wythers:vegetation/placed_random_patch/dark_oak_roots",
                    "wythers:terrain/placed_random_patch/mossify_grass",
                    "wythers:vegetation/bushes_mediterranean",
                    "wythers:vegetation/placed_random_patch/mediterranean_lilacs",
                    "wythers:vegetation/placed_random_patch/dripleaves_bayou",
                    "minecraft:glow_lichen",
                    "minecraft:patch_grass_savanna",
                    "minecraft:seagrass_normal"),
                null)

            .addMobSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.SKELETON, 3, 1, 1)
            .addMobSpawn(MobCategory.MONSTER, EntityType.CREEPER, 3, 1, 1)
            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 10, 1, 2)

            .build();

        BiomeDefinition palm_beach = BiomeDefinition.builder("beer:coast/palm_beach")
            .hasPrecipitation(false)
            .temperature(2.0f)
            .downfall(0.0f)
            .foliageColorOverride(442658)
            .grassColorOverride(6017902)
            .waterColor(6003155)

            .setAttribute(EnvironmentAttributes.SKY_COLOR, 5634012)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 16564102)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 8846572)

            .features(List.of("beer:terrain/diorite_cliffs",
                    "beer:terrain/grass_to_sand",
                    "beer:delta/beach_delta"),
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("beer:tree/palm_beach_palm",
                    "minecraft:patch_waterlily",
                    "beer:vegetation/patch_small_dripleaf"),
                null)

            .addMobSpawn(MobCategory.CREATURE, EntityType.FROG, 15, 2, 2)
            .build();

        biomes.add(beachy_beach);
        biomes.add(coast);
        biomes.add(dry_coast);
        biomes.add(frozen_beach);
        biomes.add(lush_coast);
        biomes.add(palm_beach);

        return biomes;
    }

    private static List<BiomeDefinition> forestBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition moss_garden = BiomeDefinition.builder("beer:forest/moss_garden")
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

            .features(null, List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_gravel",
                    "minecraft:ore_granite_upper",
                    "minecraft:ore_granite_lower",
                    "minecraft:ore_diorite_upper",
                    "minecraft:ore_diorite_lower",
                    "minecraft:ore_andesite_upper",
                    "minecraft:ore_andesite_lower",
                    "minecraft:ore_tuff",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:ore_iron_upper",
                    "minecraft:ore_iron_middle",
                    "minecraft:ore_iron_small",
                    "minecraft:ore_gold",
                    "minecraft:ore_gold_lower",
                    "minecraft:ore_redstone",
                    "minecraft:ore_redstone_lower",
                    "minecraft:ore_diamond",
                    "minecraft:ore_diamond_medium",
                    "minecraft:ore_diamond_large",
                    "minecraft:ore_diamond_buried",
                    "minecraft:ore_lapis",
                    "minecraft:ore_lapis_buried",
                    "minecraft:ore_copper",
                    "minecraft:underwater_magma",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("minecraft:glow_lichen",
                    "beer:tree/moss_garden",
                    "beer:tree/fallen_warped_stem",
                    "beer:vegetation/moss_patch",
                    "minecraft:patch_grass_forest"),
                List.of("minecraft:freeze_top_layer"))

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

            .carvers("minecraft:cave",
                "minecraft:cave_extra_underground",
                "minecraft:canyon")
            .build();

        biomes.add(moss_garden);

        return biomes;
    }

    private static List<BiomeDefinition> plainsBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition dry_plains = BiomeDefinition.builder("beer:plains/dry_plains")
            .hasPrecipitation(true)
            .temperature(2.0f)
            .downfall(0.05f)
            .waterColor(5336976)

            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 16379351)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 2171215)

            .features(List.of("beer:terrain/stone_cliff"),
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("wythers:terrain/placed_random_patch/cliff_grass",
                    "wythers:terrain/disk_sand_shore",
                    "minecraft:patch_grass_plain",
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane",
                    "beer:vegetation/rooted_dirt_blob"),
                null)

            .addMobSpawn(MobCategory.CREATURE, EntityType.COW, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.SHEEP, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.PIG, 10, 2, 4)

            .build();

        BiomeDefinition lush_plains = BiomeDefinition.builder("beer:plains/lush_plains")
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.5f)
            .waterColor(4159204)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)

            .features(List.of("beer:terrain/stone_cliff"),
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("wythers:terrain/placed_random_patch/cliff_grass",
                    "wythers:terrain/disk_sand_shore",
                    "beer:tree/tall_stripped_pale_oak",
                    "minecraft:flower_plains",
                    "minecraft:patch_grass_plain",
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane",
                    "beer:vegetation/patch_hay_bale",
                    "beer:vegetation/patch_cherry_petals",
                    "beer:vegetation/rooted_dirt_blob",
                    "beer:tree/fallen_stripped_pale_oak"),
                null)

            .addMobSpawn(MobCategory.CREATURE, EntityType.COW, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.SHEEP, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.PIG, 10, 2, 4)

            .build();

        BiomeDefinition plains = BiomeDefinition.builder("beer:plains/plains")
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.2f)
            .waterColor(4159204)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)

            .features(List.of("beer:terrain/stone_cliff"),
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("wythers:terrain/placed_random_patch/cliff_grass",
                    "wythers:terrain/disk_sand_shore",
                    "minecraft:flower_plains",
                    "minecraft:patch_grass_plain",
                    "minecraft:patch_waterlily",
                    "minecraft:patch_sugar_cane",
                    "beer:vegetation/patch_hay_bale",
                    "beer:vegetation/patch_cherry_petals",
                    "beer:vegetation/rooted_dirt_blob"),
                null)

            .addMobSpawn(MobCategory.CREATURE, EntityType.COW, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.SHEEP, 10, 2, 4)
            .addMobSpawn(MobCategory.CREATURE, EntityType.PIG, 10, 2, 4)

            .build();

        biomes.add(dry_plains);
        biomes.add(lush_plains);
        biomes.add(plains);

        return biomes;
    }

    private static List<BiomeDefinition> riverBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition desert_river = BiomeDefinition.builder("beer:river/desert_river")
            .hasPrecipitation(false)
            .temperature(0.9f)
            .downfall(0.5f)
            .waterColor(4112789)
            .foliageColorOverride(9285927)

            .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 7788235)
            .setAttribute(EnvironmentAttributes.FOG_COLOR, 13880215)
            .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 2326625)

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
                List.of("wythers:vegetation/placed_random_patch/oasis_grass_blocks",
                    "wythers:vegetation/placed_random_patch/oasis_podzol_blocks",
                    "minecraft:ore_dirt",
                    "minecraft:ore_gravel",
                    "minecraft:ore_granite_upper",
                    "minecraft:ore_granite_lower",
                    "minecraft:ore_diorite_upper",
                    "minecraft:ore_diorite_lower",
                    "minecraft:ore_andesite_upper",
                    "minecraft:ore_andesite_lower",
                    "minecraft:ore_tuff",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:ore_iron_upper",
                    "minecraft:ore_iron_middle",
                    "minecraft:ore_iron_small",
                    "minecraft:ore_gold",
                    "minecraft:ore_gold_lower",
                    "minecraft:ore_redstone",
                    "minecraft:ore_redstone_lower",
                    "minecraft:ore_diamond",
                    "minecraft:ore_diamond_large",
                    "minecraft:ore_diamond_buried",
                    "minecraft:ore_lapis",
                    "minecraft:ore_lapis_buried",
                    "minecraft:ore_copper",
                    "minecraft:underwater_magma",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("wythers:vegetation/trees_desert_lakes",
                    "minecraft:glow_lichen",
                    "minecraft:patch_grass_savanna",
                    "minecraft:flower_default",
                    "minecraft:patch_dead_bush_2",
                    "minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:patch_sugar_cane_desert"),
                List.of("minecraft:freeze_top_layer"))

            .carvers("minecraft:cave", "minecraft:cave_extra_underground", "minecraft:canyon")

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

            .build();

        BiomeDefinition lush_river = BiomeDefinition.builder("beer:river/lush_river")
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
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_gravel",
                    "minecraft:ore_granite_upper",
                    "minecraft:ore_granite_lower",
                    "minecraft:ore_diorite_upper",
                    "minecraft:ore_diorite_lower",
                    "minecraft:ore_andesite_upper",
                    "minecraft:ore_andesite_lower",
                    "minecraft:ore_tuff",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:ore_iron_upper",
                    "minecraft:ore_iron_middle",
                    "minecraft:ore_iron_small",
                    "minecraft:ore_gold",
                    "minecraft:ore_gold_lower",
                    "minecraft:ore_redstone",
                    "minecraft:ore_redstone_lower",
                    "minecraft:ore_diamond",
                    "minecraft:ore_diamond_large",
                    "minecraft:ore_diamond_buried",
                    "minecraft:ore_lapis",
                    "minecraft:ore_lapis_buried",
                    "minecraft:ore_copper",
                    "minecraft:underwater_magma",
                    "minecraft:disk_grass",
                    "minecraft:disk_clay"),
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("wythers:vegetation/placed_random_patch/flooded_savanna_water_plants",
                    "wythers:vegetation/placed_random_patch/flowers_tropical_forest",
                    "wythers:vegetation/trees_tropical_forest",
                    "minecraft:glow_lichen",
                    "minecraft:patch_tall_grass",
                    "minecraft:patch_grass_savanna",
                    "minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:patch_sugar_cane",
                    "minecraft:seagrass_swamp"),
                List.of("minecraft:freeze_top_layer"))

            .carvers("minecraft:cave", "minecraft:cave_extra_underground", "minecraft:canyon")

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

            .build();

        BiomeDefinition temperate_river = BiomeDefinition.builder("beer:river/temperate_river")
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

            .features(null,
                List.of("minecraft:lake_lava_underground",
                    "minecraft:lake_lava_surface"),
                List.of("minecraft:amethyst_geode"),
                List.of("minecraft:monster_room",
                    "minecraft:monster_room_deep"),
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_gravel",
                    "minecraft:ore_granite_upper",
                    "minecraft:ore_granite_lower",
                    "minecraft:ore_diorite_upper",
                    "minecraft:ore_diorite_lower",
                    "minecraft:ore_andesite_upper",
                    "minecraft:ore_andesite_lower",
                    "minecraft:ore_tuff",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:ore_iron_upper",
                    "minecraft:ore_iron_middle",
                    "minecraft:ore_iron_small",
                    "minecraft:ore_gold",
                    "minecraft:ore_gold_lower",
                    "minecraft:ore_redstone",
                    "minecraft:ore_redstone_lower",
                    "minecraft:ore_diamond",
                    "minecraft:ore_diamond_large",
                    "minecraft:ore_diamond_buried",
                    "minecraft:ore_lapis",
                    "minecraft:ore_lapis_buried",
                    "minecraft:ore_copper",
                    "minecraft:underwater_magma",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                List.of("minecraft:spring_water",
                    "minecraft:spring_lava"),
                List.of("minecraft:glow_lichen",
                    "minecraft:brown_mushroom_normal",
                    "minecraft:red_mushroom_normal",
                    "minecraft:seagrass_river"),
                List.of("minecraft:freeze_top_layer"))

            .carvers("minecraft:cave", "minecraft:cave_extra_underground", "minecraft:canyon")

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


            .build();

        biomes.add(desert_river);
        biomes.add(lush_river);
        biomes.add(temperate_river);

        return biomes;
    }

    private static List<BiomeDefinition> swampBiomes() {
        List<BiomeDefinition> biomes = new ArrayList<>();

        BiomeDefinition dripleaf_swamp = BiomeDefinition.builder("beer:swamp/dripleaf_swamp")
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
            .features(List.of("beer:delta/swamp_delta",
                    "beer:delta/coastal_delta",
                    "beer:terrain/water_blob"),
                null,
                null,
                null,
                null,
                null,
                List.of("minecraft:ore_dirt",
                    "minecraft:ore_coal_upper",
                    "minecraft:ore_coal_lower",
                    "minecraft:disk_sand",
                    "minecraft:disk_clay",
                    "minecraft:disk_gravel"),
                null,
                null,
                List.of("minecraft:trees_swamp",
                    "minecraft:flower_swamp",
                    "minecraft:patch_grass_normal",
                    "minecraft:patch_dead_bush",
                    "minecraft:patch_waterlily",
                    "minecraft:seagrass_swamp"),
                null)

            .build();

        biomes.add(dripleaf_swamp);

        return biomes;
    }

}
