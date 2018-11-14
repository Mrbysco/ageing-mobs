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
	private String gamestage;
	
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
	
	public AddBossAgeingAction(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int maxInArea, int checkRange, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.maxInArea = maxInArea;
		this.checkRange = checkRange;
		this.gamestage = gamestage;
	}
	
	@Override
	public void apply()
	{
		if(gamestage != null && !gamestage.isEmpty())
			AgeList.addStagedBossAgeing(uniqueID, gamestage, entity, entityData, transformedEntity, changedEntityData, maxInArea, checkRange, tickTime);
		else
			AgeList.addBossAgeing(uniqueID, entity, entityData, transformedEntity, changedEntityData, maxInArea, checkRange, tickTime);
	}

	@Override
	public String describe() {
		if(gamestage != null && !gamestage.isEmpty())
			return String.format("%s has been added to the staged Boss ageing list.", new Object[] {this.uniqueID});	
		else
			return String.format("%s has been added to the Boss ageing list.", new Object[] {this.uniqueID});	
	}
}
