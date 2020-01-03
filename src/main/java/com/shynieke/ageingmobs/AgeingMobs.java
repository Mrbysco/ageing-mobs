package com.shynieke.ageingmobs;

import com.shynieke.ageingmobs.config.AgeingConfig;
import com.shynieke.ageingmobs.handler.AgeHandler;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class AgeingMobs {
    public static final Logger LOGGER = LogManager.getLogger();

    public AgeingMobs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, AgeingConfig.serverSpec);
        FMLJavaModLoadingContext.get().getModEventBus().register(AgeingConfig.class);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(new AgeHandler());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        AgeingRegistry.initializeAgeing();
    }
}
