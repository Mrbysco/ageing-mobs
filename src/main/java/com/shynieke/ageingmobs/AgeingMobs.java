package com.shynieke.ageingmobs;

import com.shynieke.ageingmobs.config.AgeingConfig;
import com.shynieke.ageingmobs.handler.AgeHandler;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class AgeingMobs {
    public static final Logger LOGGER = LogManager.getLogger();

    public AgeingMobs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, AgeingConfig.serverSpec);
        FMLJavaModLoadingContext.get().getModEventBus().register(AgeingConfig.class);

        MinecraftForge.EVENT_BUS.register(new AgeHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void serverStart(FMLServerStartingEvent event) {
        AgeingRegistry.INSTANCE.initializeAgeing();
        AgeingRegistry.INSTANCE.initializeMagicMap();
        AgeingRegistry.INSTANCE.initializeMoonDimensions();
    }
}
