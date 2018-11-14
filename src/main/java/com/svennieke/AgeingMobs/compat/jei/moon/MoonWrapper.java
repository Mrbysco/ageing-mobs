package com.svennieke.AgeingMobs.compat.jei.moon;

import java.util.UUID;

import com.svennieke.AgeingMobs.Reference;
import com.svennieke.AgeingMobs.compat.jei.JeiRenderHelpers;
import com.svennieke.AgeingMobs.util.AgeingHelper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class MoonWrapper implements IRecipeWrapper{
	private final ItemStack egg1;
	private final ItemStack egg2;
	private final String entity;
	private final NBTTagCompound entityData;
	private final String evolvedEntity;
	private final NBTTagCompound changedEntityData;
	private final int tickTime;
	private final String moonPhase;
	
	protected final ResourceLocation moonPhases = new ResourceLocation(Reference.MOD_ID, "textures/gui/moon_phases.png");
	 
	public MoonWrapper(ItemStack egg1, ItemStack egg2, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, int tickTime, String moonPhase) {
		this.entity = entity;
		this.entityData = entityData;
		this.evolvedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.moonPhase = moonPhase;
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
			 
			 String moonPhaseName = "Moon Phase: " + moonPhase;
			 fontRenderer.drawString(moonPhaseName, 72 - (fontRenderer.getStringWidth(moonPhaseName)/2), 16, -1, true);
			 
			 CategoryInfo info = phaseToCoords(moonPhase);
			 minecraft.getTextureManager().bindTexture(moonPhases);
	         this.drawTexturedModalRect(67, 38, info.getX(), info.getY(), 8, 8);
			 
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
	
	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x + 0), (double)(y + height), (double)1.0D).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), (double)1.0D).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + 0), (double)1.0D).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + 0), (double)(y + 0), (double)1.0D).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }
	
	public CategoryInfo phaseToCoords(String phase)
	{
		switch (phase) {
		case "Full Moon":
			return new CategoryInfo(12, 12);
		case "Waning Gibbous":
			return new CategoryInfo(44, 12);
		case "Last Quarter":
			return new CategoryInfo(76, 12);
		case "Waning Crescent":
			return new CategoryInfo(108, 12);
		case "New Moon":
			return new CategoryInfo(12, 44);
		case "Waxing Crescent":
			return new CategoryInfo(44, 44);
		case "First Quarter":
			return new CategoryInfo(76, 44);
		case "Waxing Gibbous":
			return new CategoryInfo(108, 44);
		default:
			return new CategoryInfo(12, 12);
		}
	}
	
	public class CategoryInfo
	{
		private int x;
		private int y;
		
		public CategoryInfo(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
}
