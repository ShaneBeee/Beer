package com.shanebeestudios.beer.api.utils;

import com.shanebeestudios.beer.api.registration.biome.BiomeDefinition;
import com.shanebeestudios.beer.api.registration.feature.PlacedFeatureDefinition;
import com.shanebeestudios.coreapi.util.ReflectionUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.tags.TagKey;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
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
    private static final Registry<Item> ITEM_REGISTRY = getRegistry(Registries.ITEM);
    private static final Registry<Block> BLOCK_REGISTRY = getRegistry(Registries.BLOCK);
    private static final Registry<Biome> BIOME_REGISTRY = getRegistry(Registries.BIOME);
    private static final Registry<PlacedFeature> PLACED_FEATURE_REGISTRY = getRegistry(Registries.PLACED_FEATURE);
    private static final Registry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTRY = getRegistry(Registries.CONFIGURED_FEATURE);
    private static final Registry<ConfiguredWorldCarver<?>> CARVER_REGISTRY = getRegistry(Registries.CONFIGURED_CARVER);
    private static final Registry<Dialog> DIALOG_REGISTRY = getRegistry(Registries.DIALOG);
    private static final Registry<EnvironmentAttribute<?>> ENVIRONMENT_ATTRIBUTES_REGISTRY = getRegistry(Registries.ENVIRONMENT_ATTRIBUTE);

    public static Registry<Enchantment> getEnchantRegistry() {
        return ENCHANT_REGISTRY;
    }

    public static Registry<Item> getItemRegistry() {
        return ITEM_REGISTRY;
    }

    public static Registry<Biome> getBiomeRegistry() {
        return BIOME_REGISTRY;
    }

    public static Registry<Dialog> getDialogRegistry() {
        return DIALOG_REGISTRY;
    }

    public static Registry<EnvironmentAttribute<?>> getEnvironmentAttributesRegistry() {
        return ENVIRONMENT_ATTRIBUTES_REGISTRY;
    }

    @NotNull
    public static <T> ResourceKey<T> getResourceKey(@NotNull Registry<T> registry, @NotNull String name) {
        return ResourceKey.create(registry.key(), Identifier.parse(name));
    }

    public static <T> Identifier getResourceLocation(@NotNull NamespacedKey namespacedKey) {
        return CraftNamespacedKey.toMinecraft(namespacedKey);
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

//    private static void setupDistribution(@NotNull Holder.Reference<Enchantment> reference, EnchantmentDefinition distribution) {
//        boolean experimentalTrades = SERVER.getWorldData().enabledFeatures().contains(FeatureFlags.TRADE_REBALANCE);
//
//        EnchantmentDefinition.TagData tagData = distribution.getTagData();
//        if (tagData.isTradeable) {
//            addInTag(EnchantmentTags.TREASURE, reference);
//            addInTag(EnchantmentTags.DOUBLE_TRADE_PRICE, reference);
//        } else {
//            addInTag(EnchantmentTags.NON_TREASURE, reference);
//        }
//
//        if (tagData.isOnRandomLoot) {
//            addInTag(EnchantmentTags.ON_RANDOM_LOOT, reference);
//        }
//
//        if (!tagData.isTreasure) {
//            if (tagData.isOnMobSpawnEquipment) {
//                addInTag(EnchantmentTags.ON_MOB_SPAWN_EQUIPMENT, reference);
//            }
//
//            if (tagData.isOnTradedEquipment) {
//                addInTag(EnchantmentTags.ON_TRADED_EQUIPMENT, reference);
//            }
//        }
//
//        if (experimentalTrades) {
//            if (tagData.isTradeable) {
//                addInTag(EnchantmentTags.TRADES_DESERT_COMMON, reference);
//                addInTag(EnchantmentTags.TRADES_JUNGLE_COMMON, reference);
//                // Add more trade tags if needed.
//            }
//        } else {
//            if (tagData.isTradeable) {
//                addInTag(EnchantmentTags.TRADEABLE, reference);
//            } else removeFromTag(EnchantmentTags.TRADEABLE, reference);
//        }
//
//        if (tagData.isCursed) {
//            addInTag(EnchantmentTags.CURSE, reference);
//        } else {
//            if (!tagData.isTreasure) {
//                if (tagData.isDiscoverable) {
//                    addInTag(EnchantmentTags.IN_ENCHANTING_TABLE, reference);
//                } else {
//                    removeFromTag(EnchantmentTags.IN_ENCHANTING_TABLE, reference);
//                }
//            }
//        }
//    }

    public static <T> Registry<T> getRegistry(ResourceKey<Registry<T>> key) {
        return MinecraftServer.getServer().registryAccess().lookup(key).orElseThrow();
    }

//    public static org.bukkit.enchantments.Enchantment registerEnchantment(EnchantmentDefinition definition) {
//        unfreeze(ENCHANT_REGISTRY);
//
//        Identifier key = CraftNamespacedKey.toMinecraft(definition.getId());
//        ResourceKey<Enchantment> resourceKey = ResourceKey.create(Registries.ENCHANTMENT, key);
//        Enchantment enchantment = definition.getEnchantment();
//        Holder.Reference<Enchantment> intrusiveHolder = ENCHANT_REGISTRY.createIntrusiveHolder(enchantment);
//        Registry.register(ENCHANT_REGISTRY, resourceKey, enchantment);
//
//        setupDistribution(intrusiveHolder, definition);
//        freeze(ENCHANT_REGISTRY);
//
//        return CraftEnchantment.minecraftHolderToBukkit(intrusiveHolder);
//    }

    public static Biome registerBiome(BiomeDefinition definition) {
        unfreeze(BIOME_REGISTRY);

        Identifier identifier = definition.getIdentifier();
        ResourceKey<Biome> resourceKey = ResourceKey.create(Registries.BIOME, identifier);
        Biome biome = definition.getBiome();
        Holder.Reference<Biome> intrusiveHolder = BIOME_REGISTRY.createIntrusiveHolder(biome);
        Registry.register(BIOME_REGISTRY, resourceKey, biome);

        setupBiomeDistribution(intrusiveHolder, definition);
        freeze(BIOME_REGISTRY);

        return biome;
    }

    public static Holder.Reference<PlacedFeature> registerPlacedFeature(PlacedFeatureDefinition definition) {
        Identifier identifier = definition.getIdentifier();
        if (!PLACED_FEATURE_REGISTRY.containsKey(identifier)) {
            unfreeze(PLACED_FEATURE_REGISTRY);

            ResourceKey<PlacedFeature> resourceKey = ResourceKey.create(Registries.PLACED_FEATURE, identifier);
            PlacedFeature placedFeature = definition.getFeature();
            Holder.Reference<PlacedFeature> intrusiveHolder = PLACED_FEATURE_REGISTRY.createIntrusiveHolder(placedFeature);
            Registry.register(PLACED_FEATURE_REGISTRY, resourceKey, placedFeature);

            //setupBiomeDistribution(intrusiveHolder, definition); TODO do we need tags?!?!?
            freeze(PLACED_FEATURE_REGISTRY);

            return intrusiveHolder;
        }

        return null;
    }

    public static void registerDialog(Dialog dialog, NamespacedKey dialogKey) {
        Identifier identifier = CraftNamespacedKey.toMinecraft(dialogKey);
        ResourceKey<Dialog> resourceKey = ResourceKey.create(Registries.DIALOG, identifier);
        if (DIALOG_REGISTRY.containsKey(resourceKey)) {
            // Already registered
            return;
        }

        unfreeze(DIALOG_REGISTRY);
        Holder.Reference<Dialog> intrusiveHolder = DIALOG_REGISTRY.createIntrusiveHolder(dialog);
        Registry.register(DIALOG_REGISTRY, resourceKey, dialog);
        freeze(DIALOG_REGISTRY);
    }

    @Nullable
    public static Holder<PlacedFeature> getPlacedFeature(Identifier identifier) {
        return PLACED_FEATURE_REGISTRY.get(identifier).orElse(null);
    }

    public static Holder<ConfiguredFeature<?, ?>> getConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> key) {
        return CONFIGURED_FEATURE_REGISTRY.getOrThrow(key);
    }

    @Nullable
    public static Holder<ConfiguredWorldCarver<?>> getCarver(Identifier identifier) {
        return CARVER_REGISTRY.get(identifier).orElse(null);
    }

}
