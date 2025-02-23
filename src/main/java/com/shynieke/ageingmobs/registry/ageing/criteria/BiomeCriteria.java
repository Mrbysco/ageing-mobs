package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Criteria that checks if the entity is in any of the specified biomes.
 */
public class BiomeCriteria extends BaseCriteria {
	@Nonnull
	private List<ResourceLocation> biomes;

	public BiomeCriteria(iAgeing ageing, @Nonnull ResourceLocation biome) {
		super(ageing);
		this.biomes = Collections.singletonList(biome);
	}

	public BiomeCriteria(iAgeing ageing, @Nonnull List<ResourceLocation> biomes) {
		super(ageing);
		this.biomes = List.copyOf(biomes);
	}

	public ResourceLocation getBiome(int index) {
		return biomes.get(index);
	}

	public void setBiome(int index, @Nonnull ResourceLocation biome) {
		this.biomes.set(index, biome);
	}

	public List<ResourceLocation> getBiomes() {
		return biomes;
	}

	public void setBiomes(List<ResourceLocation> biomes) {
		this.biomes = biomes;
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		var biomeKey = level.getBiome(entityIn.blockPosition()).unwrapKey().orElse(null);
		if (biomeKey == null) {
			return false;
		}
		return this.biomes.contains(biomeKey.location());
	}
}
