package com.svennieke.AgeingMobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Optional;
public class RegularAgingInfo{
	private String gamestage;
	
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String evolvedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	
	public RegularAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.evolvedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
	}
	
	public String getUniqueID() {
		return uniqueID;
	}
	
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	
	public String getEntity() {
		return entity;
	}
	
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public NBTTagCompound getEntityData() {
		return entityData;
	}
	
	public void setEntityData(NBTTagCompound entityData) {
		this.entityData = entityData;
	}
	
	public String getEvolvedEntity() {
		return evolvedEntity;
	}
	
	public void setEvolvedEntity(String evolvedEntity) {
		this.evolvedEntity = evolvedEntity;
	}
	
	public NBTTagCompound getChangedEntityData() {
		return changedEntityData;
	}
	
	public void setChangedEntityData(NBTTagCompound changedEntityData) {
		this.changedEntityData = changedEntityData;
	}
	
	public int getTickTime() {
		return tickTime;
	}
	
	public void setTickTime(int tickTime) {
		this.tickTime = tickTime;
	}
	
	@Optional.Method(modid = "gamestages")
	public void setGameStage(String stage) {
		this.gamestage = stage;
	}

	@Optional.Method(modid = "gamestages")
	public String getGameStage() {
		return this.gamestage;
	}
}
