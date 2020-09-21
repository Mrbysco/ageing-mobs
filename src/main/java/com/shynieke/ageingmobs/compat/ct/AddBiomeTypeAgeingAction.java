package com.shynieke.ageingmobs.compat.ct;

import com.shynieke.ageingmobs.lists.AgeList;
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
	private String gamestage;
	
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
	
	public AddBiomeTypeAgeingAction(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String biomeType, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		BiomeDictionary.Type selectedType = BiomeDictionary.Type.getType(biomeType);
		this.biometype = selectedType;
		this.gamestage = gamestage;
	}
	
	@Override
	public void apply()
	{
		if(gamestage != null && !gamestage.isEmpty())
			AgeList.addStagedBiomeTypeBasedAgeing(uniqueID, gamestage, entity, entityData, transformedEntity, changedEntityData, biometype, tickTime);
		else
			AgeList.addBiomeTypeBasedAgeing(uniqueID, entity, entityData, transformedEntity, changedEntityData, biometype, tickTime);
	}

	@Override
	public String describe() {
		if(gamestage != null && !gamestage.isEmpty())
			return String.format("%s has been added to the staged Biome Type ageing list.", new Object[] {this.uniqueID});	
		else
			return String.format("%s has been added to the Biome Type ageing list.", new Object[] {this.uniqueID});	
	}
}
