package com.svennieke.AgeingMobs.lists;

import java.util.ArrayList;

import com.svennieke.AgeingMobs.AgeingMobs;
import com.svennieke.AgeingMobs.config.AgeingMobsConfigGen;
import com.svennieke.AgeingMobs.endermite.EndermiteToShulkerAgeingInfo;
import com.svennieke.AgeingMobs.lists.info.BiomeBasedAgingInfo;
import com.svennieke.AgeingMobs.lists.info.BiomeTypeBasedAgingInfo;
import com.svennieke.AgeingMobs.lists.info.BlockBasedAgingInfo;
import com.svennieke.AgeingMobs.lists.info.BossAgingInfo;
import com.svennieke.AgeingMobs.lists.info.DimensionBasedAgingInfo;
import com.svennieke.AgeingMobs.lists.info.LightBasedAgingInfo;
import com.svennieke.AgeingMobs.lists.info.MagicBasedAgingInfo;
import com.svennieke.AgeingMobs.lists.info.MoonBasedAgingInfo;
import com.svennieke.AgeingMobs.lists.info.RegularAgingInfo;
import com.svennieke.AgeingMobs.lists.info.TimeBasedAgingInfo;
import com.svennieke.AgeingMobs.lists.info.WeatherBasedAgingInfo;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class AgeList {

	public static ArrayList<RegularAgingInfo> agingList = new ArrayList<>();
		
	public static void initializeAgeing() {
		if(AgeingMobsConfigGen.general.chargedcreeper.creeperAgeing)
			addWeatherBasedSecondOnlyAging("CreeperToCharged", "minecraft:creeper", "minecraft:creeper", createNBTTag("{powered:1b}"), "thunder", AgeingMobsConfigGen.general.chargedcreeper.creeperAgeingTime);
		
		if(AgeingMobsConfigGen.general.zombiehusk.zombieToHuskAgeing)
		{
			addBiomeTypeBasedSecondOnlyAging("ZombieToHusk", "minecraft:zombie", "minecraft:husk", createNBTTag("{IsBaby:0}"), BiomeDictionary.Type.HOT, AgeingMobsConfigGen.general.zombiehusk.zombieToHuskAgeingTime);
			addBiomeTypeBasedBothAging("BabyZombieToBabyHusk", "minecraft:zombie", createNBTTag("{IsBaby:1b}"), "minecraft:husk", createNBTTag("{IsBaby:1b}"), BiomeDictionary.Type.HOT, AgeingMobsConfigGen.general.zombiehusk.zombieToHuskAgeingTime);
		}
		
		if(AgeingMobsConfigGen.general.huskzombie.huskToZombieAgeing)
		{
			addBiomeTypeBasedSecondOnlyAging("HuskToZombie", "minecraft:husk", "minecraft:zombie", createNBTTag("{IsBaby:0}"), BiomeDictionary.Type.COLD, AgeingMobsConfigGen.general.huskzombie.huskToZombieAgeingTime);
			addBiomeTypeBasedBothAging("BabyHuskToBabyZombie", "minecraft:husk", createNBTTag("{IsBaby:1b}"), "minecraft:zombie", createNBTTag("{IsBaby:1b}"), BiomeDictionary.Type.COLD, AgeingMobsConfigGen.general.huskzombie.huskToZombieAgeingTime);
		}
		
		if(AgeingMobsConfigGen.general.villagervindicator.villagerToVindicatorAgeing)			
			addDarknessBasedAging("VillagerToVindicator", "minecraft:villager", "minecraft:vindicator", AgeingMobsConfigGen.general.villagervindicator.minimumLightLevel, AgeingMobsConfigGen.general.villagervindicator.maximumLightLevel, false, true, AgeingMobsConfigGen.general.villagervindicator.villagerToVindicatorAgeingTime);
		
		if(AgeingMobsConfigGen.general.vindicatorevoker.vindicatorToEvokerAgeing)			
			addMagicBasedAging("VindicatorToEvoker", "minecraft:vindicator", "minecraft:evoker", AgeingMobsConfigGen.general.vindicatorevoker.vindicatorToEvokerAgeingTime);
		
		if(AgeingMobsConfigGen.general.guardianelder.guardianToElderAgeing)
			addBossAging("GuardianToElder", "minecraft:guardian", "minecraft:elder_guardian", AgeingMobsConfigGen.general.guardianelder.guardianToElderAgeingMax, AgeingMobsConfigGen.general.guardianelder.guardianToElderRange, AgeingMobsConfigGen.general.guardianelder.guardianToElderAgeingTime);
	
		if(AgeingMobsConfigGen.general.babyzombie.babyToZombieAgeing)
		{
			addRegularBothAging("BabyToZombie", "minecraft:zombie", createNBTTag("{IsBaby:1b}"), "minecraft:zombie", createNBTTag("{IsBaby:0}"), AgeingMobsConfigGen.general.babyzombie.babyToZombieAgeingTime);
			addRegularBothAging("BabyToHusk", "minecraft:husk", createNBTTag("{IsBaby:1b}"), "minecraft:husk", createNBTTag("{IsBaby:0}"), AgeingMobsConfigGen.general.babyzombie.babyToZombieAgeingTime);
		}
		
		if(AgeingMobsConfigGen.general.endermiteshulker.endermiteToShulkerAgeing)
		{
			addEndermite();
		}
		
		if(AgeingMobsConfigGen.general.skeletonstray.skeletonToStrayAgeing)
			addBiomeTypeBasedAging("SkeletonToStray", "minecraft:skeleton", "minecraft:stray", BiomeDictionary.Type.COLD, AgeingMobsConfigGen.general.skeletonstray.skeletonToStrayAgeingTime);
		
		if(AgeingMobsConfigGen.general.strayskeleton.strayToSkeletonAgeing)
			addBiomeTypeBasedAging("StrayToSkeleton", "minecraft:stray", "minecraft:skeleton", BiomeDictionary.Type.HOT, AgeingMobsConfigGen.general.strayskeleton.strayToSkeletonAgeingTime);
		
		if(AgeingMobsConfigGen.general.rabbitkiller.rabbitToKillerAgeing)
			addDarknessBasedSecondOnlyAging("RabbitToKiller", "minecraft:rabbit", "minecraft:rabbit", createNBTTag("{RabbitType:99}"), AgeingMobsConfigGen.general.rabbitkiller.minimumLightLevel, AgeingMobsConfigGen.general.rabbitkiller.maximumLightLevel, true, false, AgeingMobsConfigGen.general.rabbitkiller.rabbitToKillerAgeingTime);
		
		if(AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeing)
		{
			addBlockBasedAging("CowToMooshroom", "minecraft:cow", "minecraft:mooshroom", Blocks.MYCELIUM.getDefaultState(), false, 0, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
			addBlockBasedAging("CowToMooshroom", "minecraft:cow", "minecraft:mooshroom", Blocks.BROWN_MUSHROOM.getDefaultState(), true, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingRadius, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
			addBlockBasedAging("CowToMooshroom", "minecraft:cow", "minecraft:mooshroom", Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState(), true, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingRadius, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
			addBlockBasedAging("CowToMooshroom", "minecraft:cow", "minecraft:mooshroom", Blocks.RED_MUSHROOM.getDefaultState(), true, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingRadius, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
			addBlockBasedAging("CowToMooshroom", "minecraft:cow", "minecraft:mooshroom", Blocks.RED_MUSHROOM_BLOCK.getDefaultState(), true, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingRadius, AgeingMobsConfigGen.general.cowmooshroom.cowToMooshroomAgeingTime);
		}
		
		if(AgeingMobsConfigGen.general.skeletonwitherskelly.skeletonToWitherSkeletonAgeing)
			addDimensionBasedAging("SkeletonToWitherSkelly", "minecraft:skeleton", "minecraft:wither_skeleton", -1, AgeingMobsConfigGen.general.skeletonwitherskelly.skeletonToWitherSkeletonAgeingTime);
	
		if(AgeingMobsConfigGen.general.slimemagma.slimeToMagmaCubeAgeing)
			addDimensionBasedAging("SlimeToMagmaCube", "minecraft:slime", "minecraft:magma_cube", -1, AgeingMobsConfigGen.general.slimemagma.slimeToMagmaCubeAgeingTime);

		if(AgeingMobsConfigGen.general.batvex.batToVexAgeing)
			addRegularAging("BatToVex", "minecraft:bat", "minecraft:vex", AgeingMobsConfigGen.general.batvex.batToVexAgeingTime);
	}
	
	//Regular
	public static void addRegularAging(String uniqueID, String entity, String transformedEntity, int tickTime)
	{
		// Check if the info doesn't already exist
		RegularAgingInfo regular_info = new RegularAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), tickTime);
		if(agingList.contains(regular_info))
			return;
		else
			agingList.add(regular_info);
	}
	
	public static void addRegularFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, int tickTime)
	{
		// Check if the info doesn't already exist
		RegularAgingInfo regular_info = new RegularAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), tickTime);
		if(agingList.contains(regular_info))
			return;
		else
			agingList.add(regular_info);
	}
	
	public static void addRegularSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, int tickTime)
	{
		// Check if the info doesn't already exist
		RegularAgingInfo regular_info = new RegularAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, tickTime);
		if(agingList.contains(regular_info))
			return;
		else
			agingList.add(regular_info);
	}
	
	public static void addRegularBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime)
	{
		// Check if the info doesn't already exist
		RegularAgingInfo regular_info = new RegularAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		if(agingList.contains(regular_info))
			return;
		else
			agingList.add(regular_info);
	}
	
	//Boss
	public static void addBossAging(String uniqueID, String entity, String transformedEntity, int maxInArea, int checkRadius, int tickTime)
	{
		// Check if the info doesn't already exist
		BossAgingInfo boss_info = new BossAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), maxInArea, checkRadius, tickTime);
		if(agingList.contains(boss_info))
			return;
		else
			agingList.add(boss_info);
	}
	
	public static void addBossFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, int maxInArea, int checkRadius, int tickTime)
	{
		// Check if the info doesn't already exist
		BossAgingInfo boss_info = new BossAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), maxInArea, checkRadius, tickTime);
		if(agingList.contains(boss_info))
			return;
		else
			agingList.add(boss_info);
	}
	
	public static void addBossSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, int maxInArea, int checkRadius, int tickTime)
	{
		// Check if the info doesn't already exist
		BossAgingInfo boss_info = new BossAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, maxInArea, checkRadius, tickTime);
		if(agingList.contains(boss_info))
			return;
		else
			agingList.add(boss_info);
	}
	
	public static void addBossBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int maxInArea, int checkRadius, int tickTime)
	{
		// Check if the info doesn't already exist
		BossAgingInfo boss_info = new BossAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, maxInArea, checkRadius, tickTime);
		if(agingList.contains(boss_info))
			return;
		else
			agingList.add(boss_info);
	}
	
	//Biome
	public static void addBiomeBasedAging(String uniqueID, String entity, String transformedEntity, Biome biome, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeBasedAgingInfo biomeBased_info = new BiomeBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), biome, tickTime);
		if(agingList.contains(biomeBased_info))
			return;
		else
			agingList.add(biomeBased_info);
	}
	
	public static void addBiomeBasedFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, Biome biome, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeBasedAgingInfo biomeBased_info = new BiomeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), biome, tickTime);
		if(agingList.contains(biomeBased_info))
			return;
		else
			agingList.add(biomeBased_info);
	}
	
	public static void addBiomeBasedSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, Biome biome, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeBasedAgingInfo biomeBased_info = new BiomeBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, biome, tickTime);
		if(agingList.contains(biomeBased_info))
			return;
		else
			agingList.add(biomeBased_info);
	}
	
	public static void addBiomeBasedBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, Biome biome, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeBasedAgingInfo biomeBased_info = new BiomeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, biome, tickTime);
		if(agingList.contains(biomeBased_info))
			return;
		else
			agingList.add(biomeBased_info);
	}
	
	//Biome Type
	public static void addBiomeTypeBasedAging(String uniqueID, String entity, String transformedEntity, BiomeDictionary.Type biomeType, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeTypeBasedAgingInfo biomeTypeBased_info = new BiomeTypeBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), biomeType, tickTime);
		if(agingList.contains(biomeTypeBased_info))
			return;
		else
			agingList.add(biomeTypeBased_info);
	}
	
	public static void addBiomeTypeBasedFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, BiomeDictionary.Type biomeType, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeTypeBasedAgingInfo biomeTypeBased_info = new BiomeTypeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), biomeType, tickTime);
		if(agingList.contains(biomeTypeBased_info))
			return;
		else
			agingList.add(biomeTypeBased_info);
	}
	
	public static void addBiomeTypeBasedSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, BiomeDictionary.Type biomeType, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeTypeBasedAgingInfo biomeTypeBased_info = new BiomeTypeBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, biomeType, tickTime);
		if(agingList.contains(biomeTypeBased_info))
			return;
		else
			agingList.add(biomeTypeBased_info);
	}
	
	public static void addBiomeTypeBasedBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, BiomeDictionary.Type biomeType, int tickTime)
	{
		// Check if the info doesn't already exist
		BiomeTypeBasedAgingInfo biomeTypeBased_info = new BiomeTypeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, biomeType, tickTime);
		if(agingList.contains(biomeTypeBased_info))
			return;
		else
			agingList.add(biomeTypeBased_info);
	}
	
	public static void addWeatherBasedAging(String uniqueID, String entity, String transformedEntity, String weather, int tickTime)
	{
		// Check if the info doesn't already exist
		WeatherBasedAgingInfo weatherBased_info = new WeatherBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), weather, tickTime);
		if(agingList.contains(weatherBased_info))
			return;
		else
			agingList.add(weatherBased_info);
	}
	
	//Weather
	public static void addWeatherBasedFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, String weather, int tickTime)
	{
		// Check if the info doesn't already exist
		WeatherBasedAgingInfo weatherBased_info = new WeatherBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), weather, tickTime);
		if(agingList.contains(weatherBased_info))
			return;
		else
			agingList.add(weatherBased_info);
	}
	
	public static void addWeatherBasedSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, String weather, int tickTime)
	{
		// Check if the info doesn't already exist
		WeatherBasedAgingInfo weatherBased_info = new WeatherBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, weather, tickTime);
		if(agingList.contains(weatherBased_info))
			return;
		else
			agingList.add(weatherBased_info);
	}
	
	public static void addWeatherBasedBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String weather, int tickTime)
	{
		// Check if the info doesn't already exist
		WeatherBasedAgingInfo weatherBased_info = new WeatherBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, weather, tickTime);
		if(agingList.contains(weatherBased_info))
			return;
		else
			agingList.add(weatherBased_info);
	}
	
	//Time
	public static void addTimeBasedAging(String uniqueID, String entity, String transformedEntity, int minTime, int maxTime, int tickTime)
	{
		// Check if the info doesn't already exist
		TimeBasedAgingInfo timeBased_info = new TimeBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), minTime, maxTime, tickTime);
		if(agingList.contains(timeBased_info))
			return;
		else
			agingList.add(timeBased_info);
	}
	
	public static void addTimeBasedFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, int minTime, int maxTime, int tickTime)
	{
		// Check if the info doesn't already exist
		TimeBasedAgingInfo timeBased_info = new TimeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), minTime, maxTime, tickTime);
		if(agingList.contains(timeBased_info))
			return;
		else
			agingList.add(timeBased_info);
	}
	
	public static void addTimeBasedSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, int minTime, int maxTime, int tickTime)
	{
		// Check if the info doesn't already exist
		TimeBasedAgingInfo timeBased_info = new TimeBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, minTime, maxTime, tickTime);
		if(agingList.contains(timeBased_info))
			return;
		else
			agingList.add(timeBased_info);
	}
	
	public static void addTimeBasedBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int minTime, int maxTime, int tickTime)
	{
		// Check if the info doesn't already exist
		TimeBasedAgingInfo timeBased_info = new TimeBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, minTime, maxTime, tickTime);
		if(agingList.contains(timeBased_info))
			return;
		else
			agingList.add(timeBased_info);
	}
	
	//Moon
	public static void addMoonBasedAging(String uniqueID, String entity, String transformedEntity, String moonPhase, int tickTime)
	{
		// Check if the info doesn't already exist
		MoonBasedAgingInfo moonBased_info = new MoonBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), moonPhase, tickTime);
		if(agingList.contains(moonBased_info))
			return;
		else
			agingList.add(moonBased_info);
	}
	
	public static void addMoonBasedFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, String moonPhase, int tickTime)
	{
		// Check if the info doesn't already exist
		MoonBasedAgingInfo moonBased_info = new MoonBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), moonPhase, tickTime);
		if(agingList.contains(moonBased_info))
			return;
		else
			agingList.add(moonBased_info);
	}
	
	public static void addMoonBasedSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, String moonPhase, int tickTime)
	{
		// Check if the info doesn't already exist
		MoonBasedAgingInfo moonBased_info = new MoonBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, moonPhase, tickTime);
		if(agingList.contains(moonBased_info))
			return;
		else
			agingList.add(moonBased_info);
	}
	
	public static void addMoonBasedBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String moonPhase, int tickTime)
	{
		// Check if the info doesn't already exist
		MoonBasedAgingInfo moonBased_info = new MoonBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, moonPhase, tickTime);
		if(agingList.contains(moonBased_info))
			return;
		else
			agingList.add(moonBased_info);
	}
	
	//Magic
	public static void addMagicBasedAging(String uniqueID, String entity, String transformedEntity, int tickTime)
	{
		// Check if the info doesn't already exist
		MagicBasedAgingInfo magicBased_info = new MagicBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), tickTime);
		if(agingList.contains(magicBased_info))
			return;
		else
			agingList.add(magicBased_info);
	}
	
	public static void addMagicBasedFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, int tickTime)
	{
		// Check if the info doesn't already exist
		MagicBasedAgingInfo magicBased_info = new MagicBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), tickTime);
		if(agingList.contains(magicBased_info))
			return;
		else
			agingList.add(magicBased_info);
	}
	
	public static void addMagicBasedSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, int tickTime)
	{
		// Check if the info doesn't already exist
		MagicBasedAgingInfo magicBased_info = new MagicBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, tickTime);
		if(agingList.contains(magicBased_info))
			return;
		else
			agingList.add(magicBased_info);
	}
	
	public static void addMagicBasedBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime)
	{
		// Check if the info doesn't already exist
		MagicBasedAgingInfo magicBased_info = new MagicBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		if(agingList.contains(magicBased_info))
			return;
		else
			agingList.add(magicBased_info);
	}
	
	//Darkness
	public static void addDarknessBasedAging(String uniqueID, String entity, String transformedEntity, int lightLevelMin, int lightLevelMax, boolean alone, boolean reversable, int tickTime)
	{
		// Check if the info doesn't already exist
		LightBasedAgingInfo darknessBased_info = new LightBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), lightLevelMin, lightLevelMax, alone, reversable, tickTime);
		if(agingList.contains(darknessBased_info))
			return;
		else
			agingList.add(darknessBased_info);
	}
	
	public static void addDarknessBasedFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, int lightLevelMin, int lightLevelMax, boolean alone, boolean reversable, int tickTime)
	{
		// Check if the info doesn't already exist
		LightBasedAgingInfo darknessBased_info = new LightBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), lightLevelMin, lightLevelMax, alone, reversable, tickTime);
		if(agingList.contains(darknessBased_info))
			return;
		else
			agingList.add(darknessBased_info);
	}
	
	public static void addDarknessBasedSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, int lightLevelMin, int lightLevelMax, boolean alone, boolean reversable, int tickTime)
	{
		// Check if the info doesn't already exist
		LightBasedAgingInfo darknessBased_info = new LightBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, lightLevelMin, lightLevelMax, alone, reversable, tickTime);
		if(agingList.contains(darknessBased_info))
			return;
		else
			agingList.add(darknessBased_info);
	}
	
	public static void addDarknessBasedBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int lightLevelMin, int lightLevelMax, boolean alone, boolean reversable, int tickTime)
	{
		// Check if the info doesn't already exist
		LightBasedAgingInfo darknessBased_info = new LightBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, lightLevelMin, lightLevelMax, alone, reversable, tickTime);
		if(agingList.contains(darknessBased_info))
			return;
		else
			agingList.add(darknessBased_info);
	}
	
	//Dimension
	public static void addDimensionBasedAging(String uniqueID, String entity, String transformedEntity, int dimension, int tickTime)
	{
		// Check if the info doesn't already exist
		DimensionBasedAgingInfo dimensionBased_info = new DimensionBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), dimension, tickTime);
		if(agingList.contains(dimensionBased_info))
			return;
		else
			agingList.add(dimensionBased_info);
	}
	
	public static void addDimensionBasedFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, int dimension, int tickTime)
	{
		// Check if the info doesn't already exist
		DimensionBasedAgingInfo dimensionBased_info = new DimensionBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), dimension, tickTime);
		if(agingList.contains(dimensionBased_info))
			return;
		else
			agingList.add(dimensionBased_info);
	}
	
	public static void addDimensionBasedSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, int dimension, int tickTime)
	{
		// Check if the info doesn't already exist
		DimensionBasedAgingInfo dimensionBased_info = new DimensionBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, dimension, tickTime);
		if(agingList.contains(dimensionBased_info))
			return;
		else
			agingList.add(dimensionBased_info);
	}
	
	public static void addDimensionBasedBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int dimension, int tickTime)
	{
		// Check if the info doesn't already exist
		DimensionBasedAgingInfo dimensionBased_info = new DimensionBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, dimension, tickTime);
		if(agingList.contains(dimensionBased_info))
			return;
		else
			agingList.add(dimensionBased_info);
	}
	
	//Block
	public static void addBlockBasedAging(String uniqueID, String entity, String transformedEntity, IBlockState state, boolean nearby, int radius, int tickTime)
	{
		// Check if the info doesn't already exist
		BlockBasedAgingInfo blockBased_info = new BlockBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), state, nearby, radius, tickTime);
		if(agingList.contains(blockBased_info))
			return;
		else
			agingList.add(blockBased_info);
	}
	
	public static void addBlockBasedFirstOnlyAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, IBlockState state, boolean nearby, int radius, int tickTime)
	{
		// Check if the info doesn't already exist
		BlockBasedAgingInfo blockBased_info = new BlockBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, createNBTTag(""), state, nearby, radius, tickTime);
		if(agingList.contains(blockBased_info))
			return;
		else
			agingList.add(blockBased_info);
	}
	
	public static void addBlockBasedSecondOnlyAging(String uniqueID, String entity, String transformedEntity, NBTTagCompound changedEntityData, IBlockState state, boolean nearby, int radius, int tickTime)
	{
		// Check if the info doesn't already exist
		BlockBasedAgingInfo blockBased_info = new BlockBasedAgingInfo(uniqueID, entity, createNBTTag(""), transformedEntity, changedEntityData, state, nearby, radius, tickTime);
		if(agingList.contains(blockBased_info))
			return;
		else
			agingList.add(blockBased_info);
	}
	
	public static void addBlockBasedBothAging(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, IBlockState state, boolean nearby, int radius, int tickTime)
	{
		// Check if the info doesn't already exist
		BlockBasedAgingInfo blockBased_info = new BlockBasedAgingInfo(uniqueID, entity, entityData, transformedEntity, changedEntityData, state, nearby, radius, tickTime);
		if(agingList.contains(blockBased_info))
			return;
		else
			agingList.add(blockBased_info);
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
