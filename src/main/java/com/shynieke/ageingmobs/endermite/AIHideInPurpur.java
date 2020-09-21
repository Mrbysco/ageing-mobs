package com.shynieke.ageingmobs.endermite;

import com.shynieke.ageingmobs.block.BlockEndermite;
import com.shynieke.ageingmobs.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class AIHideInPurpur extends EntityAIWander{
    private EnumFacing facing;
    private boolean doMerge;

    public AIHideInPurpur(EntityEndermite endermiteIn)
    {
        super(endermiteIn, 1.0D, 10);
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.entity.getAttackTarget() != null)
        {
            return false;
        }
        else if (!this.entity.getNavigator().noPath())
        {
            return false;
        }
        else
        {
            Random random = this.entity.getRNG();

            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entity.world, this.entity) && random.nextInt(10) == 0)
            {
                this.facing = EnumFacing.random(random);
                BlockPos blockpos = (new BlockPos(this.entity.posX, this.entity.posY + 0.5D, this.entity.posZ)).offset(this.facing);
                IBlockState iblockstate = this.entity.world.getBlockState(blockpos);

                if (BlockEndermite.canContainEndermite(iblockstate))
                {
                    this.doMerge = true;
                    return true;
                }
            }

            this.doMerge = false;
            return super.shouldExecute();
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.doMerge ? false : super.shouldContinueExecuting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        if (!this.doMerge)
        {
            super.startExecuting();
        }
        else
        {
            World world = this.entity.world;
            BlockPos blockpos = (new BlockPos(this.entity.posX, this.entity.posY + 0.5D, this.entity.posZ)).offset(this.facing);
            IBlockState iblockstate = world.getBlockState(blockpos);

            if (BlockEndermite.canContainEndermite(iblockstate))
            {
                world.setBlockState(blockpos, getProperEggBlock(iblockstate), 3);
                this.entity.spawnExplosionParticle();
                this.entity.setDead();
            }
        }
    }
    
    public IBlockState getProperEggBlock(IBlockState state)
    {
    	if(state.getBlock() == Blocks.PURPUR_BLOCK)
    		return ModBlocks.PURPUR_BLOCK_EGG.getDefaultState();
    	else if(state.getBlock() == Blocks.PURPUR_PILLAR)
    		return ModBlocks.PURPUR_PILLAR_EGG.getDefaultState();
    	else if(state.getBlock() == Blocks.PURPUR_DOUBLE_SLAB)
    		return ModBlocks.PURPUR_DOUBLESLAB_EGG.getDefaultState();
    	else
    		return ModBlocks.PURPUR_BLOCK_EGG.getDefaultState();
    }
}
