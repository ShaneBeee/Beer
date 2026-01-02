package com.shanebeestudios.beer.api.registration;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

public interface Definition<T> {

    Holder.Reference<T> register();

    ResourceKey<T> getResourceKey();

    T getValue();

}
