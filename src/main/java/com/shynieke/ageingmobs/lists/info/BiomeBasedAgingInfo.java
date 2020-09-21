package com.shynieke.ageingmobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;

public class BiomeBasedAgingInfo extends RegularAgingInfo{
	private Biome biome;
	
	public BiomeBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, Biome biome, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.biome = biome;
	}
	
	public Biome getBiome() {
		return biome;
	}
	
	public void setBiome(Biome biome) {
		this.biome = biome;
	}
}
