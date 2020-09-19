package com.shynieke.ageingmobs.handler;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.Reference;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class AgeHandler {

    @SubscribeEvent
    public void AgeHandler(TickEvent.WorldTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END) && event.side.isServer()) {
            ServerWorld world = (ServerWorld)event.world;
            if (world.getGameTime() % 20 == 0) {
                List<AgeingData> ageingList = AgeingRegistry.ageingList;
                if(!ageingList.isEmpty()) {
                    Iterator<Entity> entityIterator = world.getEntities().iterator();
                    while(entityIterator.hasNext()) {
                        Entity entityIn = entityIterator.next();
                        for(AgeingData info : ageingList) {
                            if(entityIn != null && !(entityIn instanceof PlayerEntity) && entityIn.getType() != null && info.getEntity() != null) {
                                if(entityIn.getType().equals(info.getEntity())) {
                                    if(info.getTransformedEntity() != null && info.getEntity().equals(info.getTransformedEntity())) {
                                        if(!info.getTransformedEntityData().isEmpty()) {
                                            CheckList(info, entityIn, world);
                                        } else {
                                            AgeingMobs.LOGGER.error("An error has occured. A mob can not transform into itself. See id: " + info.getName());
                                            if(AgeingRegistry.ageingList.contains(info)) {
                                                AgeingRegistry.INSTANCE.removeAgeing(info);
                                            }
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

    public void CheckList(AgeingData info, Entity entity, World world) {
        if(info.getEntity().equals(info.getTransformedEntity())) {
            if(info.getEntityData().equals(info.getTransformedEntityData())) {
                AgeingMobs.LOGGER.error("Aged Entity nbt identical to the original: " + info.getName());
            } else {
                if(info.getEntityData().isEmpty()) {
                    if(!info.getTransformedEntityData().isEmpty()) {
                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTag2 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty()) {
                            if(!NBTUtil.areNBTEquals(entityTag2, entityTag, true)) {
                                extraChecks(info, entity, world);
                            }
                        }
                    } else {
                        AgeingMobs.LOGGER.error("Aged Entity identical to the original: " + info.getName());
                    }
                } else {
                    if(!info.getTransformedEntityData().isEmpty()) {
                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTag2 = info.getEntityData();
                        CompoundNBT entityTag3 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty() && !entityTag3.isEmpty()) {
                            if(NBTUtil.areNBTEquals(entityTag2, entityTag, true) && !NBTUtil.areNBTEquals(entityTag3, entityTag, true)) {
                                extraChecks(info, entity, world);
                            }
                        }
                    } else {
                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTag2 = info.getEntityData();

                        if(!entityTag2.isEmpty()) {
                            if(NBTUtil.areNBTEquals(entityTag2, entityTag, true)) {
                                extraChecks(info, entity, world);
                            }
                        }
                    }
                }
            }
        } else {
            if(!info.getEntityData().isEmpty()) {
                CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                CompoundNBT entityTag2 = info.getEntityData();
                CompoundNBT entityTag3 = info.getTransformedEntityData();

                if(!entityTag2.isEmpty() && !entityTag3.isEmpty()) {
                    if(NBTUtil.areNBTEquals(entityTag2, entityTag, true) && !NBTUtil.areNBTEquals(entityTag3, entityTag, true))
                    {
                        extraChecks(info, entity, world);
                    }
                }
            } else {
                extraChecks(info, entity, world);
            }
        }
    }

    public void extraChecks(AgeingData info, Entity entity, World world) {
        if(ModList.get().isLoaded("gamestages")) {
            if(!info.getGamestage().isEmpty()) {
                if(GamestagesHandler.gamestageChecks(info, entity, world)) {
                    checkCriteria(info, entity, world);
                }
            } else {
                checkCriteria(info, entity, world);
            }
        } else {
            checkCriteria(info, entity, world);
        }
    }

    public void checkCriteria(AgeingData info, Entity entity, World world) {
        if(info.getCriteria().length > 0) {
            boolean ableToAge = true;
            for(int i = 0; i < info.getCriteria().length; i++) {
                BaseCriteria criteria = info.getCriteria()[i];
                if(criteria.isReversing()) {
                    BabifyTheMob(info, entity);
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

    public void ageTheMob(AgeingData info, Entity entity, World world) {
        int maxTime = info.getAgeingTme();

        String uniqueTag = Reference.MOD_PREFIX + info.getName();
        CompoundNBT tag = entity.getPersistentData();
        if(!tag.contains(uniqueTag)) {
            tag.putInt(uniqueTag, 0);
        }

        if(tag.getInt(uniqueTag) >= maxTime) {
            if(info.getEntity().equals(info.getTransformedEntity())) {
                if(!info.getTransformedEntityData().isEmpty()) {
                    Entity agedEntity = info.getTransformedEntity().create(world);
                    if(agedEntity != null) {
                        agedEntity.copyLocationAndAnglesFrom(entity);
                        copyEquipment(entity, agedEntity);

                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTagCopy = entityTag.copy();
                        CompoundNBT entityTag2 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty()) {
                            entityTagCopy.merge(entityTag2);
                            UUID uuid = agedEntity.getUniqueID();
                            agedEntity.read(entityTagCopy);
                            agedEntity.setUniqueId(uuid);
                        }
                        world.addEntity(agedEntity);
                    } else {
                        AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: " + info.getTransformedEntity().getRegistryName());
                    }

                    tag.remove(uniqueTag);
                    entity.captureDrops(null);
                    entity.remove();
                }
            } else {
                if(!info.getTransformedEntityData().isEmpty()) {
                    Entity agedEntity = info.getTransformedEntity().create(world);
                    if(agedEntity != null) {
                        agedEntity.copyLocationAndAnglesFrom(entity);
                        copyEquipment(entity, agedEntity);

                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTagCopy = entityTag.copy();
                        CompoundNBT entityTag2 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty()) {
                            UUID uuid = agedEntity.getUniqueID();
                            entityTagCopy.merge(entityTag2);
                            agedEntity.read(entityTag);
                            agedEntity.setUniqueId(uuid);
                        }
                        world.addEntity(agedEntity);
                    } else {
                        AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: " + info.getTransformedEntity().getRegistryName());
                    }

                    tag.remove(uniqueTag);
                    entity.captureDrops(null);
                    entity.remove();
                } else {
                    Entity agedEntity = info.getTransformedEntity().create(world);
                    if(agedEntity != null) {
                        agedEntity.copyLocationAndAnglesFrom(entity);
                        copyEquipment(entity, agedEntity);
                        world.addEntity(agedEntity);
                    } else {
                        AgeingMobs.LOGGER.error("An error has occured. Aged Entity is null, can not create entity with resource location: " + info.getTransformedEntity().getRegistryName());
                    }

                    tag.remove(uniqueTag);
                    entity.captureDrops(null);
                    entity.remove();
                }
            }
        } else {
            int currentAge = tag.getInt(uniqueTag);
            currentAge++;
            tag.putInt(uniqueTag, currentAge);
            //System.out.println(info.getName() + " " + currentAge + " / " + maxTime);
        }
    }

    public void BabifyTheMob(AgeingData info, Entity entity) {
        String uniqueTag = Reference.MOD_PREFIX + info.getName();
        CompoundNBT tag = entity.getPersistentData();
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
        if(original instanceof MobEntity && changedEntity instanceof MobEntity) {
            MobEntity originalMob = (MobEntity)original;
            MobEntity changedMob = (MobEntity)changedEntity;
            changedMob.setItemStackToSlot(EquipmentSlotType.MAINHAND, originalMob.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
            changedMob.setItemStackToSlot(EquipmentSlotType.OFFHAND, originalMob.getItemStackFromSlot(EquipmentSlotType.OFFHAND));
            changedMob.setItemStackToSlot(EquipmentSlotType.HEAD, originalMob.getItemStackFromSlot(EquipmentSlotType.HEAD));
            changedMob.setItemStackToSlot(EquipmentSlotType.CHEST, originalMob.getItemStackFromSlot(EquipmentSlotType.CHEST));
            changedMob.setItemStackToSlot(EquipmentSlotType.LEGS, originalMob.getItemStackFromSlot(EquipmentSlotType.LEGS));
            changedMob.setItemStackToSlot(EquipmentSlotType.FEET, originalMob.getItemStackFromSlot(EquipmentSlotType.FEET));
        }
    }
}
