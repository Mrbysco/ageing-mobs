package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class BiomeTypeCriteria extends BaseCriteria {
	private ResourceLocation biomeTag;

	public BiomeTypeCriteria(iAgeing ageing, ResourceLocation biomeTag) {
		super(ageing);
		this.biomeTag = biomeTag;
	}

	public BiomeTypeCriteria(iAgeing ageing, TagKey<Biome> biomeTag) {
		super(ageing);
		this.biomeTag = biomeTag.location();
	}

	public ResourceLocation getBiomeType() {
		return biomeTag;
	}

	public void setBiomeTag(ResourceLocation biome) {
		this.biomeTag = biome;
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		return level.getBiome(entityIn.blockPosition()).is(TagKey.create(Registry.BIOME_REGISTRY, biomeTag));
	}
}
