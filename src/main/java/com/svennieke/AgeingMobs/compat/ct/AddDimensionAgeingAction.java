package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddDimensionAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private int dimension;
	
	public AddDimensionAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int dimension, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.dimension = dimension;
	}
	
	@Override
	public void apply()
	{
		AgeList.addDimensionBasedBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, dimension, tickTime);
	}

	@Override
	public String describe() {
		return String.format("%s has been added to the Dimension ageing list.", new Object[] {this.uniqueID});	
	}
}
