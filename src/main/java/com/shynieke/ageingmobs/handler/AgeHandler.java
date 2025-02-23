package com.shynieke.ageingmobs.handler;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.Reference;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.List;
import java.util.UUID;

public class AgeHandler {

	@SubscribeEvent
	public void handleAgeing(LevelTickEvent.Post event) {
		if (event.getLevel() instanceof ServerLevel serverLevel) {
			if (serverLevel.dimension() == Level.OVERWORLD && serverLevel.getGameTime() % 20 == 0) {
				if (!AgeingRegistry.ageingList.isEmpty()) {
					for (Entity entityIn : serverLevel.getEntities().getAll()) {
						if (entityIn != null) {
							ResourceLocation entityLocation = BuiltInRegistries.ENTITY_TYPE.getKey(entityIn.getType());
							if (entityLocation != null && AgeingRegistry.hasEntityAgeing(entityLocation)) {
								List<AgeingData> dataList = AgeingRegistry.getDataList(entityLocation);
								for (AgeingData info : dataList) {
									if (entityIn != null && !(entityIn instanceof Player) && entityIn.getType() != null && info.getEntity() != null) {
										if (entityIn.getType().equals(info.getEntity())) {
											if (info.getTransformedEntity() != null && info.getEntity().equals(info.getTransformedEntity())) {
												if (!info.getTransformedEntityData().isEmpty()) {
													checkList(info, entityIn, serverLevel);
												} else {
													AgeingMobs.LOGGER.error("An error has occured. A mob can not transform into itself. See id: {}", info.getName());
													AgeingRegistry.INSTANCE.removeAgeing(info);
												}
											} else {
												checkList(info, entityIn, serverLevel);
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

	/**
	 * Checks if the entity meets the requirements to be aged.
	 * <p>
	 * This method:
	 * - Determines whether the entity has the required NBT data for ageing.
	 * - If the transformed entity type is the same, it also compares with the transformed NBT data.
	 * - Logs an error if the transformation results in an identical entity.
	 * - Calls extraChecks() when the entity meets the ageing criteria.
	 *
	 * @param info   The ageing data containing transformation details.
	 * @param entity The entity being checked.
	 * @param level  The level in which the entity exists.
	 */
	public void checkList(AgeingData info, Entity entity, Level level) {
		// Convert the current entity into NBT data
		CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);

		// Retrieve stored original and transformed entity NBT data
		CompoundTag originalData = info.getEntityData();
		CompoundTag transformedData = info.getTransformedEntityData();

		// Check if the transformed entity is the same type as the original
		boolean sameEntityType = info.getEntity().equals(info.getTransformedEntity());

		// If both entity type and NBT data are identical, log an error
		if (sameEntityType && originalData.equals(transformedData)) {
			AgeingMobs.LOGGER.error("Aged Entity NBT identical to the original: {}", info.getName());
			return;
		}

		// Check if the entity matches the original NBT data (or if no NBT is required)
		boolean matchesOriginalData = originalData.isEmpty() || NbtUtils.compareNbt(originalData, entityTag, true);

		if (matchesOriginalData) {
			if (sameEntityType) {
				// If the transformed entity is the same type, ensure it doesn't match transformed data before running extraChecks
				if (transformedData.isEmpty() || !NbtUtils.compareNbt(transformedData, entityTag, true)) {
					extraChecks(info, entity, level);
				}
			} else {
				// If the entity type is different, proceed without checking transformed data
				extraChecks(info, entity, level);
			}
		}
	}

	public void extraChecks(AgeingData info, Entity entity, Level level) {
		if (ModList.get().isLoaded("gamestages") && !info.getGamestage().isEmpty()) {
			if (GamestagesHandler.gamestageChecks(info, entity, level)) {
				checkCriteria(info, entity, level);
			}
		} else {
			checkCriteria(info, entity, level);
		}
	}

	public void checkCriteria(AgeingData info, Entity entity, Level level) {
		boolean ableToAge = true;
		for (BaseCriteria criteria : info.getCriteria()) {
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
						tag.remove(uniqueTag);
						if (!canConvert(entity, agedEntity)) {
							return;
						}
						callConversionEvent(entity, agedEntity);

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
						AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: {}", BuiltInRegistries.ENTITY_TYPE.getKey(info.getTransformedEntity()));
					}

					entity.captureDrops(null);
					entity.discard();
				}
			} else {
				if (!info.getTransformedEntityData().isEmpty()) {
					Entity agedEntity = info.getTransformedEntity().create(level);
					if (agedEntity != null) {
						tag.remove(uniqueTag);
						if (!canConvert(entity, agedEntity)) {
							return;
						}
						callConversionEvent(entity, agedEntity);

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
						AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: {}", BuiltInRegistries.ENTITY_TYPE.getKey(info.getTransformedEntity()));
					}

					entity.captureDrops(null);
					entity.discard();
				} else {
					Entity agedEntity = info.getTransformedEntity().create(level);
					if (agedEntity != null) {
						tag.remove(uniqueTag);
						if (!canConvert(entity, agedEntity)) {
							return;
						}
						callConversionEvent(entity, agedEntity);

						agedEntity.copyPosition(entity);
						copyEquipment(entity, agedEntity);
						level.addFreshEntity(agedEntity);
					} else {
						AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: {}", BuiltInRegistries.ENTITY_TYPE.getKey(info.getTransformedEntity()));
					}

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

	/**
	 * Fire the LivingConversionEvent.Pre to check if the entity can be converted
	 *
	 * @param entity     the entity to be converted
	 * @param agedEntity the entity to be converted to
	 * @return if the entity can be converted
	 */
	@SuppressWarnings("unchecked")
	private boolean canConvert(Entity entity, Entity agedEntity) {
		if (entity instanceof LivingEntity livingEntity && agedEntity instanceof LivingEntity) {
			return EventHooks.canLivingConvert(livingEntity, (EntityType<? extends LivingEntity>) agedEntity.getType(), (timer) -> {
			});
		} else {
			return true;
		}
	}

	/**
	 * Fire the LivingConversionEvent.Post to notify that the entity has been converted
	 *
	 * @param entity     the entity that was converted
	 * @param agedEntity the entity that was converted to
	 */
	private void callConversionEvent(Entity entity, Entity agedEntity) {
		if (entity instanceof LivingEntity livingEntity && agedEntity instanceof LivingEntity outcome) {
			EventHooks.onLivingConvert(livingEntity, outcome);
		}
	}

	public void babifyTheMob(AgeingData info, Entity entity) {
		String uniqueTag = Reference.MOD_PREFIX + info.getName();
		CompoundTag tag = entity.getPersistentData();
		if (tag.contains(uniqueTag)) {
			int currentAge = tag.getInt(uniqueTag);
			if (currentAge >= 0) {
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
