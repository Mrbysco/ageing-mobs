package com.shynieke.ageingmobs.lists.info;

import net.minecraft.nbt.NBTTagCompound;

public class WeatherBasedAgingInfo extends RegularAgingInfo{
	private String weather;
	
	public WeatherBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String weather, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.weather = weather;
	}
	
	public String getWeather() {
		return weather;
	}
	
	public void setWeather(String weather) {
		this.weather = weather;
	}
}
