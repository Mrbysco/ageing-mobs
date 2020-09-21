package com.shynieke.ageingmobs.compat.ct;

import com.shynieke.ageingmobs.lists.AgeList;
import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddHeightAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private int minHeigth;
	private int maxHeigth;
	private String gamestage;
	
	public AddHeightAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minHeigth, int maxHeigth, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.minHeigth = minHeigth;
		this.maxHeigth = maxHeigth;
	}
	
	public AddHeightAgeingAction(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minHeigth, int maxHeigth, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.minHeigth = minHeigth;
		this.maxHeigth = maxHeigth;
		this.gamestage = gamestage;
	}
	
	@Override
	public void apply()
	{
		if(gamestage != null && !gamestage.isEmpty())
			AgeList.addStagedHeightBasedAgeing(uniqueID, gamestage, entity, entityData, transformedEntity, changedEntityData, minHeigth, maxHeigth, tickTime);
		else
			AgeList.addHeightBasedAgeing(uniqueID, entity, entityData, transformedEntity, changedEntityData, minHeigth, maxHeigth, tickTime);
	}

	@Override
	public String describe() {
		if(gamestage != null && !gamestage.isEmpty())
			return String.format("%s has been added to the staged Height ageing list.", new Object[] {this.uniqueID});	
		else
			return String.format("%s has been added to the Height ageing list.", new Object[] {this.uniqueID});	
	}
}
