package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class WeatherCriteria extends BaseCriteria {
    private String weather;

    public WeatherCriteria(iAgeing ageing, String weather) {
        super(ageing);
        this.weather = weather;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        int wantedWeather = weatherPhaseFromString(getWeather());

        if(wantedWeather == 0)
        {
            return !worldIn.isRaining();
        }
        else if(wantedWeather == 1)
        {
            return worldIn.isRaining() && !worldIn.isThundering();
        }
        else if(wantedWeather == 2)
        {
            return worldIn.isThundering();
        }
        else
        {
            AgeingMobs.LOGGER.error("An error has occured. Criteria for ageing ID: %s is using the wrong syntax.", new Object[] { getUniqueID() });
            return false;
        }
    }

    public int weatherPhaseFromString(String weatherPhase)
    {
        switch (weatherPhase) {
            default:
                return 0;
            case "rain":
                return 1;
            case "thunder":
                return 2;
        }
    }
}
