package com.shynieke.ageingmobs.helper;

import javax.annotation.Nonnull;

public class BiomeHelper {
    public static net.minecraft.world.biome.Biome getBiome(net.minecraft.util.RegistryKey<net.minecraft.world.biome.Biome> key) {
        net.minecraft.world.biome.Biome biome = net.minecraftforge.registries.ForgeRegistries.BIOMES.getValue(key.getLocation());
        if (biome == null) throw new RuntimeException("Attempted to get unregistered biome " + key);
        return biome;
    }

    public static net.minecraft.util.RegistryKey<net.minecraft.world.biome.Biome> getOrCreateBiomeKey(@Nonnull net.minecraft.world.biome.Biome biome) {
        return net.minecraft.util.RegistryKey.getOrCreateKey(net.minecraft.util.registry.Registry.BIOME_KEY,
                biome.getRegistryName() != null ? biome.getRegistryName() : new net.minecraft.util.ResourceLocation("the_void"));
    }
}
