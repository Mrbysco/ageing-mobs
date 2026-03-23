package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.resources.Identifier;
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
	private List<Identifier> biomes;

	public BiomeCriteria(iAgeing ageing, @Nonnull Identifier biome) {
		super(ageing);
		this.biomes = Collections.singletonList(biome);
	}

	public BiomeCriteria(iAgeing ageing, @Nonnull List<Identifier> biomes) {
		super(ageing);
		this.biomes = List.copyOf(biomes);
	}

	public Identifier getBiome(int index) {
		return biomes.get(index);
	}

	public void setBiome(int index, @Nonnull Identifier biome) {
		this.biomes.set(index, biome);
	}

	public List<Identifier> getBiomes() {
		return biomes;
	}

	public void setBiomes(List<Identifier> biomes) {
		this.biomes = biomes;
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		var biomeKey = level.getBiome(entityIn.blockPosition()).unwrapKey().orElse(null);
		if (biomeKey == null) {
			return false;
		}
		return this.biomes.contains(biomeKey.identifier());
	}
}
