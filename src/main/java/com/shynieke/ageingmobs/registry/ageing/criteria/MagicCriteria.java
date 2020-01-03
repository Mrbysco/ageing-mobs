package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
        if(!magicMap.isEmpty()) {
            HashMap<Block, Double> importanceList = new HashMap<>();
            double totalImportance = 0;

            Iterator<BlockPos> posIterator = BlockPos.getAllInBox(entityPos.add(-getRange(), -getRange(), -getRange()), entityPos.add(getRange(), getRange(), getRange())).iterator();
            while(posIterator.hasNext()) {
                BlockPos pos = posIterator.next();
                BlockState foundState = worldIn.getBlockState(pos);
                if(magicMap.containsKey(foundState)) {
                    double importanceFound = importanceList.get(foundState);
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
