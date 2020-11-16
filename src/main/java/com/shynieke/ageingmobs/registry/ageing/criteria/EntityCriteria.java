package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityCriteria extends BaseCriteria {
    private EntityType<? extends Entity> nearbyEntity;
    private CompoundNBT nearbyEntityData;
    private int radius;

    public EntityCriteria(iAgeing ageing, EntityType<? extends Entity> nearbyEntity, CompoundNBT nearbyEntityData, int radius) {
        super(ageing);
        this.nearbyEntity = nearbyEntity;
        this.nearbyEntityData = nearbyEntityData;
        this.radius = radius;
    }

    public EntityType<? extends Entity> getNearbyEntity() {
        return nearbyEntity;
    }

    public void setNearbyEntity(EntityType<? extends Entity> nearbyEntity) {
        this.nearbyEntity = nearbyEntity;
    }

    public CompoundNBT getNearbyEntityData() {
        return nearbyEntityData;
    }

    public void setNearbyEntityData(CompoundNBT nearbyEntityData) {
        this.nearbyEntityData = nearbyEntityData;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        BlockPos entityPos = entityIn.getPosition();
        int nearbyEntityAmount = 0;

        AxisAlignedBB areaHitbox = new AxisAlignedBB(entityPos.getX() - 0.5f, entityPos.getY() - 0.5f, entityPos.getZ() - 0.5f, entityPos.getX() + 0.5f, entityPos.getY() + 0.5f, entityPos.getZ() + 0.5f)
                .expand(-getRadius(), -getRadius(), -getRadius()).expand(getRadius(), getRadius(), getRadius());
        if(!worldIn.getEntitiesWithinAABB(Entity.class, areaHitbox).isEmpty()) {
            for(Entity foundEntity: worldIn.getEntitiesWithinAABB(Entity.class, areaHitbox)) {
                if(!(foundEntity instanceof PlayerEntity)) {
                    if(foundEntity.getType().equals(getTransformedEntity())) {
                        if(!getTransformedEntityData().isEmpty()) {
                            CompoundNBT entityTag = AgeingRegistry.entityToNBT(foundEntity);
                            CompoundNBT entityTag2 = getNearbyEntityData();

                            if(!NBTUtil.areNBTEquals(entityTag2, entityTag, true)) {
                                nearbyEntityAmount++;
                            }
                        } else {
                            nearbyEntityAmount++;
                        }
                    }
                }
            }
        }
        return nearbyEntityAmount != 0;
    }
}
