package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class EntityCriteria extends BaseCriteria {
	private EntityType<? extends Entity> nearbyEntity;
	private CompoundTag nearbyEntityData;
	private int radius;

	public EntityCriteria(iAgeing ageing, EntityType<? extends Entity> nearbyEntity, CompoundTag nearbyEntityData, int radius) {
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

	public CompoundTag getNearbyEntityData() {
		return nearbyEntityData;
	}

	public void setNearbyEntityData(CompoundTag nearbyEntityData) {
		this.nearbyEntityData = nearbyEntityData;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public boolean checkCriteria(Level worldIn, Entity entityIn) {
		BlockPos entityPos = entityIn.blockPosition();
		int nearbyEntityAmount = 0;

		AABB areaHitbox = new AABB(entityPos.getX() - 0.5f, entityPos.getY() - 0.5f, entityPos.getZ() - 0.5f, entityPos.getX() + 0.5f, entityPos.getY() + 0.5f, entityPos.getZ() + 0.5f)
				.expandTowards(-getRadius(), -getRadius(), -getRadius()).expandTowards(getRadius(), getRadius(), getRadius());
		if (!worldIn.getEntitiesOfClass(Entity.class, areaHitbox).isEmpty()) {
			for (Entity foundEntity : worldIn.getEntitiesOfClass(Entity.class, areaHitbox)) {
				if (!(foundEntity instanceof Player)) {
					if (foundEntity.getType().equals(getTransformedEntity())) {
						if (!getTransformedEntityData().isEmpty()) {
							CompoundTag entityTag = AgeingRegistry.entityToNBT(foundEntity);
							CompoundTag entityTag2 = getNearbyEntityData();

							if (!NbtUtils.compareNbt(entityTag2, entityTag, true)) {
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
