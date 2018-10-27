package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddLightAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private int minLevel;
	private int maxLevel;
	private boolean alone;
	private boolean reversable;
	
	public AddLightAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minLevel, int maxLevel, boolean alone, boolean reversable, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.alone = alone;
		this.reversable = reversable;
	}
	
	@Override
	public void apply()
	{
		AgeList.addDarknessBasedBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, minLevel, maxLevel, alone, reversable, tickTime);
	}

	@Override
	public String describe() {
		return String.format("%s has been added to the Light Level ageing list.", new Object[] {this.uniqueID});	
	}
}
