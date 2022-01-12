package com.shynieke.ageingmobs.registry;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AgeingReloadManager implements ResourceManagerReloadListener {
	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		AgeingRegistry.INSTANCE.initializeAgeing();
		AgeingRegistry.INSTANCE.initializeMagicMap();
		AgeingRegistry.INSTANCE.initializeMoonDimensions();
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAddReloadListeners(AddReloadListenerEvent event) {
		event.addListener(this);
	}
}
