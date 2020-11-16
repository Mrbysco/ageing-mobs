package com.shynieke.ageingmobs.compat.ct.impl;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.blocks.MCBlock;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.world.MCBiome;
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
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.ageingmobs.AgeingCriteria")
public class MCCriteria {
    private final BaseCriteria internal;

    public MCCriteria(BaseCriteria criteria) {
        this.internal = criteria;
    }

    @ZenCodeType.Constructor
    public MCCriteria(MCAgeing ageing) {
        this(new BaseCriteria(ageing.getInternal()));
    }

    @ZenCodeType.Method
    public MCCriteria constructBiome(MCBiome biome) {
        return new MCCriteria(new BiomeCriteria(this.internal.getAgeingData(), biome.getInternal()));
    }

    @ZenCodeType.Method
    public MCCriteria constructBiome(String biomeName) {
        net.minecraft.world.biome.Biome biome = net.minecraft.world.biome.Biomes.THE_VOID;
        if(net.minecraftforge.registries.ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeName)) != null) {
            biome = net.minecraftforge.registries.ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeName));
        } else {
            AgeingMobs.LOGGER.error("Could not find biome with ID: " + biomeName);
        }
        return new MCCriteria(new BiomeCriteria(this.internal.getAgeingData(), biome));
    }

    @ZenCodeType.Method
    public MCCriteria constructBiomeType(String biomeType) {
        BiomeDictionary.Type returnType = BiomeDictionary.Type.WATER;
        for(BiomeDictionary.Type type : BiomeDictionary.Type.getAll()) {
            if(type.getName().equals(biomeType)) {
                returnType = type;
                break;
            }
        }
        return new MCCriteria(new BiomeTypeCriteria(this.internal.getAgeingData(), returnType));
    }

    @ZenCodeType.Method
    public MCCriteria constructBlockBased(MCBlock[] blocks, Boolean nearBlock, int radius) {
        if(blocks.length > 0) {
            List<Block> blockList = Lists.newArrayList();
            for (MCBlock block : blocks) {
                Block newInternal = block.getInternal();
                blockList.add(newInternal);
            }
            Block[] blockArray = new Block[blockList.size()];
            blockArray = blockList.toArray(blockArray);
            return new MCCriteria(new BlockBasedCriteria(this.internal.getAgeingData(), blockArray,nearBlock, radius));
        }
        return this;
    }

    @ZenCodeType.Method
    public MCCriteria constructBlockBased(String[] blocks, Boolean nearBlock, int radius) {
        if(blocks.length > 0) {
            List<Block> blockList = Lists.newArrayList();
            for (String blockName : blocks) {
                Block block = net.minecraftforge.registries.ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName));
                if (block != null) {
                    blockList.add(block);
                } else {
                    AgeingMobs.LOGGER.error("Could not resolve block: " + blockName);
                }
            }
            Block[] blockArray = new Block[blockList.size()];
            blockArray = blockList.toArray(blockArray);
            return new MCCriteria(new BlockBasedCriteria(this.internal.getAgeingData(), blockArray,nearBlock, radius));
        }
        return this;
    }

    @ZenCodeType.Method
    public MCCriteria constructBoss(int maxInArea, int checkRadius) {
        return new MCCriteria(new BossCriteria(this.internal.getAgeingData(), maxInArea, checkRadius));
    }

    @ZenCodeType.Method
    public MCCriteria constructDimension(Integer[] dimensions) {
        if(dimensions.length > 0) {
            List<Integer> blockList = Lists.newArrayList();
            blockList.addAll(Arrays.asList(dimensions));
            Integer[] dimensionArray = new Integer[blockList.size()];
            dimensionArray = blockList.toArray(dimensionArray);
            return new MCCriteria(new DimensionCriteria(this.internal.getAgeingData(), dimensionArray));
        }
        return this;
    }

    @ZenCodeType.Method
    public MCCriteria constructEntity(MCEntityType nearbyEntity, String nearbyEntityData, int radius) {
        return new MCCriteria(new EntityCriteria(this.internal.getAgeingData(), nearbyEntity.getInternal(), NBTHelper.createNBTTag(nearbyEntityData), radius));
    }

    @ZenCodeType.Method
    public MCCriteria constructHeight(int minHeight, int maxHeight) {
        return new MCCriteria(new HeightCriteria(this.internal.getAgeingData(), minHeight, maxHeight));
    }

    @ZenCodeType.Method
    public MCCriteria constructLight(int lightLevelMin, int lightLevelMax, boolean aloneBased, boolean reversible) {
        return new MCCriteria(new LightCriteria(this.internal.getAgeingData(), lightLevelMin, lightLevelMax, aloneBased, reversible));
    }

    @ZenCodeType.Method
    public MCCriteria constructLiquidBased(String liquid, Boolean reversible) {
        return new MCCriteria(new LiquidBasedCriteria(this.internal.getAgeingData(), liquid, reversible));
    }

    @ZenCodeType.Method
    public MCCriteria constructMagic(int range) {
        return new MCCriteria(new MagicCriteria(this.internal.getAgeingData(), range));
    }

    @ZenCodeType.Method
    public MCCriteria constructMoon(String moonPhase) {
        return new MCCriteria(new MoonCriteria(this.internal.getAgeingData(), moonPhase));
    }

    @ZenCodeType.Method
    public MCCriteria constructTime(int minTime, int maxTime) {
        return new MCCriteria(new TimeCriteria(this.internal.getAgeingData(), minTime, maxTime));
    }

    @ZenCodeType.Method
    public MCCriteria constructWeather(String weather) {
        return new MCCriteria(new WeatherCriteria(this.internal.getAgeingData(), weather));
    }

    public BaseCriteria getInternal() {
        return this.internal;
    }
}
