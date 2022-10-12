package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.google.common.collect.Lists;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.List;

public class DimensionCriteria extends BaseCriteria {
	private List<ResourceLocation> dimensionID;

	public DimensionCriteria(iAgeing ageing, ResourceLocation[] dimensionID) {
		super(ageing);
		this.dimensionID = Lists.newArrayList(dimensionID);
	}

	public List<ResourceLocation> getDimensionID() {
		return dimensionID;
	}

	public void setDimensionID(List<ResourceLocation> dimensionID) {
		this.dimensionID = dimensionID;
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		return getDimensionID().contains(entityIn.getCommandSenderWorld().dimension().location());
	}
}
