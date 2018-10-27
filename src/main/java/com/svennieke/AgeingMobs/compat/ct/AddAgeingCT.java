package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.AgeingMobs;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.world.IBiome;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.ageingmobs")
@ZenRegister
public class AddAgeingCT {
	//Regular
	@ZenMethod
	public static void addRegularAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, int tickTime) {
        CraftTweakerAPI.apply(new AddRegularAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), tickTime));
	}
	
	@ZenMethod
	public static void addRegularAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int tickTime) {
		CraftTweakerAPI.apply(new AddRegularAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), tickTime));
	}

	//Boss
	@ZenMethod
	public static void addBossAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, int maxInArea, int checkRange, int tickTime) {
		CraftTweakerAPI.apply(new AddBossAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), maxInArea, checkRange, tickTime));
	}
	
	@ZenMethod
	public static void addBossAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int maxInArea, int checkRange, int tickTime) {
		CraftTweakerAPI.apply(new AddBossAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), maxInArea, checkRange, tickTime));
	}

	//Weather
	@ZenMethod
	public static void addWeatherAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String weather, int tickTime) {
		CraftTweakerAPI.apply(new AddWeatherAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), weather, tickTime));
	}
	
	@ZenMethod
	public static void addWeatherAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String weather, int tickTime) {
		CraftTweakerAPI.apply(new AddWeatherAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), weather, tickTime));
	}

	//Time
	@ZenMethod
	public static void addTimeAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, int minTime, int maxTime, int tickTime) {
		CraftTweakerAPI.apply(new AddTimeAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), minTime, maxTime, tickTime));
	}
	
	@ZenMethod
	public static void addTimeAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int minTime, int maxTime, int tickTime) {
		CraftTweakerAPI.apply(new AddTimeAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), minTime, maxTime, tickTime));
	}

	//Light Level
	@ZenMethod
	public static void addLightLevelAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, int minLevel, int maxLevel, boolean alone, boolean reversable, int tickTime) {
		CraftTweakerAPI.apply(new AddLightAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), minLevel, maxLevel, alone, reversable, tickTime));
	}
	
	@ZenMethod
	public static void addLightLevelAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int minLevel, int maxLevel, boolean alone, boolean reversable, int tickTime) {
		CraftTweakerAPI.apply(new AddLightAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), minLevel, maxLevel, alone, reversable, tickTime));
	}

	//Biome
	@ZenMethod
	public static void addBiomeAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String biome, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biome, tickTime));
	}
	
	@ZenMethod
	public static void addBiomeAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String biome, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biome, tickTime));
	}
	
	@ZenMethod
	public static void addBiomeAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, IBiome biome, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biome.getId(), tickTime));
	}
	
	@ZenMethod
	public static void addBiomeAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, IBiome biome, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biome.getId(), tickTime));
	}
	
	@ZenMethod
	public static void addBiomeAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String[] biome, int tickTime) {
		if(biome.length > 0)
		{
			for(int i = 0; i < biome.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biome[i], tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addBiomeAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String[] biome, int tickTime) {
		if(biome.length > 0)
		{
			for(int i = 0; i < biome.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biome[i], tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addBiomeAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, IBiome[] biome, int tickTime) {
		if(biome.length > 0)
		{
			for(int i = 0; i < biome.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biome[i].getId(), tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addBiomeAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, IBiome[] biome, int tickTime) {
		if(biome.length > 0)
		{
			for(int i = 0; i < biome.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biome[i].getId(), tickTime));
			}
		}
	}

	//Biome Type
	@ZenMethod
	public static void addBiomeTypeAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String biomeType, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeTypeAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biomeType, tickTime));
	}
	
	@ZenMethod
	public static void addBiomeTypeAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String biomeType, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeTypeAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biomeType, tickTime));
	}

	//Block
	@ZenMethod
	public static void addBlockAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String block, int tickTime) {
		CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), block, tickTime));
	}
	
	@ZenMethod
	public static void addBlockAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String block, int tickTime) {
		CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), block, tickTime));
	}
	
	@ZenMethod
	public static void addBlockNearbyAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String block, int radius, int tickTime) {
		CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), block, radius, tickTime));
	}
	
	@ZenMethod
	public static void addBlockNearbyAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String block, int radius, int tickTime) {
		CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), block, radius, tickTime));
	}
	
	@ZenMethod
	public static void addBlockAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String[] block, int tickTime) {
		if(block.length > 0)
		{
			for(int i = 0; i < block.length; i++)
			{
				CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), block[i], tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addBlockAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String block[], int tickTime) {
		if(block.length > 0)
		{
			for(int i = 0; i < block.length; i++)
			{
				CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), block[i], tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addBlockNearbyAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String block[], int radius, int tickTime) {
		if(block.length > 0)
		{
			for(int i = 0; i < block.length; i++)
			{
				CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), block[i], radius, tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addBlockNearbyAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String block[], int radius, int tickTime) {
		if(block.length > 0)
		{
			for(int i = 0; i < block.length; i++)
			{
				CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), block[i], radius, tickTime));
			}
		}
	}

	//Dimension
	@ZenMethod
	public static void addDimensionAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, int dimension, int tickTime) {
		CraftTweakerAPI.apply(new AddDimensionAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), dimension, tickTime));
	}
	
	@ZenMethod
	public static void addDimensionAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int dimension, int tickTime) {
		CraftTweakerAPI.apply(new AddDimensionAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), dimension, tickTime));
	}
	
	@ZenMethod
	public static void addDimensionAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, int[] dimensionArray, int tickTime) {
		if(dimensionArray.length > 0)
		{
			for(int i = 0; i < dimensionArray.length; i++)
			{
				CraftTweakerAPI.apply(new AddDimensionAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), dimensionArray[i], tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addDimensionAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int[] dimensionArray, int tickTime) {
		if(dimensionArray.length > 0)
		{
			for(int i = 0; i < dimensionArray.length; i++)
			{
				CraftTweakerAPI.apply(new AddDimensionAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), dimensionArray[i], tickTime));
			}
		}
	}
	
	//Moon Phase
	@ZenMethod
	public static void AddMoonPhaseAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String moonPhase, int tickTime) {
		CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), moonPhase, tickTime));
	}
	
	@ZenMethod
	public static void AddMoonPhaseAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String moonPhase, int tickTime) {
		CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), moonPhase, tickTime));
	}
	
	@ZenMethod
	public static void AddMoonPhaseAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String[] moonPhase, int tickTime) {
		if(moonPhase.length > 0)
		{
			for(int i = 0; i < moonPhase.length; i++)
			{
				CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), moonPhase[i], tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void AddMoonPhaseAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String[] moonPhase, int tickTime) {
		if(moonPhase.length > 0)
		{
			for(int i = 0; i < moonPhase.length; i++)
			{
				CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), moonPhase[i], tickTime));
			}
		}
	}
	
	//Magic
	@ZenMethod
	public static void AddMagicAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, int tickTime) {
		CraftTweakerAPI.apply(new AddMagicAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), tickTime));
	}
	
	@ZenMethod
	public static void AddMagicAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int tickTime) {
		CraftTweakerAPI.apply(new AddMagicAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), tickTime));
	}
	
	public static NBTTagCompound createNBTTag(String nbtData)
	{
        NBTTagCompound tag = new NBTTagCompound();

		try
        {
        	String data = nbtData;
        	if(data.startsWith("{") && data.endsWith("}"))
        	{
        		tag = JsonToNBT.getTagFromJson(data);
        	}
        	else
        	{
        		tag = JsonToNBT.getTagFromJson("{" + data + "}");
        	}
        }
        catch (NBTException nbtexception)
        {
        	AgeingMobs.logger.error("nope... " +  nbtexception);
        }
		
		return tag;
	}
}
