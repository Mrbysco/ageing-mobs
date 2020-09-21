package com.shynieke.ageingmobs.lists;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.config.AgeingMobsConfigGen;
import com.shynieke.ageingmobs.endermite.EndermiteToShulkerAgeingInfo;
import com.shynieke.ageingmobs.lists.info.BiomeBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.BiomeTypeBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.BlockBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.BossAgingInfo;
import com.shynieke.ageingmobs.lists.info.DimensionBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.EntityBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.HeightBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.LightBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.LiquidBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.MagicBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.MoonBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.RegularAgingInfo;
import com.shynieke.ageingmobs.lists.info.TimeBasedAgingInfo;
import com.shynieke.ageingmobs.lists.info.WeatherBasedAgingInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.Optional;

import java.util.ArrayList;

public class AgeList {

	public static ArrayList<RegularAgingInfo> agingList = new ArrayList<>();
		
	public static void initializeAgeing() {
		if(AgeingMobsConfigGen.general.chargedcreeper.creeperAgeing)
			addWeatherBasedAgeing("CreeperToCharged", "minecraft:creeper", createNBTTag(""), "minecraft:creeper", createNBTTag("{powered:1b}"), "thunder", AgeingMobsConfigGen.general.chargedcreeper.creeperAgeingTime);
		
		if(AgeingMobsConfigGen.general.zombiehusk.zombieToHuskAgeing)
		{
			addBiomeTypeBasedAgeing("ZombieToHusk", "minecraft:zombie", createNBTTag(""), "minecraft:husk", createNBTTag("{IsBaby:0}"), BiomeDictionary.Type.HOT, AgeingMobsConfigGen.general.zombiehusk.zombieToHuskAgeingTime);
			addBiomeTypeBasedAgeing("BabyZombieToBabyHusk", "minecraft:zombie", createNBTTag("{IsBaby:1b}"), "minecraft:husk", createNBTTag("{IsBaby:1b}"), BiomeDictionary.Type.HOT, AgeingMobsConfigGen.general.zombiehusk.zombieToHuskAgeingTime);
		}
		
		if(AgeingMobsConfigGen.general.huskzombie.huskToZombieAgeing)
		{
			addBiomeTypeBasedAgeing("HuskToZombie", "minecraft:husk", createNBTTag(""), "minecraft:zombie", createNBTTag("{IsBaby:0}"), BiomeDictionary.Type.COLD, AgeingMobsConfigGen.general.huskzombie.huskToZombieAgeingTime);
			addBiomeTypeBasedAgeing("BabyHuskToBabyZombie", "minecraft:husk", createNBTTag("{IsBaby:1b}"), "minecraft:zombie", createNBTTag("{IsBaby:1b}"), BiomeDictionary.Type.COLD, AgeingMobsConfigGen.general.huskzombie.huskToZombieAgeingTime);
		}
		
		if(AgeingMobsConfigGen.general.villagervindicator.villagerToVindicatorAgeing)		
			addDarknessBasedAgeing("VillagerToVindicator", "minecraft:villager", createNBTTag(""), "minecraft:vindication_illager", createNBTTag(""), AgeingMobsConfigGen.general.villagervindicator.minimumLightLevel, AgeingMobsConfigGen.general.villagervindicator.maximumLightLevel, false, true, AgeingMobsConfigGen.general.villagervindicator.villagerToVindicatorAgeingTime);
		
		if(AgeingMobsConfigGen.general.vindicatorevoker.vindicatorToEvokerAgeing)			
			addMagicBasedAgeing("VindicatorToEvoker", "minecraft:vindication_illager", createNBTTag(""), "minecraft:evocation_illager", createNBTTag(""), AgeingMobsConfigGen.general.vindicatorevoker.vindicatorToEvokerAgeingTime);
		
		if(AgeingMobsConfigGen.general.guardianelder.guardianToElderAgeing)
			addBossAgeing("GuardianToElder", "minecraft:guardian", createNBTTag(""), "minecraft:elder_guardian", createNBTTag(""), AgeingMobsConfigGen.general.guardianelder.guardianToElderAgeingMax, AgeingMobsConfigGen.general.guardianelder.guardianToElderRange, AgeingMobsConfigGen.general.guardianelder.guardianToElderAgeingTime);
	
		if(AgeingMobsConfigGen.general.babyzombie.babyToZombieAgeing)
		{
			addRegularAgeing("BabyToZombie", "minecraft:zombie", createNBTTag("{IsBaby:1b}"), "minecraft:zombie", createNBTTag("{IsBaby:0}"), AgeingMobsConfigGen.general.babyzombie.babyToZombieAgeingTime);
			addRegularAgeing("BabyToHusk", "minecraft:husk", createNBTTag("{IsBaby:1b}"), "minecraft:husk", createNBTTag("{IsBaby:0}"), AgeingMobsConfigGen.general.babyzombie.babyToZombieAgeingTime);
		}
		
		if(AgeingMobsConfigGen.general.endermiteshulker.endermiteToShulkerAgeing)
		{
			addEndermite();
		}
		
		if(AgeingMobsConfigGen.general.skeletonstray.skeletonToStrayAgeing)
			addBiomeTypeBasedAgeing("SkeletonToStray", "minecraft:skeleton", createNBTTag(""), "minecraft:stray", createNBTTag(""), BiomeDictionary.Type.COLD, AgeingMobsConfigGen.general.skeletonstray.skeletonToStrayAgeingTime);
		
		if(AgeingMobsConfigGen.general.strayskeleton.strayToSkeletonAgeing)
			addBiomeTypeBasedAgeing("StrayToSkeleton", "minecraft:stray", createNBTTag(""), "minecraft:skeleton", createNBTTag(""), BiomeDictionary.Type.HOT, AgeingMobsConfigGen.general.strayskeleton.strayToSkeletonAgeingTime);
		
		if(AgeingMobsConfigGen.general.rabbitkiller.rabbitToKillerAgeing)
			addDarknessBasedAgeing("RabbitToKiller", "minecraft:rabbit", createNBTTag(""), "minecraft:rabbit", createNBTTag("{RabbitType:99}"), AgeingMobsConfigGen.general.rabbitkiller.minimumLightLevel, AgeingMobsConfigGen.general.rabbitkiller.maximumLightLevel, true, false, AgeingMobsConfigGen.general.rabbitkiller.rabbitToKillerAgeingTime);
		
		if(AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeing)
		{
			addBlockBasedAgeing("CowToMooshroom", "minecraft:cow", createNBTTag(""), "minecraft:mooshroom", createNBTTag(""), Blocks.MYCELIUM.getDefaultState(), false, 0, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
			addBlockBasedAgeing("CowToMooshroom", "minecraft:cow", createNBTTag(""), "minecraft:mooshroom", createNBTTag(""), Blocks.BROWN_MUSHROOM.getDefaultState(), true, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingRadius, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
			addBlockBasedAgeing("CowToMooshroom", "minecraft:cow", createNBTTag(""), "minecraft:mooshroom", createNBTTag(""), Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState(), true, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingRadius, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
			addBlockBasedAgeing("CowToMooshroom", "minecraft:cow", createNBTTag(""), "minecraft:mooshroom", createNBTTag(""), Blocks.RED_MUSHROOM.getDefaultState(), true, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingRadius, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
			addBlockBasedAgeing("CowToMooshroom", "minecraft:cow", createNBTTag(""), "minecraft:mooshroom", createNBTTag(""), Blocks.RED_MUSHROOM_BLOCK.getDefaultState(), true, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingRadius, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
		}
		
		if(AgeingMobsConfigGen.general.skeletonwitherskelly.skeletonToWitherSkeletonAgeing)
			addDimensionBasedAgeing("SkeletonToWitherSkelly", "minecraft:skeleton", createNBTTag(""), "minecraft:wither_skeleton", createNBTTag(""), -1, AgeingMobsConfigGen.general.skeletonwitherskelly.skeletonToWitherSkeletonAgeingTime);
	
		if(AgeingMobsConfigGen.general.slimemagma.slimeToMagmaCubeAgeing)
			addDimensionBasedAgeing("SlimeToMagmaCube", "minecraft:slime", createNBTTag(""), "minecraft:magma_cube", createNBTTag(""), -1, AgeingMobsConfigGen.general.slimemagma.slimeToMagmaCubeAgeingTime);

		if(AgeingMobsConfigGen.general.batvex.batToVexAgeing)
			addRegularAgeing("BatToVex", "minecraft:bat", createNBTTag(""), "minecraft:vex", createNBTTag(""), AgeingMobsConfigGen.general.batvex.batToVexAgeingTime);
	}
	
	//Regular
	public static void addRegularAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime)
	{
		// Check if the info doesn't already exist
		RegularAgingInfo regular_info = new RegularAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		if(agingList.contains(regular_info))
			return;
		else
			agingList.add(regular_info);
	}
	
	//Boss
	public static void addBossAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int maxInArea, int checkRadius, int tickTime)
	{
		// Check if the info doesn't already exist
		BossAgingInfo boss_info = new BossAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, maxInArea, checkRadius, tickTime);
		if(agingList.contains(boss_info))
			return;
		else
			agingList.add(boss_info);
	}
	
	//Biome
	public static void addBiomeBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, Biome biome, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeBasedAgingInfo biomeBased_info = new BiomeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, biome, tickTime);
		if(agingList.contains(biomeBased_info))
			return;
		else
			agingList.add(biomeBased_info);
	}
	
	//Biome Type
	public static void addBiomeTypeBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, BiomeDictionary.Type biomeType, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeTypeBasedAgingInfo biomeTypeBased_info = new BiomeTypeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, biomeType, tickTime);
		if(agingList.contains(biomeTypeBased_info))
			return;
		else
			agingList.add(biomeTypeBased_info);
	}

	//Weather
	public static void addWeatherBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String weather, int tickTime)
	{
		// Check if the info doesn't already exist
		WeatherBasedAgingInfo weatherBased_info = new WeatherBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, weather, tickTime);
		if(agingList.contains(weatherBased_info))
			return;
		else
			agingList.add(weatherBased_info);
	}
	
	//Time
	public static void addTimeBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minTime, int maxTime, int tickTime)
	{
		// Check if the info doesn't already exist
		TimeBasedAgingInfo heightBased_info = new TimeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, minTime, maxTime, tickTime);
		if(agingList.contains(heightBased_info))
			return;
		else
			agingList.add(heightBased_info);
	}
	
	//Moon
	public static void addMoonBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String moonPhase, int tickTime)
	{
		// Check if the info doesn't already exist
		MoonBasedAgingInfo moonBased_info = new MoonBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, moonPhase, tickTime);
		if(agingList.contains(moonBased_info))
			return;
		else
			agingList.add(moonBased_info);
	}
	
	//Magic
	public static void addMagicBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime)
	{
		// Check if the info doesn't already exist
		MagicBasedAgingInfo magicBased_info = new MagicBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		if(agingList.contains(magicBased_info))
			return;
		else
			agingList.add(magicBased_info);
	}
	
	//Darkness
	public static void addDarknessBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int lightLevelMin, int lightLevelMax, boolean alone, boolean reversable, int tickTime)
	{
		// Check if the info doesn't already exist
		LightBasedAgingInfo darknessBased_info = new LightBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, lightLevelMin, lightLevelMax, alone, reversable, tickTime);
		if(agingList.contains(darknessBased_info))
			return;
		else
			agingList.add(darknessBased_info);
	}
	
	//Dimension
	public static void addDimensionBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int dimension, int tickTime)
	{
		// Check if the info doesn't already exist
		DimensionBasedAgingInfo dimensionBased_info = new DimensionBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, dimension, tickTime);
		if(agingList.contains(dimensionBased_info))
			return;
		else
			agingList.add(dimensionBased_info);
	}
	
	//Block
	public static void addBlockBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, IBlockState state, boolean nearby, int radius, int tickTime)
	{
		// Check if the info doesn't already exist
		BlockBasedAgingInfo blockBased_info = new BlockBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, state, nearby, radius, tickTime);
		if(agingList.contains(blockBased_info))
			return;
		else
			agingList.add(blockBased_info);
	}
	
	//Liquid
	public static void addLiquidBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String liquid, boolean reversible, int tickTime)
	{
		// Check if the info doesn't already exist
		LiquidBasedAgingInfo liquidBased_info = new LiquidBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, liquid, reversible, tickTime);
		if(agingList.contains(liquidBased_info))
			return;
		else
			agingList.add(liquidBased_info);
	}
	
	//Height
	public static void addHeightBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minHeight, int maxHeight, int tickHeight)
	{
		// Check if the info doesn't already exist
		HeightBasedAgingInfo heightBased_info = new HeightBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, minHeight, maxHeight, tickHeight);
		if(agingList.contains(heightBased_info))
			return;
		else
			agingList.add(heightBased_info);
	}
	
	//Entity
	public static void addEntityBasedAgeing(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String entityNearby, NBTTagCompound entityNearbyData, int radius, int tickTime)
	{
		// Check if the info doesn't already exist
		EntityBasedAgingInfo entityBased_info = new EntityBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, entityNearby, entityNearbyData, radius, tickTime);
		if(agingList.contains(entityBased_info))
			return;
		else
			agingList.add(entityBased_info);
	}
	
	public static void addEndermite()
	{
		// Check if the info doesn't already exist
		EndermiteToShulkerAgeingInfo endermite_info = new EndermiteToShulkerAgeingInfo("EndermiteToShulker", "minecraft:endermite", "minecraft:shulker", AgeingMobsConfigGen.general.endermiteshulker.endermiteToShulkerAgeingTime);
		if(agingList.contains(endermite_info))
			return;
		else
			agingList.add(endermite_info);
	}
	
	//GAMESTAGES
	//Regular
	@Optional.Method(modid = "gamestages")
	public static void addStagedRegularAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime)
	{
		// Check if the info doesn't already exist
		RegularAgingInfo regular_info = new RegularAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		regular_info.setGameStage(gamestage);
		if(agingList.contains(regular_info))
			return;
		else
			agingList.add(regular_info);
	}
	
	//Boss
	@Optional.Method(modid = "gamestages")
	public static void addStagedBossAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int maxInArea, int checkRadius, int tickTime)
	{
		// Check if the info doesn't already exist
		BossAgingInfo boss_info = new BossAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, maxInArea, checkRadius, tickTime);
		boss_info.setGameStage(gamestage);
		if(agingList.contains(boss_info))
			return;
		else
			agingList.add(boss_info);
	}
	
	//Biome
	@Optional.Method(modid = "gamestages")
	public static void addStagedBiomeBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, Biome biome, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeBasedAgingInfo biomeBased_info = new BiomeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, biome, tickTime);
		biomeBased_info.setGameStage(gamestage);
		if(agingList.contains(biomeBased_info))
			return;
		else
			agingList.add(biomeBased_info);
	}
	
	//Biome Type
	@Optional.Method(modid = "gamestages")
	public static void addStagedBiomeTypeBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, BiomeDictionary.Type biomeType, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeTypeBasedAgingInfo biomeTypeBased_info = new BiomeTypeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, biomeType, tickTime);
		biomeTypeBased_info.setGameStage(gamestage);
		if(agingList.contains(biomeTypeBased_info))
			return;
		else
			agingList.add(biomeTypeBased_info);
	}

	//Weather
	@Optional.Method(modid = "gamestages")
	public static void addStagedWeatherBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String weather, int tickTime)
	{
		// Check if the info doesn't already exist
		WeatherBasedAgingInfo weatherBased_info = new WeatherBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, weather, tickTime);
		weatherBased_info.setGameStage(gamestage);
		if(agingList.contains(weatherBased_info))
			return;
		else
			agingList.add(weatherBased_info);
	}
	
	//Time
	@Optional.Method(modid = "gamestages")
	public static void addStagedTimeBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minTime, int maxTime, int tickTime)
	{
		// Check if the info doesn't already exist
		TimeBasedAgingInfo heightBased_info = new TimeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, minTime, maxTime, tickTime);
		heightBased_info.setGameStage(gamestage);
		if(agingList.contains(heightBased_info))
			return;
		else
			agingList.add(heightBased_info);
	}
	
	//Moon
	@Optional.Method(modid = "gamestages")
	public static void addStagedMoonBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String moonPhase, int tickTime)
	{
		// Check if the info doesn't already exist
		MoonBasedAgingInfo moonBased_info = new MoonBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, moonPhase, tickTime);
		moonBased_info.setGameStage(gamestage);
		if(agingList.contains(moonBased_info))
			return;
		else
			agingList.add(moonBased_info);
	}
	
	//Magic
	@Optional.Method(modid = "gamestages")
	public static void addStagedMagicBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime)
	{
		// Check if the info doesn't already exist
		MagicBasedAgingInfo magicBased_info = new MagicBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		magicBased_info.setGameStage(gamestage);
		if(agingList.contains(magicBased_info))
			return;
		else
			agingList.add(magicBased_info);
	}
	
	//Darkness
	@Optional.Method(modid = "gamestages")
	public static void addStagedDarknessBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int lightLevelMin, int lightLevelMax, boolean alone, boolean reversable, int tickTime)
	{
		// Check if the info doesn't already exist
		LightBasedAgingInfo darknessBased_info = new LightBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, lightLevelMin, lightLevelMax, alone, reversable, tickTime);
		darknessBased_info.setGameStage(gamestage);
		if(agingList.contains(darknessBased_info))
			return;
		else
			agingList.add(darknessBased_info);
	}
	
	//Dimension
	@Optional.Method(modid = "gamestages")
	public static void addStagedDimensionBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int dimension, int tickTime)
	{
		// Check if the info doesn't already exist
		DimensionBasedAgingInfo dimensionBased_info = new DimensionBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, dimension, tickTime);
		dimensionBased_info.setGameStage(gamestage);
		if(agingList.contains(dimensionBased_info))
			return;
		else
			agingList.add(dimensionBased_info);
	}
	
	//Block
	@Optional.Method(modid = "gamestages")
	public static void addStagedBlockBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, IBlockState state, boolean nearby, int radius, int tickTime)
	{
		// Check if the info doesn't already exist
		BlockBasedAgingInfo blockBased_info = new BlockBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, state, nearby, radius, tickTime);
		blockBased_info.setGameStage(gamestage);
		if(agingList.contains(blockBased_info))
			return;
		else
			agingList.add(blockBased_info);
	}
	
	//Liquid
	@Optional.Method(modid = "gamestages")
	public static void addStagedLiquidBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String liquid, boolean reversible, int tickTime)
	{
		// Check if the info doesn't already exist
		LiquidBasedAgingInfo liquidBased_info = new LiquidBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, liquid, reversible, tickTime);
		liquidBased_info.setGameStage(gamestage);
		if(agingList.contains(liquidBased_info))
			return;
		else
			agingList.add(liquidBased_info);
	}
	
	//Height
	@Optional.Method(modid = "gamestages")
	public static void addStagedHeightBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minHeight, int maxHeight, int tickTime)
	{
		// Check if the info doesn't already exist
		HeightBasedAgingInfo heightBased_info = new HeightBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, minHeight, maxHeight, tickTime);
		heightBased_info.setGameStage(gamestage);
		if(agingList.contains(heightBased_info))
			return;
		else
			agingList.add(heightBased_info);
	}
	
	//Entity
	@Optional.Method(modid = "gamestages")
	public static void addStagedEntityBasedAgeing(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String entityNearby, NBTTagCompound entityNearbyData, int radius, int tickTime)
	{
		// Check if the info doesn't already exist
		EntityBasedAgingInfo entityBased_info = new EntityBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, entityNearby, entityNearbyData, radius, tickTime);
		entityBased_info.setGameStage(gamestage);
		if(agingList.contains(entityBased_info))
			return;
		else
			agingList.add(entityBased_info);
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
