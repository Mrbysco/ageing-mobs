package com.shynieke.ageingmobs.registry.ageing;

import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;

public class AgeingData implements iAgeing {
    private final String uniqueID;
    private final EntityType<? extends Entity> entity;
    private final CompoundNBT entityData;
    private final EntityType<? extends Entity> evolvedEntity;
    private final CompoundNBT evolvedEntityEntityData;
    private int tickTime;
    private String gamestage = "";
    private BaseCriteria[] criteria = new BaseCriteria[]{};

    public AgeingData(String uniqueID, EntityType<? extends Entity> entity, CompoundNBT entityData, EntityType<? extends Entity> transformedEntity, CompoundNBT evolvedEntityEntityData, int tickTime) {
        this.uniqueID = uniqueID;
        this.entity = entity;
        this.entityData = entityData;
        this.evolvedEntity = transformedEntity;
        this.evolvedEntityEntityData = evolvedEntityEntityData;
        this.tickTime = tickTime;
    }

    @Override
    public EntityType<? extends Entity> getEntity() {
        return this.entity;
    }

    @Override
    public CompoundNBT getEntityData() {
        return this.entityData;
    }

    @Override
    public EntityType<? extends Entity> getTransformedEntity() {
        return this.evolvedEntity;
    }

    @Override
    public CompoundNBT getTransformedEntityData() {
        return this.evolvedEntityEntityData;
    }

    @Override
    public int getAgeingTme() {
        return this.tickTime;
    }

    @Override
    public void setAgeingTme(int time) {
        this.tickTime = time;
    }

    @Override
    public String getName() {
        return this.uniqueID;
    }

    //Optional
    @Override
    public String getGamestage() {
        return this.gamestage;
    }

    @Override
    public void setGamestage(String gamestage) {
        this.gamestage = gamestage;
    }

    @Override
    public void setCriteria(BaseCriteria[] criteria) {
        this.criteria = criteria;
    }

    public AgeingData addCriteria(BaseCriteria[] criteria) {
        this.criteria = criteria;
        return this;
    }

    @Override
    public BaseCriteria[] getCriteria() {
        return this.criteria;
    }

    public AgeingData setAgeingCriteria(BaseCriteria[] ageingCriteria) {
        AgeingData ageingData = new AgeingData(this.uniqueID, this.entity, this.entityData, this.evolvedEntity, this.evolvedEntityEntityData, this.tickTime);
        ageingData.setGamestage(this.gamestage);
        ageingData.setCriteria(ageingCriteria);
        return ageingData;
    }
}
