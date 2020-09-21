package com.shynieke.ageingmobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;

public class TimeBasedAgingInfo extends RegularAgingInfo{
	private int minTime;
	private int maxTime;
	
	public TimeBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minTime, int maxTime, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.minTime = minTime;
		this.maxTime = maxTime;
	}
	
	public int getMinTime() {
		return minTime;
	}
	
	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}
	
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}
	
	public int getMaxTime() {
		return maxTime;
	}
}
