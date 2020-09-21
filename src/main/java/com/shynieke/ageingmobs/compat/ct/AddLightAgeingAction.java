package com.shynieke.ageingmobs.compat.ct;

import com.shynieke.ageingmobs.lists.AgeList;
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
	private String gamestage;
	
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
	
	public AddLightAgeingAction(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minLevel, int maxLevel, boolean alone, boolean reversable, int tickTime) {
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
		this.gamestage = gamestage;
	}
	
	@Override
	public void apply()
	{
		if(gamestage != null && !gamestage.isEmpty())
			AgeList.addStagedDarknessBasedAgeing(uniqueID, gamestage, entity, entityData, transformedEntity, changedEntityData, minLevel, maxLevel, alone, reversable, tickTime);
		else
			AgeList.addDarknessBasedAgeing(uniqueID, entity, entityData, transformedEntity, changedEntityData, minLevel, maxLevel, alone, reversable, tickTime);
	}

	@Override
	public String describe() {
		if(gamestage != null && !gamestage.isEmpty())
			return String.format("%s has been added to the staged Light Level ageing list.", new Object[] {this.uniqueID});	
		else
			return String.format("%s has been added to the Light Level ageing list.", new Object[] {this.uniqueID});	
	}
}
