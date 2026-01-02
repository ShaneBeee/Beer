package com.shanebeestudios.beer.api.utils;

import com.shanebeestudios.beer.api.registration.BiomeDefinition;
import com.shanebeestudios.beer.api.registration.ConfiguredFeatureDefinition;
import com.shanebeestudios.beer.api.registration.PlacedFeatureDefinition;
import com.shanebeestudios.coreapi.util.ReflectionUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@SuppressWarnings({"DataFlowIssue", "unchecked"})
public class RegistryUtils {

    private static final MinecraftServer SERVER = MinecraftServer.getServer();
    private static final Registry<Enchantment> ENCHANT_REGISTRY = getRegistry(Registries.ENCHANTMENT);
    private static final Registry<Biome> BIOME_REGISTRY = getRegistry(Registries.BIOME);
    private static final Registry<PlacedFeature> PLACED_FEATURE_REGISTRY = getRegistry(Registries.PLACED_FEATURE);
    private static final Registry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTRY = getRegistry(Registries.CONFIGURED_FEATURE);
    private static final Registry<ConfiguredWorldCarver<?>> CARVER_REGISTRY = getRegistry(Registries.CONFIGURED_CARVER);

    public static Registry<Enchantment> getEnchantRegistry() {
        return ENCHANT_REGISTRY;
    }

    public static Registry<Biome> getBiomeRegistry() {
        return BIOME_REGISTRY;
    }

    public static <T> @Nullable TagKey<T> getTagKey(@NotNull Registry<T> registry, @NotNull String name) {
        TagKey<T> tagKey = TagKey.create(registry.key(), Identifier.parse(name));
        if (registry.get(tagKey).isPresent()) {
            return tagKey;
        }
        return null;
    }

    @NotNull
    private static <T> Map<TagKey<T>, HolderSet.Named<T>> getFrozenTags(@NotNull Registry<T> registry) {
        return (Map<TagKey<T>, HolderSet.Named<T>>) ReflectionUtils.getField("frozenTags", registry.getClass(), registry);
    }

    @NotNull
    private static <T> Object getAllTags(@NotNull Registry<T> registry) {
        return ReflectionUtils.getField("allTags", MappedRegistry.class, registry);
    }

    @NotNull
    private static <T> Map<TagKey<T>, HolderSet.Named<T>> getTagsMap(@NotNull Object tagSet) {
        return new HashMap<>((Map<TagKey<T>, HolderSet.Named<T>>) ReflectionUtils.getField("val$map", tagSet.getClass(), tagSet));
    }

    public static <T> void unfreeze(@NotNull Registry<T> registry) {
        ReflectionUtils.setField("frozen", registry, false);
        ReflectionUtils.setField("unregisteredIntrusiveHolders", registry, new IdentityHashMap<>());
    }

    public static <T> void freeze(@NotNull Registry<T> registry) {
        Object tagSet = getAllTags(registry);

        Map<TagKey<T>, HolderSet.Named<T>> tagsMap = getTagsMap(tagSet);
        Map<TagKey<T>, HolderSet.Named<T>> frozenTags = getFrozenTags(registry);

        tagsMap.forEach(frozenTags::putIfAbsent);
        unbound(registry);
        registry.freeze();
        frozenTags.forEach(tagsMap::putIfAbsent);
        ReflectionUtils.setField("val$map", tagSet.getClass(), tagSet, tagsMap);
        ReflectionUtils.setField("allTags", registry.getClass(), registry, tagSet);
    }

    private static <T> void unbound(@NotNull Registry<T> registry) {
        try {
            Class<?> tagSetClass = Class.forName("net.minecraft.core.MappedRegistry$TagSet");
            Method unbound = tagSetClass.getMethod("unbound");
            unbound.setAccessible(true);
            Object unboundTagSet = unbound.invoke(registry);
            ReflectionUtils.setField("allTags", registry, unboundTagSet);
        } catch (Exception ignore) {
        }
    }

    private static <T> void addInTag(@NotNull TagKey<T> tagKey, @NotNull Holder.Reference<T> reference) {
        Registry<T> registry = getRegistry((ResourceKey<Registry<T>>) tagKey.registry());
        modifyTag(registry, tagKey, reference, List::add);
    }

    private static <T> void removeFromTag(@NotNull TagKey<T> tagKey, @NotNull Holder.Reference<T> reference) {
        Registry<T> registry = getRegistry((ResourceKey<Registry<T>>) tagKey.registry());
        modifyTag(registry, tagKey, reference, List::remove);
    }

    private static <T> void modifyTag(@NotNull Registry<T> registry, @NotNull TagKey<T> tagKey, @NotNull Holder.Reference<T> reference, @NotNull BiConsumer<List<Holder<T>>, Holder.Reference<T>> consumer) {
        HolderSet.Named<T> holders = registry.get(tagKey).orElse(null);
        if (holders == null) return;

        List<Holder<T>> contents = new ArrayList<>(holders.stream().toList());
        consumer.accept(contents, reference);

        if (registry instanceof MappedRegistry<T> mappedRegistry) {
            mappedRegistry.bindTag(tagKey, contents);
        }
    }

    private static void setupBiomeDistribution(@NotNull Holder.Reference<Biome> reference, BiomeDefinition definition) {
        definition.getTagKeys().forEach(tag -> addInTag(tag, reference));
    }

    public static <T> Registry<T> getRegistry(ResourceKey<Registry<T>> key) {
        return MinecraftServer.getServer().registryAccess().lookup(key).orElseThrow();
    }

    public static Holder.Reference<Biome> registerBiome(BiomeDefinition definition) {
        unfreeze(BIOME_REGISTRY);

        Biome biome = definition.getValue();
        Holder.Reference<Biome> intrusiveHolder = BIOME_REGISTRY.createIntrusiveHolder(biome);
        Registry.register(BIOME_REGISTRY, definition.getResourceKey(), biome);

        setupBiomeDistribution(intrusiveHolder, definition);
        freeze(BIOME_REGISTRY);

        return intrusiveHolder;
    }

    public static Holder.Reference<PlacedFeature> registerPlacedFeature(PlacedFeatureDefinition definition) {
        ResourceKey<PlacedFeature> resourceKey = definition.getResourceKey();
        if (!PLACED_FEATURE_REGISTRY.containsKey(resourceKey.identifier())) {
            unfreeze(PLACED_FEATURE_REGISTRY);

            PlacedFeature placedFeature = definition.getValue();
            Holder.Reference<PlacedFeature> intrusiveHolder = PLACED_FEATURE_REGISTRY.createIntrusiveHolder(placedFeature);
            Registry.register(PLACED_FEATURE_REGISTRY, resourceKey, placedFeature);

            freeze(PLACED_FEATURE_REGISTRY);

            return intrusiveHolder;
        }

        return null;
    }

    public static Holder.Reference<ConfiguredFeature<?, ?>> registerConfiguredFeature(ConfiguredFeatureDefinition definition) {
        ResourceKey<ConfiguredFeature<?, ?>> resourceKey = definition.getResourceKey();
        if (!CONFIGURED_FEATURE_REGISTRY.containsKey(resourceKey)) {
            unfreeze(CONFIGURED_FEATURE_REGISTRY);

            ConfiguredFeature<?, ?> feature = definition.getValue();
            Holder.Reference<ConfiguredFeature<?, ?>> intrusiveHolder = CONFIGURED_FEATURE_REGISTRY.createIntrusiveHolder(feature);
            Registry.register(CONFIGURED_FEATURE_REGISTRY, resourceKey, feature);

            freeze(CONFIGURED_FEATURE_REGISTRY);

            return intrusiveHolder;
        }

        return null;
    }

    @Nullable
    public static Holder<PlacedFeature> getPlacedFeature(Identifier identifier) {
        return PLACED_FEATURE_REGISTRY.get(identifier).orElse(null);
    }

    public static Holder.Reference<PlacedFeature> getPlacedFeatureReference(ResourceKey<PlacedFeature> key) {
        return Holder.Reference.createStandAlone(PLACED_FEATURE_REGISTRY, key);
    }

    public static Holder<ConfiguredFeature<?, ?>> getConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> key) {
        return CONFIGURED_FEATURE_REGISTRY.getOrThrow(key);
    }

    public static Holder.Reference<ConfiguredFeature<?, ?>> getConfiguredFeatureReference(ResourceKey<ConfiguredFeature<?, ?>> key) {
        return Holder.Reference.createStandAlone(CONFIGURED_FEATURE_REGISTRY, key);
    }

    @Nullable
    public static Holder<ConfiguredWorldCarver<?>> getCarver(Identifier identifier) {
        return CARVER_REGISTRY.get(identifier).orElse(null);
    }

}
