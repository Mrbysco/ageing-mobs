package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
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

    public void setBiomeType(Biome biome) {
        this.biomeType = biomeType;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        return BiomeDictionary.getTypes(worldIn.getBiome(entityIn.getPosition())).contains(getBiomeType());
    }
}
