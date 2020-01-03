package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.google.common.collect.Lists;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.List;

public class DimensionCriteria extends BaseCriteria {
    private List<Integer> dimensionID;

    public DimensionCriteria(iAgeing ageing, Integer[] dimensionID) {
        super(ageing);
        this.dimensionID = Lists.newArrayList(dimensionID);
    }

    public List<Integer> getDimensionID() {
        return dimensionID;
    }

    public void setDimensionID(List<Integer> dimensionID) {
        this.dimensionID = dimensionID;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        return getDimensionID().contains(entityIn.dimension.getId());
    }
}
