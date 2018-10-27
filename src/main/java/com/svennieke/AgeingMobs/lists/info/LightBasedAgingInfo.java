package com.svennieke.AgeingMobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;

public class LightBasedAgingInfo extends RegularAgingInfo{
	private int lightLevelMin;
	private int lightLevelMax;
	private boolean aloneBased;
	private boolean reversable;
	
	public LightBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int lightLevelMin, int lightLevelMax, boolean aloneBased, boolean reversable, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.lightLevelMin = lightLevelMin;
		this.lightLevelMax = lightLevelMax;
		this.aloneBased = aloneBased;
		this.reversable = reversable;
	}

	public int getLightLevelMin() {
		return lightLevelMin;
	}
	
	public void setLightLevelMin(int lightLevelMin) {
		this.lightLevelMin = lightLevelMin;
	}
	
	public int getLightLevelMax() {
		return lightLevelMax;
	}
	
	public void setLightLevelMax(int lightLevelMax) {
		this.lightLevelMax = lightLevelMax;
	}
	
	public boolean isAloneBased() {
		return aloneBased;
	}
	
	public void setAloneBased(boolean aloneBased) {
		this.aloneBased = aloneBased;
	}
	
	public boolean isReversable() {
		return reversable;
	}
	
	public void setReversable(boolean reversable) {
		this.reversable = reversable;
	}
}
