package com.svennieke.AgeingMobs.lists.info;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class EntityBasedAgingInfo extends RegularAgingInfo{
	private String nearbyEntity;
	private NBTTagCompound nearbyEntityData;
	private int radius;
	
	public EntityBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String nearbyEntity, NBTTagCompound nearbyEntityData, int radius, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.nearbyEntity = nearbyEntity;
		this.nearbyEntityData = nearbyEntityData;
		this.radius = radius;
	}
	
	public String getNearbyEntity() {
		return nearbyEntity;
	}
	
	public void setNearbyEntity(String nearbyEntity) {
		this.nearbyEntity = nearbyEntity;
	}
	
	public NBTTagCompound getNearbyEntityData() {
		return nearbyEntityData;
	}
	
	public void setNearbyEntityData(NBTTagCompound nearbyEntityData) {
		this.nearbyEntityData = nearbyEntityData;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
}
