package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

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
	public boolean checkCriteria(Level worldIn, Entity entityIn) {
		int wantedWeather = weatherPhaseFromString(getWeather());

		if (wantedWeather == 0) {
			return !worldIn.isRaining();
		} else if (wantedWeather == 1) {
			return worldIn.isRaining() && !worldIn.isThundering();
		} else if (wantedWeather == 2) {
			return worldIn.isThundering();
		} else {
			AgeingMobs.LOGGER.error("An error has occured. Criteria for ageing ID: {} is using the wrong syntax.", getUniqueID());
			return false;
		}
	}

	public int weatherPhaseFromString(String weatherPhase) {
		return switch (weatherPhase) {
			default -> 0;
			case "rain" -> 1;
			case "thunder" -> 2;
		};
	}
}
