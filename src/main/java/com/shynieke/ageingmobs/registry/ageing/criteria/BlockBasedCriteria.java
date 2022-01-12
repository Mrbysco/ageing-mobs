package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.google.common.collect.Lists;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class BlockBasedCriteria extends BaseCriteria {
    private List<Block> block;
    private boolean nearBlock;
    private int radius;

    public BlockBasedCriteria(iAgeing ageing, Block[] block, Boolean nearBlock, int radius) {
        super(ageing);
        this.block = Lists.newArrayList(block);
        this.nearBlock = nearBlock;
        this.radius = radius;
    }

    public List<Block> getBlock() {
        return block;
    }

    public void setBlock(List<Block> block) {
        this.block = block;
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

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        BlockPos entityPos = entityIn.blockPosition();
        if(isNearBlock()) {
            int radius = getRadius();

            Iterator<BlockPos> posIterator = BlockPos.betweenClosedStream(entityPos.offset(-radius, -radius, -radius), entityPos.offset(radius, radius, radius)).iterator();
            while(posIterator.hasNext()) {
                BlockPos pos = posIterator.next();
                if(getBlock().contains(worldIn.getBlockState(pos).getBlock())) {
                    return true;
                }
            }
            return false;
        } else {
            return getBlock().contains(worldIn.getBlockState(entityPos.below()).getBlock());
        }
    }
}
