package com.shynieke.ageingmobs.compat.ct;

import com.shynieke.ageingmobs.AgeingMobs;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.world.IBiome;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Optional;
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
	
	@ZenMethod
	public static void addBiomeTypeAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String[] biomeType, int tickTime) {
		if(biomeType.length > 0)
		{
			for(int i = 0; i < biomeType.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeTypeAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biomeType[i], tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addBiomeTypeAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String[] biomeType, int tickTime) {
		if(biomeType.length > 0)
		{
			for(int i = 0; i < biomeType.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeTypeAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biomeType[i], tickTime));
			}
		}
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
	public static void addMoonPhaseAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String moonPhase, int tickTime) {
		CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), moonPhase, tickTime));
	}
	
	@ZenMethod
	public static void addMoonPhaseAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String moonPhase, int tickTime) {
		CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), moonPhase, tickTime));
	}
	
	@ZenMethod
	public static void addMoonPhaseAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String[] moonPhase, int tickTime) {
		if(moonPhase.length > 0)
		{
			for(int i = 0; i < moonPhase.length; i++)
			{
				CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), moonPhase[i], tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addMoonPhaseAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String[] moonPhase, int tickTime) {
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
	public static void addMagicAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, int tickTime) {
		CraftTweakerAPI.apply(new AddMagicAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), tickTime));
	}
	
	@ZenMethod
	public static void addMagicAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int tickTime) {
		CraftTweakerAPI.apply(new AddMagicAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), tickTime));
	}
	
	//Liquid
	@ZenMethod
	public static void addLiquidAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String liquid, boolean reversible, int tickTime) {
		CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), liquid, reversible, tickTime));
	}
	
	@ZenMethod
	public static void addLiquidAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String liquid, boolean reversible, int tickTime) {
		CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), liquid, reversible, tickTime));
	}
	
	@ZenMethod
	public static void addLiquidAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, ILiquidStack liquid, boolean reversible, int tickTime) {
		CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), liquid.getName(), reversible, tickTime));
	}
	
	@ZenMethod
	public static void addLiquidAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, ILiquidStack liquid, boolean reversible, int tickTime) {
		CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), liquid.getName(), reversible, tickTime));
	}
	
	@ZenMethod
	public static void addLiquidAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String[] liquid, boolean reversible, int tickTime) {
		if(liquid.length > 0)
		{
			for(int i = 0; i < liquid.length; i++)
			{
				CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), liquid[i], reversible, tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addLiquidAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String[] liquid, boolean reversible, int tickTime) {
		if(liquid.length > 0)
		{
			for(int i = 0; i < liquid.length; i++)
			{
				CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), liquid[i], reversible, tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addLiquidAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, ILiquidStack[] liquid, boolean reversible, int tickTime) {
		if(liquid.length > 0)
		{
			for(int i = 0; i < liquid.length; i++)
			{
				CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), liquid[i].getName(), reversible, tickTime));
			}
		}
	}
	
	@ZenMethod
	public static void addLiquidAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, ILiquidStack[] liquid, boolean reversible, int tickTime) {
		if(liquid.length > 0)
		{
			for(int i = 0; i < liquid.length; i++)
			{
				CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), liquid[i].getName(), reversible, tickTime));
			}
		}
	}
	
	//Height
	@ZenMethod
	public static void addHeightAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, int minHeight, int maxHeight, int tickTime) {
		CraftTweakerAPI.apply(new AddHeightAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), minHeight, maxHeight, tickTime));
	}
	
	@ZenMethod
	public static void addHeightAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int minHeight, int maxHeight, int tickTime) {
		CraftTweakerAPI.apply(new AddHeightAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), minHeight, maxHeight, tickTime));
	}

	//Entity
	@ZenMethod
	public static void addNearbyEntityAgeing(String uniqueID, String entity, String entityData, String transformedEntity, String changedEntityData, String nearbyEntity, String nearbyEntityData, int radius, int tickTime) {
        CraftTweakerAPI.apply(new AddEntityBasedAgeingAction(uniqueID, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), nearbyEntity, createNBTTag(changedEntityData), radius, tickTime));
	}
	
	@ZenMethod
	public static void addNearbyEntityAgeing(String uniqueID, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, IEntityDefinition nearbyEntity, String nearbyEntityData, int radius, int tickTime) {
		CraftTweakerAPI.apply(new AddEntityBasedAgeingAction(uniqueID, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), nearbyEntity.getId(), createNBTTag(changedEntityData), tickTime, tickTime));
	}
	
	//Regular
	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedRegularAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, int tickTime) {
        CraftTweakerAPI.apply(new AddRegularAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedRegularAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int tickTime) {
		CraftTweakerAPI.apply(new AddRegularAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), tickTime));
	}

	@Optional.Method(modid = "gamestages")
	//Boss
	@ZenMethod
	public static void addStagedBossAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, int maxInArea, int checkRange, int tickTime) {
		CraftTweakerAPI.apply(new AddBossAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), maxInArea, checkRange, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBossAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int maxInArea, int checkRange, int tickTime) {
		CraftTweakerAPI.apply(new AddBossAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), maxInArea, checkRange, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	//Weather
	@ZenMethod
	public static void addStagedWeatherAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String weather, int tickTime) {
		CraftTweakerAPI.apply(new AddWeatherAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), weather, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedWeatherAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String weather, int tickTime) {
		CraftTweakerAPI.apply(new AddWeatherAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), weather, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	//Time
	@ZenMethod
	public static void addStagedTimeAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, int minTime, int maxTime, int tickTime) {
		CraftTweakerAPI.apply(new AddTimeAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), minTime, maxTime, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedTimeAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int minTime, int maxTime, int tickTime) {
		CraftTweakerAPI.apply(new AddTimeAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), minTime, maxTime, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	//Light Level
	@ZenMethod
	public static void addStagedLightLevelAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, int minLevel, int maxLevel, boolean alone, boolean reversable, int tickTime) {
		CraftTweakerAPI.apply(new AddLightAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), minLevel, maxLevel, alone, reversable, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedLightLevelAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int minLevel, int maxLevel, boolean alone, boolean reversable, int tickTime) {
		CraftTweakerAPI.apply(new AddLightAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), minLevel, maxLevel, alone, reversable, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	//Biome
	@ZenMethod
	public static void addStagedBiomeAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String biome, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biome, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String biome, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biome, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, IBiome biome, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biome.getId(), tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, IBiome biome, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biome.getId(), tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String[] biome, int tickTime) {
		if(biome.length > 0)
		{
			for(int i = 0; i < biome.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biome[i], tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String[] biome, int tickTime) {
		if(biome.length > 0)
		{
			for(int i = 0; i < biome.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biome[i], tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, IBiome[] biome, int tickTime) {
		if(biome.length > 0)
		{
			for(int i = 0; i < biome.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biome[i].getId(), tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, IBiome[] biome, int tickTime) {
		if(biome.length > 0)
		{
			for(int i = 0; i < biome.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biome[i].getId(), tickTime));
			}
		}
	}

	//Biome Type
	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeTypeAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String biomeType, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeTypeAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biomeType, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeTypeAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String biomeType, int tickTime) {
		CraftTweakerAPI.apply(new AddBiomeTypeAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biomeType, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeTypeAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String[] biomeType, int tickTime) {
		if(biomeType.length > 0)
		{
			for(int i = 0; i < biomeType.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeTypeAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), biomeType[i], tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBiomeTypeAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String[] biomeType, int tickTime) {
		if(biomeType.length > 0)
		{
			for(int i = 0; i < biomeType.length; i++)
			{
				CraftTweakerAPI.apply(new AddBiomeTypeAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), biomeType[i], tickTime));
			}
		}
	}

	//Block
	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBlockAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String block, int tickTime) {
		CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), block, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBlockAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String block, int tickTime) {
		CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), block, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBlockNearbyAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String block, int radius, int tickTime) {
		CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), block, radius, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBlockNearbyAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String block, int radius, int tickTime) {
		CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), block, radius, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBlockAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String[] block, int tickTime) {
		if(block.length > 0)
		{
			for(int i = 0; i < block.length; i++)
			{
				CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), block[i], tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBlockAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String block[], int tickTime) {
		if(block.length > 0)
		{
			for(int i = 0; i < block.length; i++)
			{
				CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), block[i], tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBlockNearbyAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String block[], int radius, int tickTime) {
		if(block.length > 0)
		{
			for(int i = 0; i < block.length; i++)
			{
				CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), block[i], radius, tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedBlockNearbyAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String block[], int radius, int tickTime) {
		if(block.length > 0)
		{
			for(int i = 0; i < block.length; i++)
			{
				CraftTweakerAPI.apply(new AddBlockAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), block[i], radius, tickTime));
			}
		}
	}

	//Dimension
	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedDimensionAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, int dimension, int tickTime) {
		CraftTweakerAPI.apply(new AddDimensionAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), dimension, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedDimensionAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int dimension, int tickTime) {
		CraftTweakerAPI.apply(new AddDimensionAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), dimension, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedDimensionAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, int[] dimensionArray, int tickTime) {
		if(dimensionArray.length > 0)
		{
			for(int i = 0; i < dimensionArray.length; i++)
			{
				CraftTweakerAPI.apply(new AddDimensionAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), dimensionArray[i], tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedDimensionAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int[] dimensionArray, int tickTime) {
		if(dimensionArray.length > 0)
		{
			for(int i = 0; i < dimensionArray.length; i++)
			{
				CraftTweakerAPI.apply(new AddDimensionAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), dimensionArray[i], tickTime));
			}
		}
	}
	
	//Moon Phase
	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedMoonPhaseAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String moonPhase, int tickTime) {
		CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), moonPhase, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedMoonPhaseAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String moonPhase, int tickTime) {
		CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), moonPhase, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedMoonPhaseAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String[] moonPhase, int tickTime) {
		if(moonPhase.length > 0)
		{
			for(int i = 0; i < moonPhase.length; i++)
			{
				CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), moonPhase[i], tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedMoonPhaseAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String[] moonPhase, int tickTime) {
		if(moonPhase.length > 0)
		{
			for(int i = 0; i < moonPhase.length; i++)
			{
				CraftTweakerAPI.apply(new AddMoonAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), moonPhase[i], tickTime));
			}
		}
	}
	
	//Magic
	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedMagicAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, int tickTime) {
		CraftTweakerAPI.apply(new AddMagicAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedMagicAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int tickTime) {
		CraftTweakerAPI.apply(new AddMagicAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), tickTime));
	}
	
	//Liquid
	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedLiquidAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String liquid, boolean reversible, int tickTime) {
		CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), liquid, reversible, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedLiquidAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String liquid, boolean reversible, int tickTime) {
		CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), liquid, reversible, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedLiquidAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, ILiquidStack liquid, boolean reversible, int tickTime) {
		CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), liquid.getName(), reversible, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedLiquidAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, ILiquidStack liquid, boolean reversible, int tickTime) {
		CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), liquid.getName(), reversible, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedLiquidAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String[] liquid, boolean reversible, int tickTime) {
		if(liquid.length > 0)
		{
			for(int i = 0; i < liquid.length; i++)
			{
				CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), liquid[i], reversible, tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedLiquidAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, String[] liquid, boolean reversible, int tickTime) {
		if(liquid.length > 0)
		{
			for(int i = 0; i < liquid.length; i++)
			{
				CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), liquid[i], reversible, tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedLiquidAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, ILiquidStack[] liquid, boolean reversible, int tickTime) {
		if(liquid.length > 0)
		{
			for(int i = 0; i < liquid.length; i++)
			{
				CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), liquid[i].getName(), reversible, tickTime));
			}
		}
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedLiquidAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, ILiquidStack[] liquid, boolean reversible, int tickTime) {
		if(liquid.length > 0)
		{
			for(int i = 0; i < liquid.length; i++)
			{
				CraftTweakerAPI.apply(new AddLiquidAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), liquid[i].getName(), reversible, tickTime));
			}
		}
	}
	
	//Height
	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedHeightAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, int minHeight, int maxHeight, int tickTime) {
		CraftTweakerAPI.apply(new AddHeightAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), minHeight, maxHeight, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedHeightAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, int minHeight, int maxHeight, int tickTime) {
		CraftTweakerAPI.apply(new AddHeightAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), minHeight, maxHeight, tickTime));
	}

	//Entity
	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedNearbyEntityAgeing(String uniqueID, String gamestage, String entity, String entityData, String transformedEntity, String changedEntityData, String nearbyEntity, String nearbyEntityData, int radius, int tickTime) {
        CraftTweakerAPI.apply(new AddEntityBasedAgeingAction(uniqueID, gamestage, entity, createNBTTag(entityData), transformedEntity, createNBTTag(changedEntityData), nearbyEntity, createNBTTag(changedEntityData), radius, tickTime));
	}

	@Optional.Method(modid = "gamestages")
	@ZenMethod
	public static void addStagedNearbyEntityAgeing(String uniqueID, String gamestage, IEntityDefinition entity, String entityData, IEntityDefinition transformedEntity, String changedEntityData, IEntityDefinition nearbyEntity, String nearbyEntityData, int radius, int tickTime) {
		CraftTweakerAPI.apply(new AddEntityBasedAgeingAction(uniqueID, gamestage, entity.getId(), createNBTTag(entityData), transformedEntity.getId(), createNBTTag(changedEntityData), nearbyEntity.getId(), createNBTTag(changedEntityData), tickTime, tickTime));
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
