package com.shynieke.ageingmobs.handler;

import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class GamestagesHandler {
	public static boolean gamestageChecks(AgeingData info, Entity entity, Level level) {
//		String gamestage = info.getGamestage();
//		if (net.darkhax.gamestages.GameStageHelper.isStageKnown(gamestage) && !level.isClientSide) {
//			for (final Player player : level.players()) {
//				ServerPlayer serverPlayer = (ServerPlayer) player;
//				if (net.darkhax.gamestages.GameStageHelper.hasStage(serverPlayer, gamestage) &&
//						net.darkhax.bookshelf.api.util.EntityHelper.getDistanceFromEntity(player, entity) <= 32) {
//					return true;
//				}
//			}
//		} else {
//			AgeingMobs.LOGGER.error("Stage with ID: " + info.getName() + " is referring to a gamestage " + gamestage + " which is unknown");
//			AgeingRegistry.INSTANCE.removeAgeing(info);
//		}
//		return false;
		return true;
	}
}
