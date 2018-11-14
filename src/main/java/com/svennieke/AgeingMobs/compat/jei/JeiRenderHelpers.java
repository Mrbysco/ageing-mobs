package com.svennieke.AgeingMobs.compat.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;

public class JeiRenderHelpers {
	public static int getOffsetY(Entity entity) {
        int offsetY = 0;
        if (entity instanceof EntitySquid) offsetY = 20;
        else if (entity instanceof EntityWitch) offsetY = -10;
        else if (entity instanceof EntityGhast) offsetY = 15;
        else if (entity instanceof EntityWither) offsetY = -15;
        else if (entity instanceof EntityDragon) offsetY = 15;
        else if (entity instanceof EntityEnderman) offsetY = -10;
        else if (entity instanceof EntityGolem) offsetY = -10;
        else if (entity instanceof EntityRabbit) offsetY = -10;
        else if (entity instanceof EntityAnimal) offsetY = -10;
        else if (entity instanceof EntityVillager) offsetY = -15;
        else if (entity instanceof EntityVindicator) offsetY = -15;
        else if (entity instanceof EntityEvoker) offsetY = -15;
        else if (entity instanceof EntityEvoker) offsetY = -10;
        else if (entity instanceof EntityBlaze) offsetY = -10;
        else if (entity instanceof EntityCreeper) offsetY = -8;
        else if (entity instanceof EntityWitherSkeleton) offsetY = -5;
        else if (entity instanceof EntityVex) offsetY = -10;
        else if (entity instanceof EntityBat) offsetY = -15;
        else if (entity instanceof EntityElderGuardian) offsetY = -10;
        else if (entity instanceof EntityGuardian) offsetY = -5;
        else if (entity instanceof EntityZombie) offsetY = ((EntityZombie)entity).isChild() ? -9 : offsetY;

        return offsetY;
	}
	
	public static float getScale(Entity entity) {
		float scale = 9.0F;
		
        float width = entity.width;
        float height = entity.height;
        if (width <= height) {
            if (height < 0.9) scale = 36.0F;
            else if (height < 1) scale = 35.0F;
            else if (height < 1.8) scale = 33.0F;
            else if (height < 2) scale = 32.0F;
            else if (height < 3) scale = 24.0F;
            else if (height < 4) scale = 20.0F;
            else scale = 10.0F;
        } else {
            if (width < 1) scale = 28.0F;
            else if (width < 2) scale = 17.0F;
            else if (width < 3) scale = 3.0F;
            else scale = 1.0F;
        }
        
        
        if(entity instanceof EntityElderGuardian)
		{
			scale = 24.0F;
		}
		if(entity instanceof EntityGuardian && !(entity instanceof EntityElderGuardian))
		{
			scale = 34.0F;
		}
		
        
        return scale - 10F;
	}
	
	public static void renderEntity(int x, int y, float scale, float yaw, float pitch, Entity entity) {
		if (entity.world == null) entity.world = Minecraft.getMinecraft().world;
		
		EntityLivingBase entityLivingBase = null;
		if(entity instanceof EntityLivingBase)
		{
			entityLivingBase = (EntityLivingBase)entity;
			
	        GlStateManager.enableColorMaterial();
	        GlStateManager.pushMatrix();
	        GlStateManager.translate(x, y, 50.0F);
	        GlStateManager.scale(-scale, scale, scale);
	        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
	        float renderYawOffset = entityLivingBase.renderYawOffset;
	        float rotationYaw = entityLivingBase.rotationYaw;
	        float rotationPitch = entityLivingBase.rotationPitch;
	        float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
	        float rotationYawHead = entityLivingBase.rotationYawHead;
	        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
	        
	        GlStateManager.rotate(-((float) Math.atan((double) (pitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
	        entityLivingBase.renderYawOffset = (float) Math.atan((double) (yaw / 40.0F)) * 20.0F;
	        entityLivingBase.rotationYaw = (float) Math.atan((double) (yaw / 40.0F)) * 40.0F;
	        entityLivingBase.rotationPitch = -((float) Math.atan((double) (pitch / 40.0F))) * 20.0F;
	        entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
	        entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
	        GlStateManager.translate(0.0F, entityLivingBase.getYOffset(), 0.0F);
	        getRenderManager().setPlayerViewY(180.0F);
	        getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
	        entityLivingBase.renderYawOffset = renderYawOffset;
	        entityLivingBase.rotationYaw = rotationYaw;
	        entityLivingBase.rotationPitch = rotationPitch;
	        entityLivingBase.prevRotationYawHead = prevRotationYawHead;
	        entityLivingBase.rotationYawHead = rotationYawHead;
	        GlStateManager.popMatrix();
	        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
	        GlStateManager.disableRescaleNormal();
	        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.disableTexture2D();
	        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		}
		else
		{
	        GlStateManager.enableColorMaterial();
	        GlStateManager.pushMatrix();
	        GlStateManager.translate(x, y, 50.0F);
	        GlStateManager.scale(-scale, scale, scale);
	        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
	        float rotationYaw = entity.rotationYaw;
	        float rotationPitch = entity.rotationPitch;
	        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
	        
	        GlStateManager.rotate(-((float) Math.atan((double) (pitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
	        entity.rotationYaw = (float) Math.atan((double) (yaw / 40.0F)) * 40.0F;
	        entity.rotationPitch = -((float) Math.atan((double) (pitch / 40.0F))) * 20.0F;
	        GlStateManager.translate(0.0F, entity.getYOffset(), 0.0F);
	        getRenderManager().setPlayerViewY(180.0F);
	        getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
	        entity.rotationYaw = rotationYaw;
	        entity.rotationPitch = rotationPitch;
	        GlStateManager.popMatrix();
	        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
	        GlStateManager.disableRescaleNormal();
	        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.disableTexture2D();
	        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		}
        
	}
	
	private static RenderManager getRenderManager() {
        return Minecraft.getMinecraft().getRenderManager();
	}
}
