package com.shynieke.ageingmobs.handler;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.Reference;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.TickEvent;

import java.util.List;
import java.util.UUID;

public class AgeHandler {

	@SubscribeEvent
	public void handleAgeing(TickEvent.LevelTickEvent event) {
		if (event.phase.equals(TickEvent.Phase.END) && event.side.isServer()) {
			ServerLevel serverLevel = (ServerLevel) event.level;
			if (serverLevel.dimension() == Level.OVERWORLD && serverLevel.getGameTime() % 20 == 0) {
				if (!AgeingRegistry.ageingList.isEmpty()) {
					for (Entity entityIn : serverLevel.getEntities().getAll()) {
						if (entityIn != null) {
							var entityResourceKey = BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityIn.getType());
							if (entityResourceKey.isPresent() && AgeingRegistry.hasEntityAgeing(entityResourceKey.get().location())) {
								List<AgeingData> dataList = AgeingRegistry.getDataList(entityResourceKey.get().location());
								for (AgeingData info : dataList) {
									if (!(entityIn instanceof Player) && info.getEntity() != null) {
										if (entityIn.getType().equals(info.getEntity())) {
											if (info.getTransformedEntity() != null && info.getEntity().equals(info.getTransformedEntity())) {
												if (!info.getTransformedEntityData().isEmpty()) {
													CheckList(info, entityIn, serverLevel);
												} else {
													AgeingMobs.LOGGER.error("An error has occured. A mob can not transform into itself. See id: " + info.getName());
													AgeingRegistry.INSTANCE.removeAgeing(info);
												}
											} else {
												CheckList(info, entityIn, serverLevel);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void CheckList(AgeingData info, Entity entity, Level level) {
		if (info.getEntity().equals(info.getTransformedEntity())) {
			if (info.getEntityData().equals(info.getTransformedEntityData())) {
				AgeingMobs.LOGGER.error("Aged Entity nbt identical to the original: " + info.getName());
			} else {
				if (info.getEntityData().isEmpty()) {
					if (!info.getTransformedEntityData().isEmpty()) {
						CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
						CompoundTag entityTag2 = info.getTransformedEntityData();

						if (!entityTag2.isEmpty()) {
							if (!NbtUtils.compareNbt(entityTag2, entityTag, true)) {
								extraChecks(info, entity, level);
							}
						}
					} else {
						AgeingMobs.LOGGER.error("Aged Entity identical to the original: " + info.getName());
					}
				} else {
					if (!info.getTransformedEntityData().isEmpty()) {
						CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
						CompoundTag entityTag2 = info.getEntityData();
						CompoundTag entityTag3 = info.getTransformedEntityData();

						if (!entityTag2.isEmpty() && !entityTag3.isEmpty()) {
							if (NbtUtils.compareNbt(entityTag2, entityTag, true) && !NbtUtils.compareNbt(entityTag3, entityTag, true)) {
								extraChecks(info, entity, level);
							}
						}
					} else {
						CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
						CompoundTag entityTag2 = info.getEntityData();

						if (!entityTag2.isEmpty()) {
							if (NbtUtils.compareNbt(entityTag2, entityTag, true)) {
								extraChecks(info, entity, level);
							}
						}
					}
				}
			}
		} else {
			if (!info.getEntityData().isEmpty()) {
				CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
				CompoundTag entityTag2 = info.getEntityData();
				CompoundTag entityTag3 = info.getTransformedEntityData();

				if (!entityTag2.isEmpty() && !entityTag3.isEmpty()) {
					if (NbtUtils.compareNbt(entityTag2, entityTag, true) && !NbtUtils.compareNbt(entityTag3, entityTag, true)) {
						extraChecks(info, entity, level);
					}
				}
			} else {
				extraChecks(info, entity, level);
			}
		}
	}

	public void extraChecks(AgeingData info, Entity entity, Level level) {
		if (ModList.get().isLoaded("gamestages")) {
			if (!info.getGamestage().isEmpty()) {
				if (GamestagesHandler.gamestageChecks(info, entity, level)) {
					checkCriteria(info, entity, level);
				}
			} else {
				checkCriteria(info, entity, level);
			}
		} else {
			checkCriteria(info, entity, level);
		}
	}

	public void checkCriteria(AgeingData info, Entity entity, Level level) {
		if (info.getCriteria().length > 0) {
			boolean ableToAge = true;
			for (int i = 0; i < info.getCriteria().length; i++) {
				BaseCriteria criteria = info.getCriteria()[i];
				if (criteria.isReversing()) {
					babifyTheMob(info, entity);
				}

				if (!criteria.checkCriteria(level, entity)) {
					ableToAge = false;
					break;
				}
			}
			if (ableToAge) {
				ageTheMob(info, entity, level);
			}
		} else {
			ageTheMob(info, entity, level);
		}
	}

	public void ageTheMob(AgeingData info, Entity entity, Level level) {
		int maxTime = info.getAgeingTme();

		String uniqueTag = Reference.MOD_PREFIX + info.getName();
		CompoundTag tag = entity.getPersistentData();
		if (!tag.contains(uniqueTag)) {
			tag.putInt(uniqueTag, 0);
		}

		if (tag.getInt(uniqueTag) >= maxTime) {
			if (info.getEntity().equals(info.getTransformedEntity())) {
				if (!info.getTransformedEntityData().isEmpty()) {
					Entity agedEntity = info.getTransformedEntity().create(level);
					if (agedEntity != null) {
						agedEntity.copyPosition(entity);
						copyEquipment(entity, agedEntity);

						CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
						CompoundTag entityTagCopy = entityTag.copy();
						CompoundTag entityTag2 = info.getTransformedEntityData();

						if (!entityTag2.isEmpty()) {
							entityTagCopy.merge(entityTag2);
							UUID uuid = agedEntity.getUUID();
							agedEntity.load(entityTagCopy);
							agedEntity.setUUID(uuid);
						}
						level.addFreshEntity(agedEntity);
					} else {
						AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: " + BuiltInRegistries.ENTITY_TYPE.getKey(info.getTransformedEntity()));
					}

					tag.remove(uniqueTag);
					entity.captureDrops(null);
					entity.discard();
				}
			} else {
				if (!info.getTransformedEntityData().isEmpty()) {
					Entity agedEntity = info.getTransformedEntity().create(level);
					if (agedEntity != null) {
						agedEntity.copyPosition(entity);
						copyEquipment(entity, agedEntity);

						CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
						CompoundTag entityTagCopy = entityTag.copy();
						CompoundTag entityTag2 = info.getTransformedEntityData();

						if (!entityTag2.isEmpty()) {
							UUID uuid = agedEntity.getUUID();
							entityTagCopy.merge(entityTag2);
							agedEntity.load(entityTag);
							agedEntity.setUUID(uuid);
						}
						level.addFreshEntity(agedEntity);
					} else {
						AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: " + BuiltInRegistries.ENTITY_TYPE.getKey(info.getTransformedEntity()));
					}

					tag.remove(uniqueTag);
					entity.captureDrops(null);
					entity.discard();
				} else {
					Entity agedEntity = info.getTransformedEntity().create(level);
					if (agedEntity != null) {
						agedEntity.copyPosition(entity);
						copyEquipment(entity, agedEntity);
						level.addFreshEntity(agedEntity);
					} else {
						AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: " + BuiltInRegistries.ENTITY_TYPE.getKey(info.getTransformedEntity()));
					}

					tag.remove(uniqueTag);
					entity.captureDrops(null);
					entity.discard();
				}
			}
		} else {
			int currentAge = tag.getInt(uniqueTag);
			currentAge++;
			tag.putInt(uniqueTag, currentAge);
			//System.out.println(info.getName() + " " + currentAge + " / " + maxTime);
		}
	}

	public void babifyTheMob(AgeingData info, Entity entity) {
		String uniqueTag = Reference.MOD_PREFIX + info.getName();
		CompoundTag tag = entity.getPersistentData();
		if (tag.contains(uniqueTag)) {
			if (tag.getInt(uniqueTag) >= 0) {
				int currentAge = tag.getInt(uniqueTag);
				currentAge--;
				tag.putInt(uniqueTag, currentAge);
			} else {
				tag.remove(uniqueTag);
			}
		}
	}

	public void copyEquipment(Entity original, Entity changedEntity) {
		if (original instanceof Mob originalMob && changedEntity instanceof Mob changedMob) {
			changedMob.setItemSlot(EquipmentSlot.MAINHAND, originalMob.getItemBySlot(EquipmentSlot.MAINHAND));
			changedMob.setItemSlot(EquipmentSlot.OFFHAND, originalMob.getItemBySlot(EquipmentSlot.OFFHAND));
			changedMob.setItemSlot(EquipmentSlot.HEAD, originalMob.getItemBySlot(EquipmentSlot.HEAD));
			changedMob.setItemSlot(EquipmentSlot.CHEST, originalMob.getItemBySlot(EquipmentSlot.CHEST));
			changedMob.setItemSlot(EquipmentSlot.LEGS, originalMob.getItemBySlot(EquipmentSlot.LEGS));
			changedMob.setItemSlot(EquipmentSlot.FEET, originalMob.getItemBySlot(EquipmentSlot.FEET));
		}
	}
}
