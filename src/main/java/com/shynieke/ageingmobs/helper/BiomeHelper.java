package com.shynieke.ageingmobs.helper;

import javax.annotation.Nonnull;

public class BiomeHelper {
	public static net.minecraft.world.level.biome.Biome getBiome(net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome> key) {
		net.minecraft.world.level.biome.Biome biome = net.minecraftforge.registries.ForgeRegistries.BIOMES.getValue(key.location());
		if (biome == null) throw new RuntimeException("Attempted to get unregistered biome " + key);
		return biome;
	}

	public static net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome> getOrCreateBiomeKey(@Nonnull net.minecraft.world.level.biome.Biome biome) {
		return net.minecraft.resources.ResourceKey.create(net.minecraft.core.Registry.BIOME_REGISTRY,
				biome.getRegistryName() != null ? biome.getRegistryName() : new net.minecraft.resources.ResourceLocation("the_void"));
	}
}
