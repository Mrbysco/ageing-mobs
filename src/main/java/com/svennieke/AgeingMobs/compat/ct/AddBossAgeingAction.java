package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddBossAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private int maxInArea;
	private int checkRange;
	
	public AddBossAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int maxInArea, int checkRange, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.maxInArea = maxInArea;
		this.checkRange = checkRange;
	}
	
	@Override
	public void apply()
	{
		AgeList.addBossBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, maxInArea, checkRange, tickTime);
	}

	@Override
	public String describe() {
		return String.format("%s has been added to the Boss ageing list.", new Object[] {this.uniqueID});	
	}
}
