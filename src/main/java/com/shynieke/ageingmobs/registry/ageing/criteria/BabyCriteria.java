package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class BabyCriteria extends BaseCriteria {
	private boolean inverted;

	public BabyCriteria(iAgeing ageing, boolean inverted) {
		super(ageing);
		this.inverted = inverted;
	}

	public BabyCriteria(iAgeing ageing) {
		this(ageing, false);
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		if (entityIn instanceof LivingEntity livingEntity) {
			if (inverted) {
				return !livingEntity.isBaby();
			} else {
				return livingEntity.isBaby();
			}
		}
		return false;
	}
}
