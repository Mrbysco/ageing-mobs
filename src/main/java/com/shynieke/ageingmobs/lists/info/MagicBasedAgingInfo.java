package com.shynieke.ageingmobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;

public class MagicBasedAgingInfo extends RegularAgingInfo{
	
	public MagicBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
	}
}
