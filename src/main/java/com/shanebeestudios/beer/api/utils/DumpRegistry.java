package com.shanebeestudios.beer.api.utils;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.shanebeestudios.beer.api.registration.Definition;
import com.shanebeestudios.beer.plugin.BeerPlugin;
import com.shanebeestudios.coreapi.util.Utils;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagFile;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DumpRegistry<N> {

    private static final RegistryOps<JsonElement> REGISTRY_OPS = RegistryOps.create(JsonOps.INSTANCE, MinecraftServer.getServer().registryAccess());
    private static final File DATA_FOLDER = BeerPlugin.getPluginInstance().getDataFolder();
    private static final Map<Class<?>, DumpRegistry<?>> MAP = new HashMap<>();
    public static final String PATTERN;

    static {
        register("biomes", Registries.BIOME, Biome.DIRECT_CODEC, Biome.class);
        register("placed_feature", Registries.PLACED_FEATURE, PlacedFeature.DIRECT_CODEC, PlacedFeature.class);
        register("configured_feature", Registries.CONFIGURED_FEATURE, ConfiguredFeature.DIRECT_CODEC, ConfiguredFeature.class);
        register("enchantments", Registries.ENCHANTMENT, Enchantment.DIRECT_CODEC, Enchantment.class);

        List<String> patterns = MAP.values().stream().map(dumpRegistry -> dumpRegistry.name).toList();
        PATTERN = Joiner.on("/").join(patterns);
    }

    private static <N> void register(String name, ResourceKey<Registry<N>> registry, Codec<N> codec, Class<?> nmsClass) {
        MAP.put(nmsClass, new DumpRegistry<>(name, registry, codec));
    }

    public static <N extends Definition<?>> void dumpDefinables(List<N> definitions) {
        definitions.forEach(DumpRegistry::dumpDefinable);
    }

    public static void dumpDefinable(Definition<?> definition) {
        MAP.forEach((objectClass, dumpRegistry) -> {
            Object value = definition.getValue();
            if (objectClass.isAssignableFrom(value.getClass())) {
                Identifier identifier = definition.getResourceKey().identifier();
                Utils.log("Dumping - %s / %s", dumpRegistry.registryPath, identifier);
                dumpRegistry.dump(identifier, value);
            }
        });
    }

    public static <T,N extends Definition<T>> void dumpDefinablesTags(List<N> definitions) {
        definitions.forEach(DumpRegistry::dumpDefinableTags);
    }

    @SuppressWarnings("unchecked")
    public static <T> void dumpDefinableTags(Definition<T> definition) {
        MAP.forEach((objectClass, dumpRegistry) -> {
            if (objectClass.isAssignableFrom(Biome.class)) {
                for (TagKey<?> tagKey : definition.getTagKeys()) {
                    List<TagEntry> tagEntries = new ArrayList<>();
                    for (Holder<?> holder : RegistryUtils.getRegistry(definition.getResourceKey().registryKey()).getTagOrEmpty((TagKey<T>) tagKey)) {
                        Optional<? extends ResourceKey<?>> resourceKey = holder.unwrapKey();
                        if (resourceKey.isPresent()) {
                            Identifier identifier = resourceKey.get().identifier();
                            if (identifier.getNamespace().equalsIgnoreCase("minecraft")) continue;

                            TagEntry tagEntry = TagEntry.element(identifier);
                            tagEntries.add(tagEntry);


                        }
                        TagFile tagFile = new TagFile(tagEntries, false);
                        dumpRegistry.dumpTags(tagKey.location(), tagFile);
                    }

                }
            }
        });
    }

    public static void dumpObject(Identifier identifier, Object object) {
        MAP.forEach((objectClass, dumpRegistry) -> {
            if (objectClass.isAssignableFrom(object.getClass())) {
                Utils.log("Dumping - %s / %s", dumpRegistry.registryPath, identifier);
                dumpRegistry.dump(identifier, object);
            }
        });
    }

    private final String name;
    private final String registryPath;
    private final Codec<N> codec;

    public DumpRegistry(String name, ResourceKey<Registry<N>> registry, Codec<N> codec) {
        this.name = name;
        this.registryPath = registry.identifier().getPath();
        this.codec = codec;
    }

    @SuppressWarnings("unchecked")
    private void dump(Identifier identifier, Object nmsObject) {
        File file = new File(DATA_FOLDER, "data/" + identifier.getNamespace() + "/" + this.registryPath + "/" + identifier.getPath() + ".json");

        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            return;
        }
        DataResult<JsonElement> jsonData = this.codec.encodeStart(REGISTRY_OPS, (N) nmsObject);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Files.writeString(file.toPath(), gson.toJson(jsonData.getOrThrow()) + "\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void dumpTags(Identifier identifier, TagFile nmsObject) {
        File file = new File(DATA_FOLDER, "data/" + identifier.getNamespace() + "/tags/" + this.registryPath + "/" + identifier.getPath() + ".json");

        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            return;
        }
        DataResult<JsonElement> jsonData = TagFile.CODEC.encodeStart(REGISTRY_OPS, nmsObject);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Files.writeString(file.toPath(), gson.toJson(jsonData.getOrThrow()) + "\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
