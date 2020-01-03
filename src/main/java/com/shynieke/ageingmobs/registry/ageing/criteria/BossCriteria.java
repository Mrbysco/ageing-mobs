package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class BossCriteria extends BaseCriteria {
    private int maxInArea;
    private int checkRadius;

    public BossCriteria(iAgeing ageing, int maxInArea, int checkRadius) {
        super(ageing);
        this.maxInArea = maxInArea;
        this.checkRadius = checkRadius;
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
    public boolean checkCriteria(World worldIn, Entity entityIn) {
        BlockPos entityPos = entityIn.getPosition();
        if(getMaxInArea() != 0)
        {
            int checkRadius = getCheckRadius();
            int bossAmount = 0;

            AxisAlignedBB areaHitbox = new AxisAlignedBB(entityPos.getX() - 0.5f, entityPos.getY() - 0.5f, entityPos.getZ() - 0.5f, entityPos.getX() + 0.5f, entityPos.getY() + 0.5f, entityPos.getZ() + 0.5f)
                    .expand(-checkRadius, -checkRadius, -checkRadius).expand(checkRadius, checkRadius, checkRadius);

            if(!worldIn.getEntitiesWithinAABB(Entity.class, areaHitbox).isEmpty())
            {
                for(Entity foundEntity: worldIn.getEntitiesWithinAABB(Entity.class, areaHitbox)) {
                    if(!(foundEntity instanceof PlayerEntity) && !(foundEntity instanceof FakePlayer))
                    {
                        if(foundEntity.getType().equals(getTransformedEntity()))
                            {
                            if(!getTransformedEntityData().isEmpty())
                            {
                                CompoundNBT entityTag = AgeingRegistry.entityToNBT(foundEntity);
                                CompoundNBT entityTag2 = getTransformedEntityData();

                                if(!NBTUtil.areNBTEquals(entityTag2, entityTag, true))
                                {
                                    bossAmount++;
                                }
                            }
                            else
                            {
                                bossAmount++;
                            }
                        }
                    }
                }
            }
            return bossAmount < getMaxInArea();
        }
        else
        {
            return true;
        }
    }
}