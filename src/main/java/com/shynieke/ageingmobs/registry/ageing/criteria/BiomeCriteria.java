package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nonnull;

public class BiomeCriteria extends BaseCriteria {
	private Biome biome;

	public BiomeCriteria(iAgeing ageing, @Nonnull Biome biome) {
		super(ageing);
		this.biome = biome;
	}

	public Biome getBiome() {
		return biome;
	}

	public void setBiome(@Nonnull Biome biome) {
		this.biome = biome;
	}

	@Override
	public boolean checkCriteria(Level worldIn, Entity entityIn) {
		return worldIn.getBiome(entityIn.blockPosition()).value().getRegistryName().equals(getBiome().getRegistryName());
	}
}
