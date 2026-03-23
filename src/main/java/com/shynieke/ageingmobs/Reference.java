package com.shynieke.ageingmobs;

import net.minecraft.resources.Identifier;

public class Reference {
	public static final String MOD_ID = "ageingmobs";
	public static final String MOD_PREFIX = MOD_ID + ":";

	public static Identifier modLoc(String reload) {
		return Identifier.fromNamespaceAndPath(MOD_ID, reload);
	}
}
