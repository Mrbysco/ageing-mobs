package com.shynieke.ageingmobs.handler;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.Reference;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Iterator;
import java.util.UUID;

public class AgeHandler {

    @SubscribeEvent
    public void AgeHandler(TickEvent.WorldTickEvent event)
    {
        if (event.phase.equals(TickEvent.Phase.END) && event.side.isServer())
        {
            ServerWorld world = (ServerWorld)event.world;
            if (world.getGameTime() % 20 == 0) {
                if(!AgeingRegistry.ageingList.isEmpty()) {
                    Iterator<Entity> entityIterator = world.getEntities().iterator();
                    while(entityIterator.hasNext()) {
                        Entity entityIn = entityIterator.next();
                        for(AgeingData info : AgeingRegistry.ageingList) {
                            if(!(entityIn instanceof PlayerEntity) && !(entityIn instanceof FakePlayer)) {
                                if(entityIn.getType().equals(info.getEntity())) {
                                    if(info.getEntity().equals(info.getTransformedEntity()))
                                    {
                                        if(!info.getTransformedEntityData().isEmpty())
                                        {
                                            CheckList(info, entityIn, world);
                                        }
                                        else
                                        {
                                            AgeingMobs.LOGGER.error("An error has occured. A mob can not transform into itself. See id: %s", new Object[] {info.getName()});
                                            if(AgeingRegistry.ageingList.contains(info))
                                            {
                                                AgeingRegistry.INSTANCE.removeAgeing(info);
                                            }
                                        }
                                    }
                                    else
                                    {
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

    public void CheckList(iAgeing info, Entity entity, World world)
    {
        if(info.getEntity().equals(info.getTransformedEntity()))
        {
            if(info.getEntityData().equals(info.getTransformedEntityData()))
            {
                AgeingMobs.LOGGER.error("Aged Entity nbt identical to the original: %s", new Object[] {info.getName()});
            }
            else
            {
                if(info.getEntityData().isEmpty())
                {
                    if(!info.getTransformedEntityData().isEmpty())
                    {
                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTag2 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty())
                        {
                            if(!NBTUtil.areNBTEquals(entityTag2, entityTag, true))
                            {
                                extraChecks(info, entity, world);
                            }
                        }
                    }
                    else
                    {
                        AgeingMobs.LOGGER.error("Aged Entity identical to the original: %s", new Object[] {info.getName()});
                    }
                }
                else
                {
                    if(!info.getTransformedEntityData().isEmpty())
                    {
                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTag2 = info.getEntityData();
                        CompoundNBT entityTag3 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty() && !entityTag3.isEmpty())
                        {
                            if(NBTUtil.areNBTEquals(entityTag2, entityTag, true) && !NBTUtil.areNBTEquals(entityTag3, entityTag, true))
                            {
                                extraChecks(info, entity, world);
                            }
                        }
                    }
                    else
                    {
                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTag2 = info.getEntityData();

                        if(!entityTag2.isEmpty())
                        {
                            if(NBTUtil.areNBTEquals(entityTag2, entityTag, true))
                            {
                                extraChecks(info, entity, world);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            if(!info.getEntityData().isEmpty())
            {
                CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                CompoundNBT entityTag2 = info.getEntityData();
                CompoundNBT entityTag3 = info.getTransformedEntityData();

                if(!entityTag2.isEmpty() && !entityTag3.isEmpty())
                {
                    if(NBTUtil.areNBTEquals(entityTag2, entityTag, true) && !NBTUtil.areNBTEquals(entityTag3, entityTag, true))
                    {
                        extraChecks(info, entity, world);
                    }
                }
            }
            else
            {
                extraChecks(info, entity, world);
            }
        }
    }

    public void extraChecks(iAgeing info, Entity entity, World world)
    {
        if(ModList.get().isLoaded("gamestages"))
        {
            checkCriteria(info, entity, world);
            //gamestageChecks(info, entity, world);
        }
        else
        {
            checkCriteria(info, entity, world);
        }
    }

    public void checkCriteria(iAgeing info, Entity entity, World world) {
        if(info.getCriteria().length > 0) {
            boolean ableToAge = true;
            for(int i = 0; i < info.getCriteria().length; i++) {
                BaseCriteria criteria = info.getCriteria()[i];
                if(criteria.isReversing()) {
                    BabifyTheMob(info, entity, world);
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

    public void ageTheMob(iAgeing info, Entity entity, World world)
    {
        int maxTime = info.getAgeingTme();

        String uniqueTag = Reference.MOD_PREFIX + info.getName();
        CompoundNBT tag = entity.getPersistentData();
        if(!tag.contains(uniqueTag))
        {
            tag.putInt(uniqueTag, 0);
        }

        if(tag.getInt(uniqueTag) >= maxTime)
        {
            if(info.getEntity().equals(info.getTransformedEntity()))
            {
                if(!info.getTransformedEntityData().isEmpty())
                {
                    Entity agedEntity = info.getTransformedEntity().create(world);
                    if(agedEntity != null)
                    {
                        agedEntity.copyLocationAndAnglesFrom(entity);
                        world.addEntity(agedEntity);

                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTagCopy = entityTag.copy();
                        CompoundNBT entityTag2 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty())
                        {
                            entityTagCopy.merge(entityTag2);
                            UUID uuid = agedEntity.getUniqueID();
                            agedEntity.read(entityTagCopy);
                            agedEntity.setUniqueId(uuid);
                        }
                    }
                    else
                    {
                        AgeingMobs.LOGGER.error("Aged Entity invalid [Line 224, Report this to the author of Ageing Mobs]: " + info.getTransformedEntity().toString());
                    }

                    tag.remove(uniqueTag);
                    entity.captureDrops(null); //TODO: Check what this even does
                    entity.remove();
                }
            }
            else
            {
                if(!info.getTransformedEntityData().isEmpty())
                {
                    Entity agedEntity = info.getTransformedEntity().create(world);
                    if(agedEntity != null)
                    {
                        agedEntity.copyLocationAndAnglesFrom(entity);

                        CompoundNBT entityTag = AgeingRegistry.entityToNBT(entity);
                        CompoundNBT entityTagCopy = entityTag.copy();
                        CompoundNBT entityTag2 = info.getTransformedEntityData();

                        if(!entityTag2.isEmpty())
                        {
                            UUID uuid = agedEntity.getUniqueID();
                            entityTagCopy.merge(entityTag2);
                            agedEntity.read(entityTag);
                            agedEntity.setUniqueId(uuid);
                        }
                        world.addEntity(agedEntity);
                    }
                    else
                    {
                        AgeingMobs.LOGGER.error("Aged Entity invalid [Line 256, Report this to the author of Ageing Mobs]: " + info.getTransformedEntity().toString());
                    }

                    tag.remove(uniqueTag);
                    entity.captureDrops(null);
                    entity.remove();
                }
                else
                {
                    Entity agedEntity = info.getTransformedEntity().create(world);
                    if(agedEntity != null)
                    {
                        agedEntity.copyLocationAndAnglesFrom(entity);
                        world.addEntity(agedEntity);
                    }
                    else
                    {
                        AgeingMobs.LOGGER.error("Aged Entity invalid [Line 273, Report this to the author of Ageing Mobs]: " + info.getTransformedEntity().toString());
                    }

                    tag.remove(uniqueTag);
                    entity.captureDrops(null);
                    entity.remove();
                }
            }
        }
        else
        {
            int currentAge = tag.getInt(uniqueTag);
            currentAge++;
            tag.putInt(uniqueTag, currentAge);
        }
    }

    public void BabifyTheMob(iAgeing info, Entity entity, World world)
    {
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
}
