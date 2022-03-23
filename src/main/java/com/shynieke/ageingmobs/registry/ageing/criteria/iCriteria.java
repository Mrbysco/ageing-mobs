package com.shynieke.ageingmobs.registry.ageing.criteria;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface iCriteria {
	boolean checkCriteria(Level worldIn, Entity entityIn);

	default boolean isReversing() {
		return false;
	}
}
