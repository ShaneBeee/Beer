package com.shanebeestudios.beer.api.registration;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;

public interface Definition<T> {

    Identifier getIdentifier();

    Holder.Reference<T> register();

    T getValue();

}
