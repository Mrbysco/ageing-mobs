package com.shynieke.ageingmobs.handler;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;

public class GamestagesHandler {
    public static boolean gamestageChecks(AgeingData info, Entity entity, World world) {
        String gamestage = info.getGamestage();
        if(net.darkhax.gamestages.GameStageHelper.isStageKnown(gamestage) && !world.isRemote) {
            for (final PlayerEntity player : world.getPlayers()) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
                if (net.darkhax.gamestages.GameStageHelper.hasStage(serverPlayer, gamestage) && net.darkhax.bookshelf.util.EntityUtils.getDistanceFromEntity(player, entity) <= 32) {
                    return true;
                }
            }
        } else {
            AgeingMobs.LOGGER.error("Stage with ID: " + info.getName() + " is referring to a gamestage " + gamestage + " which is unknown");
            AgeingRegistry.ageingList.remove(info);
        }
        return false;
    }
}
