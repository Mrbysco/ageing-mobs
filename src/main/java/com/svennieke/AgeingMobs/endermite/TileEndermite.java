package com.svennieke.AgeingMobs.endermite;

import com.svennieke.AgeingMobs.config.AgeingMobsConfigGen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEndermite extends TileEntity implements ITickable{
	public int timer;
	public int timerMax;
	
	public TileEndermite() {
		this.timer = 0;
		this.timerMax = (AgeingMobsConfigGen.general.endermiteshulker.endermiteToShulkerBlockTime * 20);
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public int getTimer() {
		return this.timer;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        timer = compound.getInteger("timer");
        timerMax = compound.getInteger("timerMax");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("timer", timer);
        compound.setInteger("timerMax", timerMax);
        return compound;
    }
    
	@Override
	public void update() {
		++this.timer;
		markDirty();
		
		if(this.timerMax == 0)
    		this.timerMax = (AgeingMobsConfigGen.general.endermiteshulker.endermiteToShulkerBlockTime * 20);
		
        if(this.timer >= this.timerMax){
        	
            this.timer = 0;
            ShulkerIfy();
        }
	}
	
	public void ShulkerIfy()
	{
		if(!world.isRemote)
		{
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			EntityShulker shulker = new EntityShulker(world);
			shulker.setPosition(pos.getX(), pos.getY(), pos.getZ());
			
			world.spawnEntity(shulker);
		}
	}
	
	@Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	readFromNBT(pkt.getNbtCompound());
    	
    	IBlockState state = world.getBlockState(getPos());
    	world.notifyBlockUpdate(getPos(), state, state, 3);
    }
        
    @Override
	public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }
    
    @Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, this.getUpdateTag());
    }
}
