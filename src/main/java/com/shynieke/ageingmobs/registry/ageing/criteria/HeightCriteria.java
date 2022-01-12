package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class HeightCriteria extends BaseCriteria {
    private int minHeight;
    private int maxHeight;

    public HeightCriteria(iAgeing ageing, int minHeight, int maxHeight) {
        super(ageing);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        int minHeight = getMinHeight();
        int maxHeight = getMaxHeight();
        int entityHeight = entityIn.blockPosition().getY();

        return entityHeight >= minHeight && entityHeight <= maxHeight;
    }
}
