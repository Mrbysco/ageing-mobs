package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class AddBiomeAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private Biome biome;
	
	public AddBiomeAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String biome, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		
		Biome selectedBiome = ForgeRegistries.BIOMES.getValue(getBiomeResourceLocation(biome));
		this.biome = selectedBiome;
	}
	
	@Override
	public void apply()
	{
		AgeList.addBiomeBasedBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, biome, tickTime);
	}

	@Override
	public String describe() {
		return String.format("%s has been added to the Biome ageing list.", new Object[] {this.uniqueID});	
	}
	
	public static ResourceLocation getBiomeResourceLocation(String name)
	{
		String[] splitResource = name.split(":");
		if (splitResource.length != 2)
			return new ResourceLocation(name);
		else
			return new ResourceLocation(splitResource[0], splitResource[1]);
	}
}
