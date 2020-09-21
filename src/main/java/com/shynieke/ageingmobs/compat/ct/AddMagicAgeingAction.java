package com.shynieke.ageingmobs.compat.ct;

import com.shynieke.ageingmobs.lists.AgeList;
import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;

public class AddMagicAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private String gamestage;
	
	public AddMagicAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
	}
	
	public AddMagicAgeingAction(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.gamestage = gamestage;
	}
	
	@Override
	public void apply()
	{
		if(gamestage != null && !gamestage.isEmpty())
			AgeList.addStagedMagicBasedAgeing(uniqueID, gamestage, entity, entityData, transformedEntity, changedEntityData, tickTime);
		else
			AgeList.addMagicBasedAgeing(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
	}

	@Override
	public String describe() {
		if(gamestage != null && !gamestage.isEmpty())
			return String.format("%s has been added to the staged Magic ageing list.", new Object[] {this.uniqueID});	
		else
			return String.format("%s has been added to the Magic ageing list.", new Object[] {this.uniqueID});	
	}
}
