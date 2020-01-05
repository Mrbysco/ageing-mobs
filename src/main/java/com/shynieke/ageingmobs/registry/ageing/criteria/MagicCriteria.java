package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Iterator;

public class MagicCriteria extends BaseCriteria {
    private int range;

    public MagicCriteria(iAgeing ageing, int range) {
        super(ageing);
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        BlockPos entityPos = entityIn.getPosition();
        HashMap<Block, Double> magicMap = AgeingRegistry.INSTANCE.getMagicMap();
        System.out.println(magicMap.size());
        if(!magicMap.isEmpty()) {
            double totalImportance = 0;
            BlockPos downPos = new BlockPos(entityPos.add(-getRange(), -getRange(), -getRange()));
            BlockPos upPos = new BlockPos(entityPos.add(getRange(), getRange(), getRange()));

            Iterator<BlockPos> posIterator = BlockPos.getAllInBox(downPos,upPos).iterator();
            while(posIterator.hasNext()) {
                BlockPos pos = posIterator.next();
                Block foundState = worldIn.getBlockState(pos).getBlock();
                if(magicMap.containsKey(foundState)) {
                    double importanceFound = magicMap.getOrDefault(foundState, 0.0D).doubleValue();
                    totalImportance = totalImportance + importanceFound;
                }
            }

            return totalImportance > 0;
        } else {
            AgeingMobs.LOGGER.error("An error has occured. A criteria is using Magical Blocks but your config has no magical blocks specified.");
            return false;
        }
    }
}
