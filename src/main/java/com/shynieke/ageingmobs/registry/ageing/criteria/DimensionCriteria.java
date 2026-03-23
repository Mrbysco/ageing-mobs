package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.google.common.collect.Lists;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.List;

public class DimensionCriteria extends BaseCriteria {
	private List<Identifier> dimensionID;

	public DimensionCriteria(iAgeing ageing, Identifier[] dimensionID) {
		super(ageing);
		this.dimensionID = Lists.newArrayList(dimensionID);
	}

	public List<Identifier> getDimensionID() {
		return dimensionID;
	}

	public void setDimensionID(List<Identifier> dimensionID) {
		this.dimensionID = dimensionID;
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		return getDimensionID().contains(entityIn.level().dimension().identifier());
	}
}
