package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class BaseCriteria implements iCriteria {
    private EntityType<? extends Entity> entity;
    private CompoundTag entityData;
    private EntityType<? extends Entity> evolvedEntity;
    private CompoundTag evolvedEntityEntityData;
    private String uniqueID;

    private iAgeing ageingData;

    public BaseCriteria(iAgeing ageing) {
        this.entity = ageing.getEntity();
        this.entityData = ageing.getEntityData();
        this.evolvedEntity = ageing.getTransformedEntity();
        this.evolvedEntityEntityData = ageing.getTransformedEntityData();
        this.uniqueID = ageing.getName();

        this.ageingData = ageing;
    }

    public EntityType<? extends Entity> getEntity() {
        return this.entity;
    }

    public CompoundTag getEntityData() {
        return this.entityData;
    }

    public EntityType<? extends Entity> getTransformedEntity() {
        return this.evolvedEntity;
    }

    public CompoundTag getTransformedEntityData() {
        return this.evolvedEntityEntityData;
    }

    public String getUniqueID() {
        return this.uniqueID;
    }

    public iAgeing getAgeingData() {
        return this.ageingData;
    }

    @Override
    public boolean checkCriteria(Level worldIn, Entity entityIn) {
        return false;
    }
}
