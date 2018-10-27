package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddMagicAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	
	public AddMagicAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
	}
	
	@Override
	public void apply()
	{
		AgeList.addMagicBasedBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
	}

	@Override
	public String describe() {
		return String.format("%s has been added to the Magic ageing list.", new Object[] {this.uniqueID});	
	}
}
