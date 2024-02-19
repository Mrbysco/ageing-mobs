package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class BiomeCriteria extends BaseCriteria {
	private ResourceKey<Biome> biome;

	public BiomeCriteria(iAgeing ageing, @NotNull ResourceKey<Biome> biome) {
		super(ageing);
		this.biome = biome;
	}

	public ResourceKey<Biome> getBiome() {
		return biome;
	}

	public void setBiome(@NotNull ResourceKey<Biome> biome) {
		this.biome = biome;
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		var biomeKey = level.getBiome(entityIn.blockPosition()).unwrapKey();
		return biomeKey.map(biomeResourceKey -> biomeResourceKey.equals(getBiome())).orElse(false);
	}
}
