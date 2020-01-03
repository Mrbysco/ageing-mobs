package com.shynieke.ageingmobs.registry.ageing.criteria;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface iCriteria {
    boolean checkCriteria(World worldIn, Entity entityIn);

    default boolean isReversing() {
        return false;
    }
}
