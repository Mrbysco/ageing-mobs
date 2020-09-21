package com.shynieke.ageingmobs.compat.ct;

import com.shynieke.ageingmobs.lists.AgeList;
import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddEntityBasedAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private String nearbyEntity;
	private NBTTagCompound nearbyEntityData;
	private int radius;
	private String gamestage;
	
	public AddEntityBasedAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String nearbyEntity, NBTTagCompound nearbyEntityData, int radius, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.nearbyEntity = nearbyEntity;
		this.nearbyEntityData = nearbyEntityData;
		this.radius = radius;
	}
	
	public AddEntityBasedAgeingAction(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String nearbyEntity, NBTTagCompound nearbyEntityData, int radius, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.nearbyEntity = nearbyEntity;
		this.nearbyEntityData = nearbyEntityData;
		this.radius = radius;
		this.gamestage = gamestage;
	}
	
	@Override
	public void apply()
	{
		if(gamestage != null && !gamestage.isEmpty())
			AgeList.addStagedEntityBasedAgeing(uniqueID, gamestage, entity, entityData, transformedEntity, changedEntityData, nearbyEntity, nearbyEntityData, radius, tickTime);
		else
			AgeList.addEntityBasedAgeing(uniqueID, entity, entityData, transformedEntity, changedEntityData, nearbyEntity, nearbyEntityData, radius, tickTime);
	}

	@Override
	public String describe() {
		if(gamestage != null && !gamestage.isEmpty())
			return String.format("%s has been added to the staged Entity ageing list.", new Object[] {this.uniqueID});	
		else
			return String.format("%s has been added to the Entity ageing list.", new Object[] {this.uniqueID});	
	}
}
