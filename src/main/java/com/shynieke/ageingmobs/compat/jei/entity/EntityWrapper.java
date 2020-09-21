package com.shynieke.ageingmobs.compat.jei.entity;

import com.shynieke.ageingmobs.compat.jei.JeiRenderHelpers;
import com.shynieke.ageingmobs.util.AgeingHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.UUID;

public class EntityWrapper implements IRecipeWrapper{
	private final ItemStack egg1;
	private final ItemStack egg2;
	private final ItemStack egg3;
	private final String entity;
	private final NBTTagCompound entityData;
	private final String evolvedEntity;
	private final NBTTagCompound changedEntityData;
	private final String nearbyEntity;
	private final NBTTagCompound nearbyEntityData;
	private final int tickTime;
	
	public EntityWrapper(ItemStack egg1, ItemStack egg2, ItemStack egg3, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime, String nearbyEntity, NBTTagCompound nearbyEntityData) {
		this.entity = entity;
		this.entityData = entityData;
		this.evolvedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.egg1 = egg1;
		this.egg2 = egg2;
		this.egg3 = egg3;
		this.nearbyEntity = nearbyEntity;
		this.nearbyEntityData = nearbyEntityData;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<ItemStack> inputStacks = new ArrayList<>();
		inputStacks.add(egg1);
		inputStacks.add(egg3);
		ingredients.setInputs(ItemStack.class, inputStacks);
		ingredients.setOutput(ItemStack.class, egg2);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		 Entity originalEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(entity), minecraft.world);
		 Entity agedEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(evolvedEntity), minecraft.world);
		 Entity neededNearbyEntity = EntityList.createEntityByIDFromName(AgeingHelper.getEntityLocation(nearbyEntity), minecraft.world);
		 
		 if(originalEntity != null && agedEntity != null && neededNearbyEntity != null)
		 {
			 if(!entityData.isEmpty())
			 {
				 NBTTagCompound nbttagcompound = AgeingHelper.entityToNBT(originalEntity);
	             NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
				 
	             UUID uuid = originalEntity.getUniqueID();
	             nbttagcompound.merge(entityData);
	             originalEntity.setUniqueId(uuid);
	             originalEntity.readFromNBT(nbttagcompound);
			 }
			 
			 if(!changedEntityData.isEmpty())
			 {
				 NBTTagCompound nbttagcompound = AgeingHelper.entityToNBT(agedEntity);
	             NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
				 
	             UUID uuid = agedEntity.getUniqueID();
	             nbttagcompound.merge(changedEntityData);
	             agedEntity.setUniqueId(uuid);
	             agedEntity.readFromNBT(nbttagcompound);
			 }
			 
			 if(!changedEntityData.isEmpty())
			 {
				 NBTTagCompound nbttagcompound = AgeingHelper.entityToNBT(neededNearbyEntity);
				 NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
				 
				 UUID uuid = agedEntity.getUniqueID();
				 nbttagcompound.merge(nearbyEntityData);
				 neededNearbyEntity.setUniqueId(uuid);
				 neededNearbyEntity.readFromNBT(nbttagcompound);
			 }
			 
			 float scale = JeiRenderHelpers.getScale(originalEntity);
			 int offsetY = JeiRenderHelpers.getOffsetY(originalEntity);
			 
			 float scale2 = JeiRenderHelpers.getScale(agedEntity);
			 int offsetY2 = JeiRenderHelpers.getOffsetY(agedEntity);

			 float scale3 = JeiRenderHelpers.getScale(neededNearbyEntity)*0.72F;
			 int offsetY3 = Math.round((float)JeiRenderHelpers.getOffsetY(neededNearbyEntity) * 0.72F);
			 
			 FontRenderer fontRenderer = minecraft.fontRenderer;

			 String mobName = originalEntity.getName();
			 String agedMobName = agedEntity.getName();
			 
			 fontRenderer.drawString(mobName, 30 - (fontRenderer.getStringWidth(mobName)/2), 90, -1, true);
			 
			 fontRenderer.drawString(agedMobName, 114 - (fontRenderer.getStringWidth(agedMobName)/2), 90, -1, true);
			 
			 String timeNeeded = AgeingHelper.getTiming(tickTime);
			 
			 fontRenderer.drawString(timeNeeded, 72 - (fontRenderer.getStringWidth(timeNeeded)/2), 2, -1, true);
			 
			 String nearby = "Near";
			 
			 fontRenderer.drawString(nearby, 72 - (fontRenderer.getStringWidth(nearby)/2), 76, -1, true);

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
			 
			 JeiRenderHelpers.renderEntity(
					 71, 66 - offsetY3, scale3,
					 72 - mouseX,
					 31 - offsetY3 - mouseY,
					 neededNearbyEntity);
		 }
	}
}
