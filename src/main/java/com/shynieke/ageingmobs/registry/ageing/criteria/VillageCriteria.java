package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class VillageCriteria extends BaseCriteria {

	public VillageCriteria(iAgeing ageing) {
		super(ageing);
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		if (level instanceof ServerLevel serverLevel) {
			return serverLevel.isVillage(entityIn.blockPosition());
		}
		return false;
	}
}
