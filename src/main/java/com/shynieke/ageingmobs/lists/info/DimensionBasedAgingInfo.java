package com.shynieke.ageingmobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;

public class DimensionBasedAgingInfo extends RegularAgingInfo{
	private int dimensionID;
	
	public DimensionBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int dimensionID, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.dimensionID = dimensionID;
	}

	public int getDimensionID() {
		return dimensionID;
	}
	
	public void setDimensionID(int dimensionID) {
		this.dimensionID = dimensionID;
	}
}
