package com.shynieke.ageingmobs.helper;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeHelper {
	public static Biome getBiome(ResourceKey<Biome> key) {
		Biome biome = ForgeRegistries.BIOMES.getValue(key.location());
		if (biome == null) throw new RuntimeException("Attempted to get unregistered biome " + key);
		return biome;
	}
}
