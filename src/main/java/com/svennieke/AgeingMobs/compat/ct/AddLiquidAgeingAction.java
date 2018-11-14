package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

import crafttweaker.IAction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;

public class AddLiquidAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private String liquid;
	private boolean reversible;
	private String gamestage;
	
	public AddLiquidAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String liquid, boolean reversible, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.liquid = liquid;
		this.reversible = reversible;
	}
	
	public AddLiquidAgeingAction(String uniqueID, String gamestage, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String liquid, boolean reversible, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.liquid = liquid;
		this.reversible = reversible;
		this.gamestage = gamestage;
	}
	
	@Override
	public void apply()
	{
		if(FluidRegistry.isFluidRegistered(liquid))
		{
			if(gamestage != null && !gamestage.isEmpty())
				AgeList.addStagedLiquidBasedAgeing(uniqueID, gamestage, entity, entityData, transformedEntity, changedEntityData, liquid, reversible, tickTime);
			else
				AgeList.addLiquidBasedAgeing(uniqueID, entity, entityData, transformedEntity, changedEntityData, liquid, reversible, tickTime);
		}
	}

	@Override
	public String describe() {
		if(FluidRegistry.isFluidRegistered(liquid))
		{
			if(gamestage != null && !gamestage.isEmpty())
				return String.format("%s has been added to the staged Liquid ageing list.", new Object[] {this.uniqueID});	
			else
				return String.format("%s has been added to the Liquid ageing list.", new Object[] {this.uniqueID});
		}
		else
		{
			return String.format("%s used a liquid that we don't know about %s.", new Object[] {this.uniqueID, this.liquid});
		}
	}
}
