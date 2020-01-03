package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class TimeCriteria extends BaseCriteria {
    private int minTime;
    private int maxTime;

    public TimeCriteria(iAgeing ageing, int minTime, int maxTime) {
        super(ageing);
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public int getMinTime() {
        return minTime;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMaxTime() {
        return maxTime;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        long minTime = (long)getMinTime();
        long maxTime = (long)getMaxTime();
        long worldTime = worldIn.getDayTime()%24000;
        return worldTime <= maxTime && worldTime >= minTime;
    }
}
