package com.shynieke.ageingmobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeTypeBasedAgingInfo extends RegularAgingInfo{
	private BiomeDictionary.Type biomeType;
	
	public BiomeTypeBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, BiomeDictionary.Type biomeType, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.biomeType = biomeType;
	}
	
	public BiomeDictionary.Type getBiomeType() {
		return biomeType;
	}
	
	public void setBiomeType(BiomeDictionary.Type biomeType) {
		this.biomeType = biomeType;
	}
}
