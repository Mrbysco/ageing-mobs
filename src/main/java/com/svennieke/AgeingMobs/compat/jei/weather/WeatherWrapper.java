package com.svennieke.AgeingMobs.compat.jei.weather;

import java.util.UUID;

import com.svennieke.AgeingMobs.compat.jei.JeiRenderHelpers;
import com.svennieke.AgeingMobs.util.AgeingHelper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WeatherWrapper implements IRecipeWrapper{
	private final ItemStack egg1;
	private final ItemStack egg2;
	private final String entity;
	private final NBTTagCompound entityData;
	private final String evolvedEntity;
	private final NBTTagCompound changedEntityData;
	private final int tickTime;
	private final String weather;
	
	public WeatherWrapper(ItemStack egg1, ItemStack egg2, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime, String weather) {
		this.entity = entity;
		this.entityData = entityData;
		this.evolvedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.weather = weather;
		this.egg1 = egg1;
		this.egg2 = egg2;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, egg1);
		ingredients.setOutput(ItemStack.class, egg2);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		 Entity originalEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(entity), minecraft.world);
		 Entity agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(evolvedEntity), minecraft.world);

		 if(originalEntity != null && agedEntity != null)
		 {
			 if(!entityData.hasNoTags())
			 {
				 NBTTagCompound nbttagcompound = AgeingHelper.entityToNBT(originalEntity);
	             NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
				 
	             UUID uuid = originalEntity.getUniqueID();
	             nbttagcompound.merge(entityData);
	             originalEntity.setUniqueId(uuid);
	             originalEntity.readFromNBT(nbttagcompound);
			 }
			 
			 if(!changedEntityData.hasNoTags())
			 {
				 NBTTagCompound nbttagcompound = AgeingHelper.entityToNBT(agedEntity);
	             NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
				 
	             UUID uuid = agedEntity.getUniqueID();
	             nbttagcompound.merge(changedEntityData);
	             agedEntity.setUniqueId(uuid);
	             agedEntity.readFromNBT(nbttagcompound);
			 }
			 
			 float scale = JeiRenderHelpers.getScale(originalEntity);
			 int offsetY = JeiRenderHelpers.getOffsetY(originalEntity);
			 
			 float scale2 = JeiRenderHelpers.getScale(agedEntity);
			 int offsetY2 = JeiRenderHelpers.getOffsetY(agedEntity);
			 
			 FontRenderer fontRenderer = minecraft.fontRenderer;

			 String mobName = originalEntity.getName();
			 String agedMobName = agedEntity.getName();
			 
			 fontRenderer.drawString(mobName, 30 - (fontRenderer.getStringWidth(mobName)/2), 90, -1, true);
			 
			 fontRenderer.drawString(agedMobName, 114 - (fontRenderer.getStringWidth(agedMobName)/2), 90, -1, true);
			 
			 String timeNeeded = AgeingHelper.getTiming(tickTime);
			 
			 fontRenderer.drawString(timeNeeded, 72 - (fontRenderer.getStringWidth(timeNeeded)/2), 2, -1, true);
			 
			 String weatherName = "Weather: " + weather;
			 fontRenderer.drawString(weatherName, 72 - (fontRenderer.getStringWidth(weatherName)/2), 16, -1, true);
			 
			 JeiRenderHelpers.renderEntity(
					 28, 70 - offsetY, scale,
					 29 - mouseX,
					 35 - offsetY - mouseY,
					 originalEntity);
			 
			 JeiRenderHelpers.renderEntity(
					 116, 70 - offsetY2, scale2,
					 117 - mouseX,
					 35 - offsetY2 - mouseY,
					 agedEntity);
		 }
	}
	
}
