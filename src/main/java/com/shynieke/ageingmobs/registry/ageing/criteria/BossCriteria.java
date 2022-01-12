package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class BossCriteria extends BaseCriteria {
    private int maxInArea;
    private int checkRadius;
    private boolean isReversing;

    public BossCriteria(iAgeing ageing, int maxInArea, int checkRadius) {
        super(ageing);
        this.maxInArea = maxInArea;
        this.checkRadius = checkRadius;

        this.isReversing = false;
    }

    public int getMaxInArea() {
        return maxInArea;
    }

    public void setMaxInArea(int maxInArea) {
        this.maxInArea = maxInArea;
    }

    public int getCheckRadius() {
        return checkRadius;
    }

    public void setCheckRadius(int checkRadius) {
        this.checkRadius = checkRadius;
    }

    @Override
    public boolean checkCriteria(Level worldIn, Entity entityIn) {
        BlockPos entityPos = entityIn.blockPosition();
        if(getMaxInArea() != 0) {
            int checkRadius = getCheckRadius();
            int bossAmount = 0;

            AABB areaHitbox = new AABB(entityPos.getX() - 0.5f, entityPos.getY() - 0.5f, entityPos.getZ() - 0.5f, entityPos.getX() + 0.5f, entityPos.getY() + 0.5f, entityPos.getZ() + 0.5f)
                    .expandTowards(-checkRadius, -checkRadius, -checkRadius).expandTowards(checkRadius, checkRadius, checkRadius);

            if(!worldIn.getEntitiesOfClass(Entity.class, areaHitbox).isEmpty()) {
                for(Entity foundEntity: worldIn.getEntitiesOfClass(Entity.class, areaHitbox)) {
                    if(!(foundEntity instanceof Player)) {
                        if(foundEntity.getType().equals(getTransformedEntity())) {
                            if(!getTransformedEntityData().isEmpty())
                            {
                                CompoundTag entityTag = AgeingRegistry.entityToNBT(foundEntity);
                                CompoundTag entityTag2 = getTransformedEntityData();

                                if(!NbtUtils.compareNbt(entityTag2, entityTag, true)) {
                                    bossAmount++;
                                }
                            } else {
                                bossAmount++;
                            }
                        }
                    }
                }
            }
            if(bossAmount < getMaxInArea()) {
                this.isReversing = false;
                return true;
            } else {
                this.isReversing = true;
                return false;
            }
        } else {
            this.isReversing = false;
            return true;
        }
    }

    @Override
    public boolean isReversing() {
        return isReversing;
    }
}
