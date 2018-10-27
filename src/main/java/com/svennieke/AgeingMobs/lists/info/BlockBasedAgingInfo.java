package com.svennieke.AgeingMobs.lists.info;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class BlockBasedAgingInfo extends RegularAgingInfo{
	private IBlockState state;
	private boolean nearBlock;
	private int radius;
	
	public BlockBasedAgingInfo(String uniqueID, String entity, NBTTagCompound entityData, String transformedEntity, NBTTagCompound changedEntityData, IBlockState block, Boolean nearBlock, int radius, int tickTime) {
		super(uniqueID, entity, entityData, transformedEntity, changedEntityData, tickTime);
		this.state = block;
		this.nearBlock = nearBlock;
		this.radius = radius;
	}
	
	public IBlockState getState() {
		return state;
	}
	
	public void setState(IBlockState state) {
		this.state = state;
	}
	
	public boolean isNearBlock() {
		return nearBlock;
	}
	
	public void setNearBlock(boolean nearBlock) {
		this.nearBlock = nearBlock;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
}
