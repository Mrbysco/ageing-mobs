package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

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
	public boolean checkCriteria(Level worldIn, Entity entityIn) {
		Level entityWorld = entityIn.getCommandSenderWorld();
		if (!entityWorld.isDay()) {
			int moonPhase = entityWorld.dimensionType().moonPhase(entityWorld.getLevelData().getDayTime());
			List<ResourceLocation> moonDimensions = AgeingRegistry.INSTANCE.getMoonDimensions();

			if (!moonDimensions.isEmpty() && moonDimensions.contains(entityWorld.dimension().location())) {
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
		return switch (moonPhase) {
			default -> 0;
			case "Waning Gibbous" -> 1;
			case "Last Quarter" -> 2;
			case "Waning Crescent" -> 3;
			case "New Moon" -> 4;
			case "Waxing Crescent" -> 5;
			case "First Quarter" -> 6;
			case "Waxing Gibbous" -> 7;
		};
	}
}
