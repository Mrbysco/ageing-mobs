package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

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
	
	public AddWeatherAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String weather, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.weather = weather;
	}
	
	@Override
	public void apply()
	{
		AgeList.addWeatherBasedBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, weather, tickTime);
	}

	@Override
	public String describe() {
		return String.format("%s has been added to the Weather ageing list.", new Object[] {this.uniqueID});	
	}
}
