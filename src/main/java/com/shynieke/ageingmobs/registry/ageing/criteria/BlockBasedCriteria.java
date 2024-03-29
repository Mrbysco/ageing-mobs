package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.google.common.collect.Lists;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

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
	public boolean checkCriteria(Level level, Entity entityIn) {
		BlockPos entityPos = entityIn.blockPosition();
		if (isNearBlock()) {
			int radius = getRadius();

			Iterator<BlockPos> posIterator = BlockPos.betweenClosedStream(entityPos.offset(-radius, -radius, -radius), entityPos.offset(radius, radius, radius)).iterator();
			while (posIterator.hasNext()) {
				BlockPos pos = posIterator.next();
				if (getBlock().contains(level.getBlockState(pos).getBlock())) {
					return true;
				}
			}
			return false;
		} else {
			return getBlock().contains(level.getBlockState(entityPos.below()).getBlock());
		}
	}
}
