package com.svennieke.AgeingMobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;

public class MoonBasedAgingInfo extends RegularAgingInfo{
	private String moonPhase;
	
	public MoonBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String moonPhase, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.moonPhase = moonPhase;
	}
	
	public String getMoonPhase() {
		return moonPhase;
	}
	
	public void setMoonPhase(String time) {
		this.moonPhase = time;
	}
}
