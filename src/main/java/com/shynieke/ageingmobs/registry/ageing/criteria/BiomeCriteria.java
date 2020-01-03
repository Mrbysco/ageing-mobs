package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class BiomeCriteria extends BaseCriteria {
    private Biome biome;

    public BiomeCriteria(iAgeing ageing, Biome biome) {
        super(ageing);
        this.biome = biome;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        return worldIn.getBiome(entityIn.getPosition()).equals(getBiome());
    }
}
