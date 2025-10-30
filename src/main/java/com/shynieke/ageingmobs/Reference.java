package com.shynieke.ageingmobs;

import net.minecraft.resources.ResourceLocation;

public class Reference {
	public static final String MOD_ID = "ageingmobs";
	public static final String MOD_PREFIX = MOD_ID + ":";

	public static ResourceLocation modLoc(String reload) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, reload);
	}
}
