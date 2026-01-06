package com.shanebeestudios.beer.api.registration;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.List;

public interface Definition<T> {

    Holder.Reference<T> register();

    ResourceKey<T> getResourceKey();

    T getValue();

    List<TagKey<T>> getTagKeys();

}
