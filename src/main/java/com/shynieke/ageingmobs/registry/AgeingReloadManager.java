package com.shynieke.ageingmobs.registry;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class AgeingReloadManager implements ResourceManagerReloadListener {
	@Override
	public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
		AgeingRegistry.INSTANCE.initializeAgeing();
		AgeingRegistry.INSTANCE.initializeMagicMap();
		AgeingRegistry.INSTANCE.initializeMoonDimensions();
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAddReloadListeners(AddReloadListenerEvent event) {
		event.addListener(this);
	}
}
