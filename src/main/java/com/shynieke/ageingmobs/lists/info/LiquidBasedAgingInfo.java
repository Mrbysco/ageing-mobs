package com.shynieke.ageingmobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;

public class LiquidBasedAgingInfo extends RegularAgingInfo{
	private String liquid;
	private boolean reversible;
	
	public LiquidBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String liquid, Boolean reversible, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.liquid = liquid;
		this.reversible = reversible;
	}
	
	public String getLiquid() {
		return liquid;
	}
		
	public void setLiquid(String liquid) {
		this.liquid = liquid;
	}

	public boolean isReversible() {
		return reversible;
	}
	
	public void setReversible(boolean reversible) {
		this.reversible = reversible;
	}
}
