package com.shynieke.ageingmobs;

import com.shynieke.ageingmobs.config.AgeingConfig;
import com.shynieke.ageingmobs.handler.AgeHandler;
import com.shynieke.ageingmobs.registry.AgeingReloadManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class AgeingMobs {
	public static final Logger LOGGER = LogManager.getLogger();

	public AgeingMobs(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, AgeingConfig.commonSpec);
		eventBus.register(AgeingConfig.class);

		NeoForge.EVENT_BUS.register(new AgeingReloadManager());
		NeoForge.EVENT_BUS.register(new AgeHandler());

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}
}
