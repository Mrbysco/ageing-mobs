package com.shynieke.ageingmobs.handler;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.Reference;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.UUID;

public class AgeHandler {

    @SubscribeEvent
    public void handleAgeing(TickEvent.WorldTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END) && event.side.isServer()) {
            ServerLevel world = (ServerLevel)event.world;
            if (world.getGameTime() % 20 == 0) {
                if(!AgeingRegistry.ageingList.isEmpty()) {
                    for (Entity entityIn : world.getEntities().getAll()) {
                        if(entityIn != null) {
                            ResourceLocation entityLocation = entityIn.getType().getRegistryName();
                            if (entityLocation != null && AgeingRegistry.hasEntityAgeing(entityLocation)) {
                                List<AgeingData> dataList = AgeingRegistry.getDataList(entityLocation);
                                for (AgeingData info : dataList) {
                                    if (entityIn != null && !(entityIn instanceof Player) && entityIn.getType() != null && info.getEntity() != null) {
                                        if (entityIn.getType().equals(info.getEntity())) {
                                            if (info.getTransformedEntity() != null && info.getEntity().equals(info.getTransformedEntity())) {
                                                if (!info.getTransformedEntityData().isEmpty()) {
                                                    CheckList(info, entityIn, world);
                                                } else {
                                                    AgeingMobs.LOGGER.error("An error has occured. A mob can not transform into itself. See id: " + info.getName());
                                                    AgeingRegistry.INSTANCE.removeAgeing(info);
                                                }
                                            } else {
                                                CheckList(info, entityIn, world);
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

    public void CheckList(AgeingData info, Entity entity, Level world) {
        if(info.getEntity().equals(info.getTransformedEntity())) {
            if(info.getEntityData().equals(info.getTransformedEntityData())) {
                AgeingMobs.LOGGER.error("Aged Entity nbt identical to the original: " + info.getName());
            } else {
                if(info.getEntityData().isEmpty()) {
                    if(!info.getTransformedEntityData().isEmpty()) {
                        CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundTag entityTag2 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty()) {
                            if(!NbtUtils.compareNbt(entityTag2, entityTag, true)) {
                                extraChecks(info, entity, world);
                            }
                        }
                    } else {
                        AgeingMobs.LOGGER.error("Aged Entity identical to the original: " + info.getName());
                    }
                } else {
                    if(!info.getTransformedEntityData().isEmpty()) {
                        CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundTag entityTag2 = info.getEntityData();
                        CompoundTag entityTag3 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty() && !entityTag3.isEmpty()) {
                            if(NbtUtils.compareNbt(entityTag2, entityTag, true) && !NbtUtils.compareNbt(entityTag3, entityTag, true)) {
                                extraChecks(info, entity, world);
                            }
                        }
                    } else {
                        CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundTag entityTag2 = info.getEntityData();

                        if(!entityTag2.isEmpty()) {
                            if(NbtUtils.compareNbt(entityTag2, entityTag, true)) {
                                extraChecks(info, entity, world);
                            }
                        }
                    }
                }
            }
        } else {
            if(!info.getEntityData().isEmpty()) {
                CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
                CompoundTag entityTag2 = info.getEntityData();
                CompoundTag entityTag3 = info.getTransformedEntityData();

                if(!entityTag2.isEmpty() && !entityTag3.isEmpty()) {
                    if(NbtUtils.compareNbt(entityTag2, entityTag, true) && !NbtUtils.compareNbt(entityTag3, entityTag, true))
                    {
                        extraChecks(info, entity, world);
                    }
                }
            } else {
                extraChecks(info, entity, world);
            }
        }
    }

    public void extraChecks(AgeingData info, Entity entity, Level world) {
//        if(ModList.get().isLoaded("gamestages")) {
//            if(!info.getGamestage().isEmpty()) {
//                if(GamestagesHandler.gamestageChecks(info, entity, world)) {
//                    checkCriteria(info, entity, world);
//                }
//            } else {
//                checkCriteria(info, entity, world);
//            }
//        } else {
            checkCriteria(info, entity, world);
//        }
    }

    public void checkCriteria(AgeingData info, Entity entity, Level world) {
        if(info.getCriteria().length > 0) {
            boolean ableToAge = true;
            for(int i = 0; i < info.getCriteria().length; i++) {
                BaseCriteria criteria = info.getCriteria()[i];
                if(criteria.isReversing()) {
                    babifyTheMob(info, entity);
                }

                if(!criteria.checkCriteria(world, entity)) {
                    ableToAge = false;
                    break;
                }
            }
            if(ableToAge) {
                ageTheMob(info, entity, world);
            }
        } else {
            ageTheMob(info, entity, world);
        }
    }

    public void ageTheMob(AgeingData info, Entity entity, Level world) {
        int maxTime = info.getAgeingTme();

        String uniqueTag = Reference.MOD_PREFIX + info.getName();
        CompoundTag tag = entity.getPersistentData();
        if(!tag.contains(uniqueTag)) {
            tag.putInt(uniqueTag, 0);
        }

        if(tag.getInt(uniqueTag) >= maxTime) {
            if(info.getEntity().equals(info.getTransformedEntity())) {
                if(!info.getTransformedEntityData().isEmpty()) {
                    Entity agedEntity = info.getTransformedEntity().create(world);
                    if(agedEntity != null) {
                        agedEntity.copyPosition(entity);
                        copyEquipment(entity, agedEntity);

                        CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundTag entityTagCopy = entityTag.copy();
                        CompoundTag entityTag2 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty()) {
                            entityTagCopy.merge(entityTag2);
                            UUID uuid = agedEntity.getUUID();
                            agedEntity.load(entityTagCopy);
                            agedEntity.setUUID(uuid);
                        }
                        world.addFreshEntity(agedEntity);
                    } else {
                        AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: " + info.getTransformedEntity().getRegistryName());
                    }

                    tag.remove(uniqueTag);
                    entity.captureDrops(null);
                    entity.discard();
                }
            } else {
                if(!info.getTransformedEntityData().isEmpty()) {
                    Entity agedEntity = info.getTransformedEntity().create(world);
                    if(agedEntity != null) {
                        agedEntity.copyPosition(entity);
                        copyEquipment(entity, agedEntity);

                        CompoundTag entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundTag entityTagCopy = entityTag.copy();
                        CompoundTag entityTag2 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty()) {
                            UUID uuid = agedEntity.getUUID();
                            entityTagCopy.merge(entityTag2);
                            agedEntity.load(entityTag);
                            agedEntity.setUUID(uuid);
                        }
                        world.addFreshEntity(agedEntity);
                    } else {
                        AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: " + info.getTransformedEntity().getRegistryName());
                    }

                    tag.remove(uniqueTag);
                    entity.captureDrops(null);
                    entity.discard();
                } else {
                    Entity agedEntity = info.getTransformedEntity().create(world);
                    if(agedEntity != null) {
                        agedEntity.copyPosition(entity);
                        copyEquipment(entity, agedEntity);
                        world.addFreshEntity(agedEntity);
                    } else {
                        AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: " + info.getTransformedEntity().getRegistryName());
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
        if(tag.contains(uniqueTag)) {
            if(tag.getInt(uniqueTag) >= 0) {
                int currentAge = tag.getInt(uniqueTag);
                currentAge--;
                tag.putInt(uniqueTag, currentAge);
            } else {
                tag.remove(uniqueTag);
            }
        }
    }

    public void copyEquipment(Entity original, Entity changedEntity) {
        if(original instanceof Mob originalMob && changedEntity instanceof Mob changedMob) {
            changedMob.setItemSlot(EquipmentSlot.MAINHAND, originalMob.getItemBySlot(EquipmentSlot.MAINHAND));
            changedMob.setItemSlot(EquipmentSlot.OFFHAND, originalMob.getItemBySlot(EquipmentSlot.OFFHAND));
            changedMob.setItemSlot(EquipmentSlot.HEAD, originalMob.getItemBySlot(EquipmentSlot.HEAD));
            changedMob.setItemSlot(EquipmentSlot.CHEST, originalMob.getItemBySlot(EquipmentSlot.CHEST));
            changedMob.setItemSlot(EquipmentSlot.LEGS, originalMob.getItemBySlot(EquipmentSlot.LEGS));
            changedMob.setItemSlot(EquipmentSlot.FEET, originalMob.getItemBySlot(EquipmentSlot.FEET));
        }
    }
}
