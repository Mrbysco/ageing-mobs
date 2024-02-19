package com.shynieke.ageingmobs.compat.ct.impl;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.collect.Lists;
import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.helper.NBTHelper;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BiomeCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BiomeTypeCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BlockBasedCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BossCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.DimensionCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.EntityCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.HeightCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.LightCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.LiquidBasedCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.MagicCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.MoonCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.TimeCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.WeatherCriteria;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType.Constructor;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.openzen.zencode.java.ZenCodeType.Name;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ZenRegister
@Name("mods.ageingmobs.AgeingCriteria")
public class MCAgeingCriteria {
	private final BaseCriteria internal;

	public MCAgeingCriteria(BaseCriteria criteria) {
		this.internal = criteria;
	}

	@Constructor
	public MCAgeingCriteria(MCAgeingData ageing) {
		this(new BaseCriteria(ageing.getInternal()));
	}

	@Method
	public MCAgeingCriteria constructBiome(ResourceLocation biomeName) {
		return new MCAgeingCriteria(new BiomeCriteria(this.internal.getAgeingData(), ResourceKey.create(Registries.BIOME, biomeName)));
	}

	@Method
	public MCAgeingCriteria constructBiomeTag(ResourceLocation biomeTag) {
		return new MCAgeingCriteria(new BiomeTypeCriteria(this.internal.getAgeingData(), biomeTag));
	}

	@Method
	public MCAgeingCriteria constructBlockBased(Block[] blocks, Boolean nearBlock, int radius) {
		if (blocks.length > 0) {
			List<Block> blockList = Lists.newArrayList(blocks);
			Block[] blockArray = new Block[blockList.size()];
			blockArray = blockList.toArray(blockArray);
			return new MCAgeingCriteria(new BlockBasedCriteria(this.internal.getAgeingData(), blockArray, nearBlock, radius));
		}
		return this;
	}

	@Method
	public MCAgeingCriteria constructBlockBased(String[] blocks, Boolean nearBlock, int radius) {
		if (blocks.length > 0) {
			List<Block> blockList = Lists.newArrayList();
			for (String blockName : blocks) {
				Optional<Block> optionalBlock = BuiltInRegistries.BLOCK.getOptional(new ResourceLocation(blockName));
				if (optionalBlock.isPresent()) {
					blockList.add(optionalBlock.get());
				} else {
					AgeingMobs.LOGGER.error("Could not resolve block: " + blockName);
				}
			}
			Block[] blockArray = new Block[blockList.size()];
			blockArray = blockList.toArray(blockArray);
			return new MCAgeingCriteria(new BlockBasedCriteria(this.internal.getAgeingData(), blockArray, nearBlock, radius));
		}
		return this;
	}

	@Method
	public MCAgeingCriteria constructBoss(int maxInArea, int checkRadius) {
		return new MCAgeingCriteria(new BossCriteria(this.internal.getAgeingData(), maxInArea, checkRadius));
	}

	@Method
	public MCAgeingCriteria constructDimension(ResourceLocation[] dimensions) {
		if (dimensions.length > 0) {
			List<ResourceLocation> blockList = Lists.newArrayList();
			blockList.addAll(Arrays.asList(dimensions));
			ResourceLocation[] dimensionArray = new ResourceLocation[blockList.size()];
			dimensionArray = blockList.toArray(dimensionArray);
			return new MCAgeingCriteria(new DimensionCriteria(this.internal.getAgeingData(), dimensionArray));
		}
		return this;
	}

	@Method
	public MCAgeingCriteria constructEntity(EntityType<Entity> nearbyEntity, String nearbyEntityData, int radius) {
		return new MCAgeingCriteria(new EntityCriteria(this.internal.getAgeingData(), nearbyEntity, NBTHelper.createNBTTag(nearbyEntityData), radius));
	}

	@Method
	public MCAgeingCriteria constructHeight(int minHeight, int maxHeight) {
		return new MCAgeingCriteria(new HeightCriteria(this.internal.getAgeingData(), minHeight, maxHeight));
	}

	@Method
	public MCAgeingCriteria constructLight(int lightLevelMin, int lightLevelMax, boolean aloneBased, boolean reversible) {
		return new MCAgeingCriteria(new LightCriteria(this.internal.getAgeingData(), lightLevelMin, lightLevelMax, aloneBased, reversible));
	}

	@Method
	public MCAgeingCriteria constructLiquidBased(String liquid, Boolean reversible) {
		return new MCAgeingCriteria(new LiquidBasedCriteria(this.internal.getAgeingData(), liquid, reversible));
	}

	@Method
	public MCAgeingCriteria constructMagic(int range) {
		return new MCAgeingCriteria(new MagicCriteria(this.internal.getAgeingData(), range));
	}

	@Method
	public MCAgeingCriteria constructMoon(String moonPhase) {
		return new MCAgeingCriteria(new MoonCriteria(this.internal.getAgeingData(), moonPhase));
	}

	@Method
	public MCAgeingCriteria constructTime(int minTime, int maxTime) {
		return new MCAgeingCriteria(new TimeCriteria(this.internal.getAgeingData(), minTime, maxTime));
	}

	@Method
	public MCAgeingCriteria constructWeather(String weather) {
		return new MCAgeingCriteria(new WeatherCriteria(this.internal.getAgeingData(), weather));
	}

	public BaseCriteria getInternal() {
		return this.internal;
	}
}
