package com.shynieke.ageingmobs.init;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.Reference;
import com.shynieke.ageingmobs.config.AgeingMobsConfigGen;
import com.shynieke.ageingmobs.endermite.TileEndermite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTiles {
	public static void register() { 
        if(AgeingMobsConfigGen.general.endermiteshulker.endermiteToShulkerAgeing)
        {
        	AgeingMobs.logger.info("Registering tileentity");
        	registerTileEntity(TileEndermite.class, "_EndermiteEgg");
        }
	}
	
	public static void registerTileEntity(Class<? extends TileEntity> tileentityClass, String tilename) {
		GameRegistry.registerTileEntity(tileentityClass, new ResourceLocation(Reference.MOD_ID, tilename));
	}
}
