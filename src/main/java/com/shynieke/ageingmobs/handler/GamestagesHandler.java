package com.shynieke.ageingmobs.handler;

import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class GamestagesHandler {
    public static boolean gamestageChecks(AgeingData info, Entity entity, Level world) {
//        String gamestage = info.getGamestage();
//        if(net.darkhax.gamestages.GameStageHelper.isStageKnown(gamestage) && !world.isClientSide) {
//            for (final Player player : world.players()) {
//                ServerPlayer serverPlayer = (ServerPlayer)player;
//                if (net.darkhax.gamestages.GameStageHelper.hasStage(serverPlayer, gamestage) && net.darkhax.bookshelf.util.EntityUtils.getDistanceFromEntity(player, entity) <= 32) {
//                    return true;
//                }
//            }
//        } else {
//            AgeingMobs.LOGGER.error("Stage with ID: " + info.getName() + " is referring to a gamestage " + gamestage + " which is unknown");
//            AgeingRegistry.INSTANCE.removeAgeing(info);
//        }
        return false;
    }
}
