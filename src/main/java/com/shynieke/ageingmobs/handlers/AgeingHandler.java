package com.shynieke.ageingmobs.handlers;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.Reference;
import com.shynieke.ageingmobs.config.AgeingMobsConfigGen;
import com.shynieke.ageingmobs.endermite.AIHideInPurpur;
import com.shynieke.ageingmobs.endermite.EndermiteToShulkerAgeingInfo;
import com.shynieke.ageingmobs.lists.AgeList;
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
import com.shynieke.ageingmobs.util.AgeingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class AgeingHandler {

	@SubscribeEvent
	public void ageHandler(TickEvent.WorldTickEvent event) {
		if (event.phase.equals(TickEvent.Phase.END) && event.side.isServer()) {
			WorldServer world = (WorldServer)event.world;
			if (world.getWorldTime() % 20 == 0) {
				ArrayList<RegularAgingInfo> ageingList = AgeList.agingList;
				if(!ageingList.isEmpty()) {
					ArrayList<Entity> loadedEntityList = new ArrayList<>(world.loadedEntityList);
					Iterator<Entity> entityIterator = loadedEntityList.iterator();
					while(entityIterator.hasNext()) {
						Entity entityIn = entityIterator.next();

						for(RegularAgingInfo info : ageingList) {
							if(entityIn != null && !(entityIn instanceof EntityPlayer) && info.getEntity() != null) {
								if(EntityList.isMatchingName(entityIn, AgeingHelper.getEntityLocation(info.getEntity()))) {
									if(info.getEvolvedEntity() != null && info.getEntity().equals(info.getEvolvedEntity())) {
										if(!info.getEvolvedEntity().isEmpty()) {
											CheckList(info, entityIn, world);
										} else {
											AgeingMobs.logger.error("An error has occured. A mob can not transform into itself. See id: " + info.getUniqueID());
											if(AgeList.agingList.contains(info)) {
												AgeList.agingList.remove(info);
											}
										}
									} else {
										CheckList(info, entityIn, world);
									}
								}
							}
						}
					}
				}
			}
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
				if(info.getEntityData().isEmpty())
				{
					if(!info.getChangedEntityData().isEmpty())
					{
						NBTTagCompound entityTag = AgeingHelper.entityToNBT(entity);
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.isEmpty())
						{
							if(!NBTUtil.areNBTEquals(entityTag2, entityTag, true))
							{
								extraChecks(info, entity, world);
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
					if(!info.getChangedEntityData().isEmpty())
					{
						NBTTagCompound entityTag = AgeingHelper.entityToNBT(entity);
						NBTTagCompound entityTag2 = info.getEntityData();
						NBTTagCompound entityTag3 = info.getChangedEntityData();

						if(!entityTag2.isEmpty() && !entityTag3.isEmpty())
						{
							if(NBTUtil.areNBTEquals(entityTag2, entityTag, true) && !NBTUtil.areNBTEquals(entityTag3, entityTag, true))
							{
								extraChecks(info, entity, world);
							}
						}
					}
					else
					{
						NBTTagCompound entityTag = AgeingHelper.entityToNBT(entity);
						NBTTagCompound entityTag2 = info.getEntityData();

						if(!entityTag2.isEmpty())
						{							
							if(NBTUtil.areNBTEquals(entityTag2, entityTag, true))
							{
								extraChecks(info, entity, world);
							}
						}
					}
				}
			}
		}
		else
		{
			if(!info.getEntityData().isEmpty())
			{
				NBTTagCompound entityTag = AgeingHelper.entityToNBT(entity);
				NBTTagCompound entityTag2 = info.getEntityData();
				NBTTagCompound entityTag3 = info.getChangedEntityData();

				if(!entityTag2.isEmpty() && !entityTag3.isEmpty())
				{
					if(NBTUtil.areNBTEquals(entityTag2, entityTag, true) && !NBTUtil.areNBTEquals(entityTag3, entityTag, true))
					{
						extraChecks(info, entity, world);
					}
				}
			}
			else
			{
				extraChecks(info, entity, world);
			}
		}
	}
	
	public void extraChecks(RegularAgingInfo info, Entity entity, World world)
	{
		if(Loader.isModLoaded("gamestages"))
		{
			gamestageChecks(info, entity, world);
		}
		else
		{
			ExecuteAppropiateMethod(info, entity, world);
		}
	}
	
	@Optional.Method(modid = "gamestages")
	public void gamestageChecks(RegularAgingInfo info, Entity entity, World world)
	{
		if(info.getGameStage() != null)
		{
			if(!info.getGameStage().isEmpty())
			{
				for (final EntityPlayer player : world.playerEntities) {
		            if (net.darkhax.gamestages.GameStageHelper.hasStage(player, info.getGameStage()) && net.darkhax.bookshelf.util.EntityUtils.getDistanceFromEntity(player, entity) <= 32) {
		            	ExecuteAppropiateMethod(info, entity, world);
		            	break;
		            }
				}
			}
			else
			{
				AgeingMobs.logger.error("Given stage of ageing is empty: %s", new Object[] {info.getUniqueID()});
				if(AgeList.agingList.contains(info))
				{
					AgeList.agingList.remove(info);
				}
			}
		}
		else
		{
			ExecuteAppropiateMethod(info, entity, world);
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
		else if(info instanceof LiquidBasedAgingInfo)
		{
			LiquidBasedAgingInfo liquidInfo = (LiquidBasedAgingInfo)info;
			liquidChecks(liquidInfo, entity, world);
		}
		else if(info instanceof HeightBasedAgingInfo)
		{
			HeightBasedAgingInfo heightInfo = (HeightBasedAgingInfo)info;
			heightChecks(heightInfo, entity, world);
		}
		else if(info instanceof EntityBasedAgingInfo)
		{
			EntityBasedAgingInfo entityInfo = (EntityBasedAgingInfo)info;
			entityChecks(entityInfo, entity, world);
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
							if(!info.getChangedEntityData().isEmpty())
							{
								NBTTagCompound entityTag = AgeingHelper.entityToNBT(foundEntity);
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
	
	public void liquidChecks(LiquidBasedAgingInfo info, Entity entity, World world)
	{
		Fluid fluid = FluidRegistry.getFluid(info.getLiquid());
		AxisAlignedBB bb = entity.getEntityBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D);
		int j2 = MathHelper.floor(bb.minX);
        int k2 = MathHelper.ceil(bb.maxX);
        int l2 = MathHelper.floor(bb.minY);
        int i3 = MathHelper.ceil(bb.maxY);
        int j3 = MathHelper.floor(bb.minZ);
        int k3 = MathHelper.ceil(bb.maxZ);
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

        for (int l3 = j2; l3 < k2; ++l3)
        {
            for (int i4 = l2; i4 < i3; ++i4)
            {
                for (int j4 = j3; j4 < k3; ++j4)
                {
                    IBlockState state = world.getBlockState(blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
                    
                    if (state.getMaterial() != Material.AIR && state.getBlock() == fluid.getBlock())
                    {
                        blockpos$pooledmutableblockpos.release();
        				AgeTheMob(info, entity, world);
        				break;
                    }
                }
            }
        }

        blockpos$pooledmutableblockpos.release();
	}
	
	public void heightChecks(HeightBasedAgingInfo info, Entity entity, World world)
	{
		int minHeight = info.getMinHeight();
		int maxHeight = info.getMaxHeight();
		int entityHeight = entity.getPosition().getY();

		if(entityHeight >= minHeight && entityHeight <= maxHeight)
		{
			AgeTheMob(info, entity, world);
		}
	}
	
	public void entityChecks(EntityBasedAgingInfo info, Entity entity, World world)
	{
		BlockPos entityPos = entity.getPosition();
		int checkRadius = info.getRadius();
		int nearbyEntityAmount = 0;
		
		AxisAlignedBB areaHitbox = new AxisAlignedBB(entityPos.getX() - 0.5f, entityPos.getY() - 0.5f, entityPos.getZ() - 0.5f, entityPos.getX() + 0.5f, entityPos.getY() + 0.5f, entityPos.getZ() + 0.5f)
				.expand(-checkRadius, -checkRadius, -checkRadius).expand(checkRadius, checkRadius, checkRadius);

		if(!world.getEntitiesWithinAABB(Entity.class, areaHitbox).isEmpty())
		{
			for(Entity foundEntity: world.getEntitiesWithinAABB(Entity.class, areaHitbox)) {
				if(!(foundEntity instanceof EntityPlayer) && !(foundEntity instanceof FakePlayer))
				{
					if(EntityList.isMatchingName(foundEntity, AgeingHelper.getEntityLocation(info.getNearbyEntity())))
					{
						if(!info.getChangedEntityData().isEmpty())
						{
							NBTTagCompound entityTag = AgeingHelper.entityToNBT(foundEntity);
							NBTTagCompound entityTag2 = info.getNearbyEntityData();
							
							if(!NBTUtil.areNBTEquals(entityTag2, entityTag, true))
							{
								nearbyEntityAmount++;
							}
						}
						else
						{
							nearbyEntityAmount++;
						}
					}
				}
			}
		}
		if(nearbyEntityAmount != 0)
		{
			AgeTheMob(info, entity, world);
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
				if(!info.getChangedEntityData().isEmpty())
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						world.spawnEntity(agedEntity);
						
						NBTTagCompound entityTag = AgeingHelper.entityToNBT(entity);
						NBTTagCompound entityTagCopy = entityTag.copy();
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.isEmpty())
						{
							entityTag.merge(entityTag2);
							UUID uuid = agedEntity.getUniqueID();
							agedEntity.readFromNBT(entityTag);
							agedEntity.setUniqueId(uuid);
						}
					}
					else
					{
						AgeingMobs.logger.error("Aged Entity invalid [Line 661, Report this to the author of Ageing Mobs]: " + info.getEvolvedEntity().toString());
					}
					
					tag.removeTag(uniqueTag);
					entity.captureDrops = false;
					world.removeEntity(entity);
				}
			}
			else
			{
				if(!info.getChangedEntityData().isEmpty())
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						
						NBTTagCompound entityTag = AgeingHelper.entityToNBT(entity);
						NBTTagCompound entityTagCopy = entityTag.copy();
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.isEmpty())
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
						AgeingMobs.logger.error("Aged Entity invalid [Line 646, Report this to the author of Ageing Mobs]: " + info.getEvolvedEntity().toString());
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
//					System.out.println(info.getEvolvedEntity().toString());
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						world.spawnEntity(agedEntity);
					}
					else
					{
						AgeingMobs.logger.error("Aged Entity invalid [Line 666, Report this to the author of Ageing Mobs]: " + info.getEvolvedEntity().toString());
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
				if(!info.getChangedEntityData().isEmpty())
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						world.spawnEntity(agedEntity);
						
						NBTTagCompound entityTag = AgeingHelper.entityToNBT(entity);
						NBTTagCompound entityTagCopy = entityTag.copy();
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.isEmpty())
						{
							entityTag.merge(entityTag2);
							UUID uuid = agedEntity.getUniqueID();
							agedEntity.readFromNBT(entityTag);
							agedEntity.setUniqueId(uuid);
						}
					}
					else
					{
						AgeingMobs.logger.error("Magic Aged Entity invalid [Line 722, Report this to the author of Ageing Mobs]: " + info.getEvolvedEntity().toString());
					}
					
					tag.removeTag(uniqueTag);
					entity.captureDrops = false;
					world.removeEntity(entity);
				}
			}
			else
			{
				if(!info.getChangedEntityData().isEmpty())
				{
					BlockPos originalPos = entity.getPosition();
					Entity agedEntity = null;
					
					agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(info.getEvolvedEntity()), entity.world);
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						
						NBTTagCompound entityTag = AgeingHelper.entityToNBT(entity);
						NBTTagCompound entityTagCopy = entityTag.copy();
						NBTTagCompound entityTag2 = info.getChangedEntityData();
						
						if(!entityTag2.isEmpty())
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
						AgeingMobs.logger.error("Magic Aged Entity invalid [Line 757, Report this to the author of Ageing Mobs]: " + info.getEvolvedEntity().toString());
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
//					System.out.println(info.getEvolvedEntity().toString());
					if(agedEntity != null)
					{
						agedEntity.copyLocationAndAnglesFrom(entity);
						world.spawnEntity(agedEntity);
					}
					else
					{
						AgeingMobs.logger.error("Magic Aged Entity invalid [Line 777, Report this to the author of Ageing Mobs]: " + info.getEvolvedEntity().toString());
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
}
