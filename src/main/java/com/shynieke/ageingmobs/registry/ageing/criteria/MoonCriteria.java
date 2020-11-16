package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

public class MoonCriteria extends BaseCriteria {
    private String moonPhase;

    public MoonCriteria(iAgeing ageing, String moonPhase) {
        super(ageing);
        this.moonPhase = moonPhase;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(String time) {
        this.moonPhase = time;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        World entityWorld = entityIn.getEntityWorld();
        if(!entityWorld.isDaytime()) {
            int moonPhase = entityWorld.getDimensionType().getMoonPhase(entityWorld.getWorldInfo().getDayTime());
            List<ResourceLocation> moonDimensions = AgeingRegistry.INSTANCE.getMoonDimensions();

            if(!moonDimensions.isEmpty() && moonDimensions.contains(entityWorld.getDimensionKey().getLocation())) {
                int wantedPhase = moonPhaseFromString(getMoonPhase());

                return moonPhase == wantedPhase;
            } else {
                AgeingMobs.LOGGER.error("An error has occured. A criteria is using a moon phase but your config has no moon dimensions.");
                return false;
            }
        } else {
            return false;
        }
    }

    public int moonPhaseFromString(String moonPhase) {
        switch (moonPhase) {
            default:
                return 0;
            case "Waning Gibbous":
                return 1;
            case "Last Quarter":
                return 2;
            case "Waning Crescent":
                return 3;
            case "New Moon":
                return 4;
            case "Waxing Crescent":
                return 5;
            case "First Quarter":
                return 6;
            case "Waxing Gibbous":
                return 7;
        }
    }
}
