package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class BiomeCriteria extends BaseCriteria {
	private ResourceLocation biome;

	public BiomeCriteria(iAgeing ageing, @Nonnull ResourceLocation biome) {
		super(ageing);
		this.biome = biome;
	}

	public ResourceLocation getBiome() {
		return biome;
	}

	public void setBiome(@Nonnull ResourceLocation biome) {
		this.biome = biome;
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		var biomeKey = level.getBiome(entityIn.blockPosition()).unwrapKey().orElse(null);
		if (biomeKey == null) {
			return false;
		}
		return biomeKey.location().equals(this.biome);
	}
}
