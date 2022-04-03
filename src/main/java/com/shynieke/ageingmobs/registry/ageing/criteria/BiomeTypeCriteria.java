package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.helper.BiomeHelper;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeTypeCriteria extends BaseCriteria {
	private BiomeDictionary.Type biomeType;

	public BiomeTypeCriteria(iAgeing ageing, BiomeDictionary.Type biomeType) {
		super(ageing);
		this.biomeType = biomeType;
	}

	public BiomeDictionary.Type getBiomeType() {
		return biomeType;
	}

	public void setBiomeType(BiomeDictionary.Type biome) {
		this.biomeType = biome;
	}

	@Override
	public boolean checkCriteria(Level worldIn, Entity entityIn) {
		return BiomeDictionary.getTypes(BiomeHelper.getOrCreateBiomeKey(worldIn.getBiome(entityIn.blockPosition()).value())).contains(getBiomeType());
	}
}
