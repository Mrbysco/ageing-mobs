package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.BiomeDictionary;

public class AddBiomeTypeAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private BiomeDictionary.Type biometype;
	
	public AddBiomeTypeAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String biomeType, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		BiomeDictionary.Type selectedType = BiomeDictionary.Type.getType(biomeType);
		this.biometype = selectedType;
	}
	
	@Override
	public void apply()
	{
		AgeList.addBiomeTypeBasedBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, biometype, tickTime);
	}

	@Override
	public String describe() {
		return String.format("%s has been added to the Biome Type ageing list.", new Object[] {this.uniqueID});	
	}
}
