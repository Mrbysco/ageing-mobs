package com.shynieke.ageingmobs.compat.jei;

import com.shynieke.ageingmobs.compat.jei.biome.BiomeCategory;
import com.shynieke.ageingmobs.compat.jei.biome.BiomeWrapper;
import com.shynieke.ageingmobs.compat.jei.biometype.BiomeTypeCategory;
import com.shynieke.ageingmobs.compat.jei.biometype.BiomeTypeWrapper;
import com.shynieke.ageingmobs.compat.jei.block.BlockCategory;
import com.shynieke.ageingmobs.compat.jei.block.BlockWrapper;
import com.shynieke.ageingmobs.compat.jei.boss.BossCategory;
import com.shynieke.ageingmobs.compat.jei.boss.BossWrapper;
import com.shynieke.ageingmobs.compat.jei.dimension.DimensionCategory;
import com.shynieke.ageingmobs.compat.jei.dimension.DimensionWrapper;
import com.shynieke.ageingmobs.compat.jei.entity.EntityCategory;
import com.shynieke.ageingmobs.compat.jei.entity.EntityWrapper;
import com.shynieke.ageingmobs.compat.jei.height.HeightCategory;
import com.shynieke.ageingmobs.compat.jei.height.HeightWrapper;
import com.shynieke.ageingmobs.compat.jei.lightlevel.LightLevelCategory;
import com.shynieke.ageingmobs.compat.jei.lightlevel.LightLevelWrapper;
import com.shynieke.ageingmobs.compat.jei.liquid.LiquidCategory;
import com.shynieke.ageingmobs.compat.jei.liquid.LiquidWrapper;
import com.shynieke.ageingmobs.compat.jei.magic.MagicCategory;
import com.shynieke.ageingmobs.compat.jei.magic.MagicWrapper;
import com.shynieke.ageingmobs.compat.jei.moon.MoonCategory;
import com.shynieke.ageingmobs.compat.jei.moon.MoonWrapper;
import com.shynieke.ageingmobs.compat.jei.regular.RegularCategory;
import com.shynieke.ageingmobs.compat.jei.regular.RegularWrapper;
import com.shynieke.ageingmobs.compat.jei.time.TimeCategory;
import com.shynieke.ageingmobs.compat.jei.time.TimeWrapper;
import com.shynieke.ageingmobs.compat.jei.weather.WeatherCategory;
import com.shynieke.ageingmobs.compat.jei.weather.WeatherWrapper;
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
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class JEIAgeingPlugin implements IModPlugin{

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        
		registry.addRecipeCategories(new RegularCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new BossCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new MagicCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new BlockCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new BiomeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new BiomeTypeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new DimensionCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new LightLevelCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new MoonCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new TimeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new WeatherCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new LiquidCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new HeightCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new EntityCategory(jeiHelpers.getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registry) {
		registerAgeing(registry);
	}
	
	private void registerAgeing(IModRegistry registry) {
		List<RegularWrapper> regular = new ArrayList<RegularWrapper>();
		List<BossWrapper> boss = new ArrayList<BossWrapper>();
		List<MagicWrapper> magic = new ArrayList<MagicWrapper>();
		List<BlockWrapper> block = new ArrayList<BlockWrapper>();
		List<BiomeWrapper> biome = new ArrayList<BiomeWrapper>();
		List<BiomeTypeWrapper> biometype = new ArrayList<BiomeTypeWrapper>();
		List<DimensionWrapper> dimension = new ArrayList<DimensionWrapper>();
		List<LightLevelWrapper> lightlevel = new ArrayList<LightLevelWrapper>();
		List<MoonWrapper> moon = new ArrayList<MoonWrapper>();
		List<TimeWrapper> time = new ArrayList<TimeWrapper>();
		List<WeatherWrapper> weather = new ArrayList<WeatherWrapper>();
		List<LiquidWrapper> liquid = new ArrayList<LiquidWrapper>();
		List<HeightWrapper> height = new ArrayList<HeightWrapper>();
		List<EntityWrapper> entity = new ArrayList<EntityWrapper>();
		
		for(RegularAgingInfo info : AgeList.agingList)
		{
			ItemStack egg1 = getEgg(info.getEntity());
			ItemStack egg2 = getEgg(info.getEvolvedEntity());
			if(info.getClass().equals(RegularAgingInfo.class))
			{
				regular.add(new RegularWrapper(egg1, egg2, info.getEntity(), info.getEntityData(), info.getEvolvedEntity(), info.getChangedEntityData(), info.getTickTime()));
			}
			else if(info.getClass().equals(BossAgingInfo.class))
			{
				BossAgingInfo bossInfo = (BossAgingInfo)info;
				boss.add(new BossWrapper(egg1, egg2, bossInfo.getEntity(), bossInfo.getEntityData(), bossInfo.getEvolvedEntity(), bossInfo.getChangedEntityData(), bossInfo.getTickTime()));
			}
			else if(info.getClass().equals(MagicBasedAgingInfo.class))
			{
				MagicBasedAgingInfo magicInfo = (MagicBasedAgingInfo)info;
				magic.add(new MagicWrapper(egg1, egg2, magicInfo.getEntity(), magicInfo.getEntityData(), magicInfo.getEvolvedEntity(), magicInfo.getChangedEntityData(), magicInfo.getTickTime()));
			}
			else if(info.getClass().equals(BlockBasedAgingInfo.class))
			{
				BlockBasedAgingInfo blockInfo = (BlockBasedAgingInfo)info;
				block.add(new BlockWrapper(egg1, egg2, blockInfo.getEntity(), blockInfo.getEntityData(), blockInfo.getEvolvedEntity(), blockInfo.getChangedEntityData(), blockInfo.getTickTime(), blockInfo.getState().getBlock()));
			}
			else if(info.getClass().equals(BiomeBasedAgingInfo.class))
			{
				BiomeBasedAgingInfo biomeInfo = (BiomeBasedAgingInfo)info;
				biome.add(new BiomeWrapper(egg1, egg2, biomeInfo.getEntity(), biomeInfo.getEntityData(), biomeInfo.getEvolvedEntity(), biomeInfo.getChangedEntityData(), biomeInfo.getTickTime(), biomeInfo.getBiome()));
			}
			else if(info.getClass().equals(BiomeTypeBasedAgingInfo.class))
			{
				BiomeTypeBasedAgingInfo biomeTypeInfo = (BiomeTypeBasedAgingInfo)info;
				biometype.add(new BiomeTypeWrapper(egg1, egg2, biomeTypeInfo.getEntity(), biomeTypeInfo.getEntityData(), biomeTypeInfo.getEvolvedEntity(), biomeTypeInfo.getChangedEntityData(), biomeTypeInfo.getTickTime(), biomeTypeInfo.getBiomeType()));
			}
			else if(info.getClass().equals(DimensionBasedAgingInfo.class))
			{
				DimensionBasedAgingInfo dimensionInfo = (DimensionBasedAgingInfo)info;
				dimension.add(new DimensionWrapper(egg1, egg2, dimensionInfo.getEntity(), dimensionInfo.getEntityData(), dimensionInfo.getEvolvedEntity(), dimensionInfo.getChangedEntityData(), dimensionInfo.getTickTime(), dimensionInfo.getDimensionID()));
			}
			else if(info.getClass().equals(LightBasedAgingInfo.class))
			{
				LightBasedAgingInfo lightLevelInfo = (LightBasedAgingInfo)info;
				lightlevel.add(new LightLevelWrapper(egg1, egg2, lightLevelInfo.getEntity(), lightLevelInfo.getEntityData(), lightLevelInfo.getEvolvedEntity(), lightLevelInfo.getChangedEntityData(), lightLevelInfo.getTickTime(), lightLevelInfo.getLightLevelMin(), lightLevelInfo.getLightLevelMax()));
			}
			else if(info.getClass().equals(MoonBasedAgingInfo.class))
			{
				MoonBasedAgingInfo moonInfo = (MoonBasedAgingInfo)info;
				moon.add(new MoonWrapper(egg1, egg2, moonInfo.getEntity(), moonInfo.getEntityData(), moonInfo.getEvolvedEntity(), moonInfo.getChangedEntityData(), moonInfo.getTickTime(), moonInfo.getMoonPhase()));
			}
			else if(info.getClass().equals(TimeBasedAgingInfo.class))
			{
				TimeBasedAgingInfo timeInfo = (TimeBasedAgingInfo)info;
				time.add(new TimeWrapper(egg1, egg2, timeInfo.getEntity(), timeInfo.getEntityData(), timeInfo.getEvolvedEntity(), timeInfo.getChangedEntityData(), timeInfo.getTickTime(), timeInfo.getMinTime(), timeInfo.getMaxTime()));
			}
			else if(info.getClass().equals(WeatherBasedAgingInfo.class))
			{
				WeatherBasedAgingInfo weatherInfo = (WeatherBasedAgingInfo)info;
				weather.add(new WeatherWrapper(egg1, egg2, weatherInfo.getEntity(), weatherInfo.getEntityData(), weatherInfo.getEvolvedEntity(), weatherInfo.getChangedEntityData(), weatherInfo.getTickTime(), weatherInfo.getWeather()));
			}
			else if(info.getClass().equals(LiquidBasedAgingInfo.class))
			{
				LiquidBasedAgingInfo liquidInfo = (LiquidBasedAgingInfo)info;
				liquid.add(new LiquidWrapper(egg1, egg2, liquidInfo.getEntity(), liquidInfo.getEntityData(), liquidInfo.getEvolvedEntity(), liquidInfo.getChangedEntityData(), liquidInfo.getTickTime(), liquidInfo.getLiquid()));
			}
			else if(info.getClass().equals(HeightBasedAgingInfo.class))
			{
				HeightBasedAgingInfo heightInfo = (HeightBasedAgingInfo)info;
				height.add(new HeightWrapper(egg1, egg2, heightInfo.getEntity(), heightInfo.getEntityData(), heightInfo.getEvolvedEntity(), heightInfo.getChangedEntityData(), heightInfo.getTickTime(), heightInfo.getMinHeight(), heightInfo.getMaxHeight()));
			}
			else if(info.getClass().equals(EntityBasedAgingInfo.class))
			{
				EntityBasedAgingInfo entityInfo = (EntityBasedAgingInfo)info;
				ItemStack egg3 = getEgg(entityInfo.getNearbyEntity());
				entity.add(new EntityWrapper(egg1, egg2, egg3, entityInfo.getEntity(), entityInfo.getEntityData(), entityInfo.getEvolvedEntity(), entityInfo.getChangedEntityData(), entityInfo.getTickTime(), entityInfo.getNearbyEntity(), entityInfo.getNearbyEntityData()));
			}
		}
		
		if(!regular.isEmpty())
		{
			registry.addRecipes(regular, RegularCategory.UID);
		}
		if(!boss.isEmpty())
		{
			registry.addRecipes(boss, BossCategory.UID);
		}
		if(!magic.isEmpty())
		{
			registry.addRecipes(magic, MagicCategory.UID);
		}
		if(!block.isEmpty())
		{
			registry.addRecipes(block, BlockCategory.UID);
		}
		if(!biome.isEmpty())
		{
			registry.addRecipes(biome, BiomeCategory.UID);
		}
		if(!biometype.isEmpty())
		{
			registry.addRecipes(biometype, BiomeTypeCategory.UID);
		}
		if(!dimension.isEmpty())
		{
			registry.addRecipes(dimension, DimensionCategory.UID);
		}
		if(!lightlevel.isEmpty())
		{
			registry.addRecipes(lightlevel, LightLevelCategory.UID);
		}
		if(!moon.isEmpty())
		{
			registry.addRecipes(moon, MoonCategory.UID);
		}
		if(!time.isEmpty())
		{
			registry.addRecipes(time, TimeCategory.UID);
		}
		if(!weather.isEmpty())
		{
			registry.addRecipes(weather, WeatherCategory.UID);
		}
		if(!liquid.isEmpty())
		{
			registry.addRecipes(liquid, LiquidCategory.UID);
		}
		if(!height.isEmpty())
		{
			registry.addRecipes(height, HeightCategory.UID);
		}
		if(!entity.isEmpty())
		{
			registry.addRecipes(entity, EntityCategory.UID);
		}
	}
	
	public static ItemStack getEgg(String entityName)
    {
        ItemStack stack = new ItemStack(Items.SPAWN_EGG);
        NBTTagCompound entityTag = new NBTTagCompound();
        entityTag.setString("id", entityName);
        NBTTagCompound eggTag = new NBTTagCompound();
        eggTag.setTag("EntityTag", entityTag);
        stack.setTagCompound(eggTag);
        return stack;
    }
}
