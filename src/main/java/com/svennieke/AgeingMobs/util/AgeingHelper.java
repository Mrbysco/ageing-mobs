package com.svennieke.AgeingMobs.util;

import net.minecraft.util.ResourceLocation;

public class AgeingHelper {
	public static ResourceLocation getEntityLocation(String name)
	{
		String[] splitResource = name.split(":");
		if (splitResource.length != 2)
			return null;
		else
			return new ResourceLocation(splitResource[0], splitResource[1]);
	}
}
