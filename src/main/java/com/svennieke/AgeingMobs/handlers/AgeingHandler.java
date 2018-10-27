package com.svennieke.AgeingMobs.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.svennieke.AgeingMobs.AgeingMobs;
import com.svennieke.AgeingMobs.Reference;
import com.svennieke.AgeingMobs.config.AgeingMobsConfigGen;
import com.svennieke.AgeingMobs.endermite.AIHideInPurpur;
import com.svennieke.AgeingMobs.endermite.EndermiteToShulkerAgeingInfo;
import com.svennieke.AgeingMobs.lists.AgeList;
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
import com.svennieke.AgeingMobs.util.AgeingHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AgeingHandler {
	private int tickCounter = 0;
	
	@SubscribeEvent
	public void AgeHandler(TickEvent.WorldTickEvent event) 
	{
		if (event.phase.equals(TickEvent.Phase.END) && event.side.isServer())
		{
			World world = event.world;
			
			if (tickCounter >= 20)
			{
				tickCounter = 0;
				if(AgeList.agingList != null && !AgeList.agingList.isEmpty())
				{
					for(RegularAgingInfo info : AgeList.agingList)
					{	
						if(world.loadedEntityList != null && !world.loadedEntityList.isEmpty() && !world.isRemote)
						{
							ArrayList<Entity> loadedEntityList = new ArrayList<>(world.loadedEntityList);
							for(Entity entity : loadedEntityList)
							{
								if(!(entity instanceof EntityPlayer) && !(entity instanceof FakePlayer))
								{
									if(EntityList.isMatchingName(entity, AgeingHelper.getEntityLocation(info.getEntity())))
									{
										if(info.getEntity().equals(info.getEvolvedEntity()))
										{
											if(!info.getChangedEntityData().hasNoTags())
											{
												CheckList(info, entity, world);
											}
											else
											{
												AgeingMobs.logger.error("An error has occured. A mob can not transform into itself. See id: %s", new Object[] {info.getUniqueID()});
												if(AgeList.agingList.contains(info))
												{
													AgeList.agingList.remove(info);
												}
											}
										}
										else
										{
											CheckList(info, entity, world);
										}
									}
								}
							}
						}
					}
				}
			}
			
			tickCounter++;
		}
	}
	
	public void CheckList(RegularAgingInfo info, Entity entity, World world)
	{
		if(info.getEntity().equals(info.getEvolvedEntity()))
		{
			if(info.getEntityData().equals(info.getChangedEntityData()))
			{
				AgeingMobs.logger.error("Aged Entity nbt identical to the original: %s", new Object[] {info.getUniqueID()});
			}
			else
			{
				if(info.getEntityData().hasNoTags())
				{
					if(!info.getChangedEntityData().hasNoTags())
					{
						NBTTagCompound entityTag = entityToNBT(entity);
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.hasNoTags())
						{
							if(!NBTUtil.areNBTEquals(entityTag2, entityTag, true))
							{
								ExecuteAppropiateMethod(info, entity, world);
							}
						}
					}
					else
					{
						AgeingMobs.logger.error("Aged Entity identical to the original: %s", new Object[] {info.getUniqueID()});
					}
				}
				else
				{
					if(!info.getChangedEntityData().hasNoTags())
					{
						NBTTagCompound entityTag = entityToNBT(entity);
						NBTTagCompound entityTag2 = info.getEntityData();
						NBTTagCompound entityTag3 = info.getChangedEntityData();

						if(!entityTag2.hasNoTags() && !entityTag3.hasNoTags())
						{
							if(NBTUtil.areNBTEquals(entityTag2, entityTag, true) && !NBTUtil.areNBTEquals(entityTag3, entityTag, true))
							{
								ExecuteAppropiateMethod(info, entity, world);
							}
						}
					}
					else
					{
						NBTTagCompound entityTag = entityToNBT(entity);
						NBTTagCompound entityTag2 = info.getEntityData();

						if(!entityTag2.hasNoTags())
						{							
							if(NBTUtil.areNBTEquals(entityTag2, entityTag, true))
							{
								ExecuteAppropiateMethod(info, entity, world);
							}
						}
					}
				}
			}
		}
		else
		{
			if(!info.getEntityData().hasNoTags())
			{
				NBTTagCompound entityTag = entityToNBT(entity);
				NBTTagCompound entityTag2 = info.getEntityData();
				NBTTagCompound entityTag3 = info.getChangedEntityData();

				if(!entityTag2.hasNoTags() && !entityTag3.hasNoTags())
				{
					if(NBTUtil.areNBTEquals(entityTag2, entityTag, true) && !NBTUtil.areNBTEquals(entityTag3, entityTag, true))
					{
						ExecuteAppropiateMethod(info, entity, world);
					}
				}
			}
			else
			{
				ExecuteAppropiateMethod(info, entity, world);
			}
		}
	}
	
	public void ExecuteAppropiateMethod(RegularAgingInfo info, Entity entity, World world)
	{
		if(info instanceof BiomeBasedAgingInfo)
		{
			BiomeBasedAgingInfo biomeInfo = (BiomeBasedAgingInfo)info;
			biomeChecks(biomeInfo, entity, world);
		}
		else if(info instanceof BiomeTypeBasedAgingInfo)
		{
			BiomeTypeBasedAgingInfo biomeTypeInfo = (BiomeTypeBasedAgingInfo)info;
			biomeTypeChecks(biomeTypeInfo, entity, world);
		}
		else if(info instanceof BlockBasedAgingInfo)
		{
			BlockBasedAgingInfo blockInfo = (BlockBasedAgingInfo)info;
			blockChecks(blockInfo, entity, world);
		}
		else if(info instanceof BossAgingInfo)
		{
			BossAgingInfo bossInfo = (BossAgingInfo)info;
			bossChecks(bossInfo, entity, world);
		}
		else if(info instanceof DimensionBasedAgingInfo)
		{
			DimensionBasedAgingInfo dimensionInfo = (DimensionBasedAgingInfo)info;
			dimensionChecks(dimensionInfo, entity, world);
		}
		else if(info instanceof EndermiteToShulkerAgeingInfo)
		{
			EndermiteToShulkerAgeingInfo endermiteInfo = (EndermiteToShulkerAgeingInfo)info;
			if(entity instanceof EntityEndermite)
			{
				String uniqueTag = Reference.MOD_PREFIX + endermiteInfo.getUniqueID();
				EntityEndermite endermite = (EntityEndermite)entity;
				NBTTagCompound data = endermite.getEntityData();
				if(!data.getBoolean(uniqueTag + "Done"))
				{
					AgeTheEndermite(endermiteInfo, endermite, world);
				}
			}
		}
		else if(info instanceof LightBasedAgingInfo)
		{
			LightBasedAgingInfo lightInfo = (LightBasedAgingInfo)info;
			LightChecks(lightInfo, entity, world);
		}
		else if(info instanceof MagicBasedAgingInfo)
		{
			MagicBasedAgingInfo magicInfo = (MagicBasedAgingInfo)info;
			magicChecks(magicInfo, entity, world);
		}
		else if(info instanceof MoonBasedAgingInfo)
		{
			MoonBasedAgingInfo moonInfo = (MoonBasedAgingInfo)info;
			moonChecks(moonInfo, entity, world);
		}
		else if(info instanceof TimeBasedAgingInfo)
		{
			TimeBasedAgingInfo timeInfo = (TimeBasedAgingInfo)info;
			timeChecks(timeInfo, entity, world);
		}
		else if(info instanceof WeatherBasedAgingInfo)
		{
			WeatherBasedAgingInfo weatherInfo = (WeatherBasedAgingInfo)info;
			weatherChecks(weatherInfo, entity, world);
		}
		else
		{
			AgeTheMob(info, entity, world);
		}
	}
	
	public void biomeChecks(BiomeBasedAgingInfo info, Entity entity, World world)
	{
		if(world.getBiome(entity.getPosition()).equals(info.getBiome()))
		{
			AgeTheMob(info, entity, world);
		}
	}
	
	public void biomeTypeChecks(BiomeTypeBasedAgingInfo info, Entity entity, World world)
	{
		if(BiomeDictionary.getTypes(world.getBiome(entity.getPosition())).contains(info.getBiomeType()))
		{
			AgeTheMob(info, entity, world);
		}
	}
	
	public void blockChecks(BlockBasedAgingInfo info, Entity entity, World world)
	{
		BlockPos entityPos = entity.getPosition();
		Block checkForBlock = info.getState().getBlock();
		
		if(info.isNearBlock())
		{
			int radius = info.getRadius();
			for (BlockPos location : BlockPos.getAllInBox(entityPos.add(-radius, -radius, -radius), entityPos.add(radius, radius, radius))) {
				if(world.getBlockState(location).getBlock().equals(checkForBlock))
				{
					AgeTheMob(info, entity, world);
				}
			}
		}
		else
		{
			if(world.getBlockState(entityPos.down()).getBlock().equals(checkForBlock))
			{
				AgeTheMob(info, entity, world);
			}
		}
	}
	
	public void bossChecks(BossAgingInfo info, Entity entity, World world)
	{
		BlockPos entityPos = entity.getPosition();
		if(info.getMaxInArea() != 0)
		{
			int checkRadius = info.getCheckRadius();
			int bossAmount = 0;
			
			AxisAlignedBB areaHitbox = new AxisAlignedBB(entityPos.getX() - 0.5f, entityPos.getY() - 0.5f, entityPos.getZ() - 0.5f, entityPos.getX() + 0.5f, entityPos.getY() + 0.5f, entityPos.getZ() + 0.5f)
					.expand(-checkRadius, -checkRadius, -checkRadius).expand(checkRadius, checkRadius, checkRadius);

			if(!world.getEntitiesWithinAABB(Entity.class, areaHitbox).isEmpty())
			{
				for(Entity foundEntity: world.getEntitiesWithinAABB(Entity.class, areaHitbox)) {
					if(!(foundEntity instanceof EntityPlayer) && !(foundEntity instanceof FakePlayer))
					{
						if(EntityList.isMatchingName(foundEntity, AgeingHelper.getEntityLocation(info.getEvolvedEntity())))
						{
							if(!info.getChangedEntityData().hasNoTags())
							{
								NBTTagCompound entityTag = entityToNBT(foundEntity);
								NBTTagCompound entityTag2 = info.getChangedEntityData();
								
								if(!NBTUtil.areNBTEquals(entityTag2, entityTag, true))
								{
									bossAmount++;
								}
							}
							else
							{
								bossAmount++;
							}
						}
					}
				}
			}
			if(bossAmount < info.getMaxInArea())
			{
				AgeTheMob(info, entity, world);
			}
		}
		else
		{
			AgeTheMob(info, entity, world);
		}
	}
	
	public void dimensionChecks(DimensionBasedAgingInfo info, Entity entity, World world)
	{
		if(entity.dimension == info.getDimensionID())
		{
			AgeTheMob(info, entity, world);
		}
	}
	
	public void LightChecks(LightBasedAgingInfo info, Entity entity, World world)
	{
		BlockPos entityPos = entity.getPosition();
		int minLevel = info.getLightLevelMin();
		int maxLevel = info.getLightLevelMax();
		
		int entityLight = world.getLightFor(EnumSkyBlock.BLOCK, entity.getPosition());
		if(entityLight >= minLevel && entityLight <= maxLevel)
		{
			if(info.isAloneBased())
			{
				AxisAlignedBB areaHitbox = new AxisAlignedBB(entityPos.getX() - 0.5f, entityPos.getY() - 0.5f, entityPos.getZ() - 0.5f, entityPos.getX() + 0.5f, entityPos.getY() + 0.5f, entityPos.getZ() + 0.5f)
						.expand(-5, -5, -5).expand(5, 5, 5);
				if(!world.getEntitiesWithinAABB(Entity.class, areaHitbox).contains(EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEntity()), world)))
				{
					AgeTheMob(info, entity, world);
				}
			}
			else
			{
				AgeTheMob(info, entity, world);
			}
		}
		else
		{
			if(info.isReversable())
			{
				BabifyTheMob(info, entity, world);
			}
		}
	}
	
	public void magicChecks(MagicBasedAgingInfo info, Entity entity, World world)
	{
		BlockPos entityPos = entity.getPosition();
		
		String[] magicalBlocks = AgeingMobsConfigGen.list.magical_blocks;
		
		if(magicalBlocks.length != 0)
		{
			HashMap<IBlockState, Double> importanceList = new HashMap<>();
			
			double totalImportance = 0;
			
			for(int i = 0; i < magicalBlocks.length; i++){
				String block = magicalBlocks[i];
				String[] blockInfo = block.split(";");
				if(blockInfo.length > 2)
				{
					AgeingMobs.logger.error("An error has occured. %s is using the wrong syntax.", new Object[] {block});
				}
				else if(blockInfo.length == 2)
				{
					String blockName = blockInfo[0];
					double importance = Double.valueOf(blockInfo[1]);
					IBlockState state = Block.getBlockFromName(blockName).getDefaultState();
					
					if(!importanceList.containsKey(state))
					{
						importanceList.put(state, importance);
					}
				}
		    }
			
			for (BlockPos location : BlockPos.getAllInBox(entityPos.add(-5, -5, -5), entityPos.add(5, 5, 5))) {
				IBlockState foundState = world.getBlockState(location);
				if(importanceList.containsKey(foundState))
				{
					double importanceFound = importanceList.get(foundState);
					totalImportance = totalImportance + importanceFound;
				}
			}
			
			if(totalImportance > 0)
			{
				AgeTheMobMagic(info, entity, world, totalImportance > 5D ? 5D : totalImportance);
			}
		}
		else
		{
			AgeingMobs.logger.error("An error has occured. %s is using Magical Blocks but your config has no magical blocks specified.", new Object[] {info.getUniqueID()});
			if(AgeList.agingList.contains(info))
			{
				AgeList.agingList.remove(info);
			}
		}
	}
	
	/* The order of the moons as they appear in the game.
	 * 0 = Full Moon
	 * 1 = Waning Gibbous
	 * 2 = Last Quarter
	 * 3 = Waning Crescent
	 * 4 = New Moon
	 * 5 = Waxing Crescent
	 * 6 = First Quarter
	 * 7 = Waxing Gibbous
	 */ 
	
	public void moonChecks(MoonBasedAgingInfo info, Entity entity, World world)
	{
		BlockPos entityPos = entity.getPosition();
		WorldProvider provider = world.provider;
		int moonPhase = provider.getMoonPhase(world.getWorldTime());
		String[] moonDimensions = AgeingMobsConfigGen.list.moon_dimensions;
		
		if(moonDimensions.length != 0)
		{
			ArrayList<Integer> dimensions = new ArrayList<>();
			
			for(int i = 0; i < moonDimensions.length; i++){
				int dim = Integer.valueOf(moonDimensions[i]);
				if(!dimensions.contains(dim))
					dimensions.add(dim);
			}
			
			if(dimensions.contains(entity.dimension))
			{
				if(!world.isDaytime())
				{
					int wantedPhase = moonPhaseFromString(info.getMoonPhase());
					
					if(moonPhase == wantedPhase)
					{
						AgeTheMob(info, entity, world);
					}
				}
			}
		}
		else
		{
			AgeingMobs.logger.error("An error has occured. %s is using a moon phae but your config has no moon dimensions.", new Object[] {info.getUniqueID()});
		}
	}
	
	public int moonPhaseFromString(String moonPhase)
	{
		switch (moonPhase) {
		case "Full Moon":
			return 0;
		case "Waning Gibbous":
			return 1;
		case "Last Quarter":
			return 2;
		case "Waning Crescent":
			return 3;
		case "New Moon":
			return 4;
		case "Waxing Crescent":
			return 5;
		case "First Quarter":
			return 6;
		case "Waxing Gibbous":
			return 7;
		default:
			return 0;
		}
	}
	
	public void timeChecks(TimeBasedAgingInfo info, Entity entity, World world)
	{
		long minTime = (long)info.getMinTime();
		long maxTime = (long)info.getMaxTime();
		long worldTime = world.getWorldTime();
		
		if(worldTime <= maxTime && worldTime >= minTime)
		{
			AgeTheMob(info, entity, world);
		}
	}
	
	public void weatherChecks(WeatherBasedAgingInfo info, Entity entity, World world)
	{
		BlockPos entityPos = entity.getPosition();
		WorldProvider provider = entity.world.provider;
		int wantedWeather = weatherPhaseFromString(info.getWeather());
			
		if(wantedWeather == 0)
		{
			if(!world.isRaining())
			{
				AgeTheMob(info, entity, world);
			}
		}
		else if(wantedWeather == 1)
		{
			if(world.isRaining() && !world.isThundering())
			{
				AgeTheMob(info, entity, world);
			}
		}
		else if(wantedWeather == 2)
		{
			if(world.isThundering())
			{
				AgeTheMob(info, entity, world);
			}
		}
		else
		{
			AgeingMobs.logger.error("An error has occured. %s is using the wrong syntax.", new Object[] {info.getUniqueID()});
		}
	}
	
	public int weatherPhaseFromString(String weatherPhase)
	{
		switch (weatherPhase) {
		case "clear":
			return 0;
		case "rain":
			return 1;
		case "thunder":
			return 2;
		default:
			return 0;
		}
	}
	
	public void AgeTheMob(RegularAgingInfo info, Entity entity, World world)
	{
		int maxTime = info.getTickTime();

		String uniqueTag = Reference.MOD_PREFIX + info.getUniqueID();
		NBTTagCompound tag = entity.getEntityData();
		if(!tag.hasKey(uniqueTag))
		{
			tag.setInteger(uniqueTag, 0);
		}

		if(tag.getInteger(uniqueTag) >= maxTime)
		{
			if(info.getEntity().equals(info.getEvolvedEntity()))
			{
				
				if(!info.getChangedEntityData().hasNoTags())
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						world.spawnEntity(agedEntity);
						
						NBTTagCompound entityTag = entityToNBT(entity);
						NBTTagCompound entityTagCopy = entityTag.copy();
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.hasNoTags())
						{
							entityTag.merge(entityTag2);
							UUID uuid = agedEntity.getUniqueID();
							agedEntity.readFromNBT(entityTag);
							agedEntity.setUniqueId(uuid);
						}
					}
					else
					{
						AgeingMobs.logger.error("Aged Entity invalid: %s", new Object[] {info.getEvolvedEntity().toString()});
					}
					
					tag.removeTag(uniqueTag);
					entity.captureDrops = false;
					world.removeEntity(entity);
				}
			}
			else
			{
				if(!info.getChangedEntityData().hasNoTags())
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						
						NBTTagCompound entityTag = entityToNBT(entity);
						NBTTagCompound entityTagCopy = entityTag.copy();
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.hasNoTags())
						{
							UUID uuid = agedEntity.getUniqueID();
							entityTag.merge(entityTag2);
							agedEntity.readFromNBT(entityTag);
							agedEntity.setUniqueId(uuid);
						}
						world.spawnEntity(agedEntity);
					}
					else
					{
						AgeingMobs.logger.error("Aged Entity invalid: %s", new Object[] {info.getEvolvedEntity().toString()});
					}
					
					tag.removeTag(uniqueTag);
					entity.captureDrops = false;
					world.removeEntity(entity);
				}
				else
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						world.spawnEntity(agedEntity);
					}
					else
					{
						AgeingMobs.logger.error("Aged Entity invalid: %s", new Object[] {info.getEvolvedEntity().toString()});
					}
					
					tag.removeTag(uniqueTag);
					entity.captureDrops = false;
					world.removeEntity(entity);
				}
			}
		}
		else
		{
			int currentAge = tag.getInteger(uniqueTag);
			currentAge++;
			tag.setInteger(uniqueTag, currentAge);
		}
	}
	
	public void AgeTheMobMagic(RegularAgingInfo info, Entity entity, World world, Double efficiency)
	{
		int maxTime = info.getTickTime();
		
		String uniqueTag = Reference.MOD_PREFIX + info.getUniqueID();
		NBTTagCompound tag = entity.getEntityData();
		if(!tag.hasKey(uniqueTag))
		{
			tag.setDouble(uniqueTag, 0);
		}
		
		if(tag.getDouble(uniqueTag) >= maxTime)
		{
			if(info.getEntity().equals(info.getEvolvedEntity()))
			{
				
				if(!info.getChangedEntityData().hasNoTags())
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						world.spawnEntity(agedEntity);
						
						NBTTagCompound entityTag = entityToNBT(entity);
						NBTTagCompound entityTagCopy = entityTag.copy();
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.hasNoTags())
						{
							entityTag.merge(entityTag2);
							UUID uuid = agedEntity.getUniqueID();
							agedEntity.readFromNBT(entityTag);
							agedEntity.setUniqueId(uuid);
						}
					}
					else
					{
						AgeingMobs.logger.error("Aged Entity invalid: %s", new Object[] {info.getEvolvedEntity().toString()});
					}
					
					tag.removeTag(uniqueTag);
					entity.captureDrops = false;
					world.removeEntity(entity);
				}
			}
			else
			{
				if(!info.getChangedEntityData().hasNoTags())
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						
						NBTTagCompound entityTag = entityToNBT(entity);
						NBTTagCompound entityTagCopy = entityTag.copy();
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.hasNoTags())
						{
							UUID uuid = agedEntity.getUniqueID();
							entityTag.merge(entityTag2);
							agedEntity.readFromNBT(entityTag);
							agedEntity.setUniqueId(uuid);
						}
						world.spawnEntity(agedEntity);
					}
					else
					{
						AgeingMobs.logger.error("Aged Entity invalid: %s", new Object[] {info.getEvolvedEntity().toString()});
					}
					
					tag.removeTag(uniqueTag);
					entity.captureDrops = false;
					world.removeEntity(entity);
				}
				else
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						world.spawnEntity(agedEntity);
					}
					else
					{
						AgeingMobs.logger.error("Aged Entity invalid: %s", new Object[] {info.getEvolvedEntity().toString()});
					}
					
					tag.removeTag(uniqueTag);
					entity.captureDrops = false;
					world.removeEntity(entity);
				}
			}
		}
		else
		{
			double currentAge = tag.getDouble(uniqueTag);
			currentAge = currentAge + efficiency;
			tag.setDouble(uniqueTag, currentAge);
		}
	}

	public void BabifyTheMob(RegularAgingInfo info, Entity entity, World world)
	{
		String uniqueTag = Reference.MOD_PREFIX + info.getUniqueID();
		NBTTagCompound tag = entity.getEntityData();
		if(tag.getInteger(uniqueTag) == 0)
			tag.removeTag(uniqueTag);
		
		int currentAge = tag.getInteger(uniqueTag);
		currentAge--;
		tag.setInteger(uniqueTag, currentAge);
	}
	
	public void AgeTheEndermite(EndermiteToShulkerAgeingInfo info, EntityEndermite endermite, World world)
	{
		int maxTime = info.getTickTime();
		BlockPos entityPos = endermite.getPosition();
		
		String uniqueTag = Reference.MOD_PREFIX + info.getUniqueID();
		NBTTagCompound tag = endermite.getEntityData();
		if(!tag.hasKey(uniqueTag))
			tag.setInteger(uniqueTag, 0);
		
		if(tag.getInteger(uniqueTag) >= maxTime)
		{
			endermite.tasks.addTask(5, new AIHideInPurpur(endermite));
			tag.setBoolean(uniqueTag + "Done", true);
			tag.removeTag(uniqueTag);
		}
		else
		{
			int currentAge = tag.getInteger(uniqueTag);
			currentAge++;
			tag.setInteger(uniqueTag, currentAge);
		}
	}
	
	public static NBTTagCompound entityToNBT(Entity theEntity)
    {
        NBTTagCompound nbttagcompound = theEntity.writeToNBT(new NBTTagCompound());

        if (theEntity instanceof EntityPlayer)
        {
            ItemStack itemstack = ((EntityPlayer)theEntity).inventory.getCurrentItem();

            if (!itemstack.isEmpty())
            {
                nbttagcompound.setTag("SelectedItem", itemstack.writeToNBT(new NBTTagCompound()));
            }
        }

        return nbttagcompound;
    }
}
