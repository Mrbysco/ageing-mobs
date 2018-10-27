package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddMoonAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private String moonPhase;
	
	public AddMoonAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String moonPhase, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.moonPhase = moonPhase;
	}
	
	@Override
	public void apply()
	{
		AgeList.addMoonBasedBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, moonPhase, tickTime);
	}

	@Override
	public String describe() {
		return String.format("%s has been added to the Moon Phase ageing list.", new Object[] {this.uniqueID});	
	}
}
