package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LiquidBasedCriteria extends BaseCriteria {
    private String liquid;
    private boolean reversible;

    private boolean isReversing;

    public LiquidBasedCriteria(iAgeing ageing, String liquid, Boolean reversible) {
        super(ageing);
        this.liquid = liquid;
        this.reversible = reversible;

        this.isReversing = false;
    }

    public String getLiquid() {
        return liquid;
    }

    public void setLiquid(String liquid) {
        this.liquid = liquid;
    }

    public boolean isReversible() {
        return reversible;
    }

    public void setReversible(boolean reversible) {
        this.reversible = reversible;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        boolean inFluid = entityIn.isOffsetPositionInLiquid(entityIn.posX, entityIn.posY-1, entityIn.posZ);
        if(inFluid) {
            if(worldIn.getFluidState(entityIn.getPosition().add(0,-1,0)).getFluid().getRegistryName().equals(getLiquid())) {
                return true;
            } else {
                if(isReversible()) {
                    this.isReversing = true;
                    return true;
                } else {
                    this.isReversing = false;
                    return false;
                }
            }
        } else {
            if(isReversible()) {
                this.isReversing = true;
                return true;
            } else {
                this.isReversing = false;
                return false;
            }
        }
    }

    @Override
    public boolean isReversing() {
        return isReversing;
    }
}