package com.svennieke.AgeingMobs.compat.ct;

import com.svennieke.AgeingMobs.lists.AgeList;

import crafttweaker.IAction;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class AddBlockAgeingAction implements IAction{
	private String uniqueID;
	private String entity;
	private NBTTagCompound entityData;
	private String transformedEntity;
	private NBTTagCompound changedEntityData;
	private int tickTime;
	private IBlockState state;
	private boolean nearby;
	private int radius;
	
	public AddBlockAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String blockName, int radius, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.nearby = true;
		this.state = Block.getBlockFromName(blockName).getDefaultState();
		this.radius = radius;
	}
	
	public AddBlockAgeingAction(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, String blockName, int tickTime) {
		this.uniqueID = uniqueID;
		this.entity = entity;
		this.entityData = entityData;
		this.transformedEntity = transformedEntity;
		this.changedEntityData = changedEntityData;
		this.tickTime = tickTime;
		this.nearby = false;
		this.state = Block.getBlockFromName(blockName).getDefaultState();
		this.radius = 0;
	}
	
	@Override
	public void apply()
	{
		if(state != null)
			AgeList.addBlockBasedBothAging(uniqueID, entity, entityData, transformedEntity, changedEntityData, state, nearby, radius, tickTime);
	}

	@Override
	public String describe() {
		if(state != null)
			return String.format("%s has been added to the Block ageing list.", new Object[] {this.uniqueID});
		else
			return String.format("%s has an invalid block %s", new Object[] {this.uniqueID, this.state.toString()});
	}
}
