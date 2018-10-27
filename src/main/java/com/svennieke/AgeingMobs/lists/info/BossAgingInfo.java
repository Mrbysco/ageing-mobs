package com.svennieke.AgeingMobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;

public class BossAgingInfo extends RegularAgingInfo{
	private int maxInArea;
	private int checkRadius;
	
	public BossAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int maxInArea, int checkRadius, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.maxInArea = maxInArea;
		this.checkRadius = checkRadius;
	}

	public int getMaxInArea() {
		return maxInArea;
	}
	
	public void setMaxInArea(int maxInArea) {
		this.maxInArea = maxInArea;
	}
	
	public int getCheckRadius() {
		return checkRadius;
	}
	
	public void setCheckRadius(int checkRadius) {
		this.checkRadius = checkRadius;
	}
}
