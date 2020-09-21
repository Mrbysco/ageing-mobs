package com.shynieke.ageingmobs.compat.ct;

import com.shynieke.ageingmobs.lists.AgeList;
import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddWeatherAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private String weather;
	private String gamestage;
	
	public AddWeatherAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String weather, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.weather = weather;
	}
	
	public AddWeatherAgeingAction(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String weather, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.weather = weather;
		this.gamestage = gamestage;
	}
	
	@Override
	public void apply()
	{
		if(gamestage != null && !gamestage.isEmpty())
			AgeList.addStagedWeatherBasedAgeing(uniqueID, gamestage, entity, entityData, transformedEntity, changedEntityData, weather, tickTime);
		else
			AgeList.addWeatherBasedAgeing(uniqueID, entity, entityData, transformedEntity, changedEntityData, weather, tickTime);
	}

	@Override
	public String describe() {
		if(gamestage != null && !gamestage.isEmpty())
			return String.format("%s has been added to the staged Weather ageing list.", new Object[] {this.uniqueID});	
		else
			return String.format("%s has been added to the Weather ageing list.", new Object[] {this.uniqueID});	
	}
}
