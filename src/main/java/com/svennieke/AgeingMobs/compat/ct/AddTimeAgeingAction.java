package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

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
	
	@Override
	public void apply()
	{
		AgeList.addTimeBasedBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, minTime, maxTime, tickTime);
	}

	@Override
	public String describe() {
		return String.format("%s has been added to the Time ageing list.", new Object[] {this.uniqueID});	
	}
}
