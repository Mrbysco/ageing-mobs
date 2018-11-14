package com.svennieke.AgeingMobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;

public class HeightBasedAgingInfo extends RegularAgingInfo{
	private int minHeight;
	private int maxHeight;
	
	public HeightBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minHeight, int maxHeight, int tickHeight) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickHeight);
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}
	
	public int getMinHeight() {
		return minHeight;
	}
	
	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}
	
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}
	
	public int getMaxHeight() {
		return maxHeight;
	}
}
