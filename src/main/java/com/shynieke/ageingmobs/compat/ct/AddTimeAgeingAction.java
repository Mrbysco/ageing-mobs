package com.shynieke.ageingmobs.compat.ct;

import com.shynieke.ageingmobs.lists.AgeList;
import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddTimeAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private int minTime;
	private int maxTime;
	private String gamestage;
	
	public AddTimeAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minTime, int maxTime, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.minTime = minTime;
		this.maxTime = maxTime;
	}
	
	public AddTimeAgeingAction(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minTime, int maxTime, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.minTime = minTime;
		this.maxTime = maxTime;
		this.gamestage = gamestage;
	}
	
	@Override
	public void apply()
	{
		if(gamestage != null && !gamestage.isEmpty())
			AgeList.addStagedTimeBasedAgeing(uniqueID, gamestage, entity, entityData, transformedEntity, changedEntityData, minTime, maxTime, tickTime);
		else
			AgeList.addTimeBasedAgeing(uniqueID, entity, entityData, transformedEntity, changedEntityData, minTime, maxTime, tickTime);
	}

	@Override
	public String describe() {
		if(gamestage != null && !gamestage.isEmpty())
			return String.format("%s has been added to the staged Time ageing list.", new Object[] {this.uniqueID});	
		else
		return String.format("%s has been added to the Time ageing list.", new Object[] {this.uniqueID});	
	}
}
