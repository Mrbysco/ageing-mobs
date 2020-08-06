package com.shynieke.ageingmobs.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.config.AgeingConfig;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BiomeTypeCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BlockBasedCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BossCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.DimensionCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.LightCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.MagicCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.WeatherCriteria;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgeingRegistry {
    public static AgeingRegistry INSTANCE = new AgeingRegistry();

    public static List<String> nameList = Lists.newArrayList();
    public static List<AgeingData> ageingList = Lists.newArrayList();
    private Map<String, AgeingData> nameToAgeing = Maps.newHashMap();

    private static HashMap<Block, Double> importanceList = new HashMap<>();
    private static List<ResourceLocation> moonDimensions = Lists.newArrayList();

    public static void initializeAgeing() {
        Map<String, AgeingData> nameToAgeing = INSTANCE.getNameToAgeing();
        if(!nameList.contains("CreeperToCharged") && AgeingConfig.SERVER.creeperAgeing.get()) {
            AgeingData creeperToCharged = new AgeingData("CreeperToCharged", EntityType.CREEPER, createNBTTag(""), EntityType.CREEPER, createNBTTag("{powered:1b}"), AgeingConfig.SERVER.creeperAgeingTime.get());
            creeperToCharged.setCriteria(new BaseCriteria[] { new WeatherCriteria(creeperToCharged, "thunder") });
            INSTANCE.registerAgeing(creeperToCharged);
        } else if (nameList.contains("CreeperToCharged")){
            AgeingData creeperToCharged = nameToAgeing.get("CreeperToCharged");
            int ageingTime = AgeingConfig.SERVER.creeperAgeingTime.get();
            if(creeperToCharged.getAgeingTme() != ageingTime) {
                creeperToCharged.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(creeperToCharged);
            }
        }

        if(!nameList.contains("ZombieToHusk") && AgeingConfig.SERVER.zombieToHuskAgeing.get()) {
            AgeingData zombieToHusk = new AgeingData("ZombieToHusk", EntityType.ZOMBIE, createNBTTag(""), EntityType.HUSK, createNBTTag(""), AgeingConfig.SERVER.zombieToHuskAgeingTime.get());
            zombieToHusk.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(zombieToHusk, BiomeDictionary.Type.HOT)});
            INSTANCE.registerAgeing(zombieToHusk);
        } else if (nameList.contains("ZombieToHusk")){
            AgeingData zombieToHusk = nameToAgeing.get("ZombieToHusk");
            int ageingTime = AgeingConfig.SERVER.zombieToHuskAgeingTime.get();
            if(zombieToHusk.getAgeingTme() != ageingTime) {
                zombieToHusk.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(zombieToHusk);
            }
        }

        if(!nameList.contains("BabyZombieToBabyHusk") && AgeingConfig.SERVER.zombieToHuskAgeing.get()) {
            AgeingData babyZombieToBabyHusk = new AgeingData("BabyZombieToBabyHusk", EntityType.ZOMBIE, createNBTTag("{IsBaby:1b}"), EntityType.HUSK, createNBTTag("{IsBaby:1b}"), AgeingConfig.SERVER.zombieToHuskAgeingTime.get());
            babyZombieToBabyHusk.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(babyZombieToBabyHusk, BiomeDictionary.Type.HOT) });
            INSTANCE.registerAgeing(babyZombieToBabyHusk);
        } else if (nameList.contains("BabyZombieToBabyHusk")){
            AgeingData babyZombieToBabyHusk = nameToAgeing.get("BabyZombieToBabyHusk");
            int ageingTime = AgeingConfig.SERVER.zombieToHuskAgeingTime.get();
            if(babyZombieToBabyHusk.getAgeingTme() != ageingTime) {
                babyZombieToBabyHusk.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(babyZombieToBabyHusk);
            }
        }

        if(!nameList.contains("HuskToZombie") && AgeingConfig.SERVER.huskToZombieAgeing.get()) {
            AgeingData huskToZombie = new AgeingData("HuskToZombie", EntityType.HUSK, createNBTTag(""), EntityType.ZOMBIE, createNBTTag(""), AgeingConfig.SERVER.huskToZombieAgeingTime.get());
            huskToZombie.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(huskToZombie, BiomeDictionary.Type.COLD) });
            INSTANCE.registerAgeing(huskToZombie);
        } else if (nameList.contains("HuskToZombie")){
            AgeingData huskToZombie = nameToAgeing.get("HuskToZombie");
            int ageingTime = AgeingConfig.SERVER.huskToZombieAgeingTime.get();
            if(huskToZombie.getAgeingTme() != ageingTime) {
                huskToZombie.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(huskToZombie);
            }
        }

        if(!nameList.contains("BabyHuskToBabyZombie") && AgeingConfig.SERVER.huskToZombieAgeing.get()) {
            AgeingData babyHuskToBabyZombie = new AgeingData("BabyHuskToBabyZombie", EntityType.HUSK, createNBTTag("{IsBaby:1b}"), EntityType.ZOMBIE, createNBTTag("{IsBaby:1b}"), AgeingConfig.SERVER.huskToZombieAgeingTime.get());
            babyHuskToBabyZombie.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(babyHuskToBabyZombie, BiomeDictionary.Type.COLD) });
            INSTANCE.registerAgeing(babyHuskToBabyZombie);
        } else if (nameList.contains("BabyHuskToBabyZombie")){
            AgeingData babyHuskToBabyZombie = nameToAgeing.get("BabyHuskToBabyZombie");
            int ageingTime = AgeingConfig.SERVER.huskToZombieAgeingTime.get();
            if(babyHuskToBabyZombie.getAgeingTme() != ageingTime) {
                babyHuskToBabyZombie.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(babyHuskToBabyZombie);
            }
        }

        if(!nameList.contains("VillagerToVindicator") && AgeingConfig.SERVER.villagerToVindicatorAgeing.get()) {
            AgeingData villagerToVindicator = new AgeingData("VillagerToVindicator", EntityType.VILLAGER, createNBTTag(""), EntityType.VINDICATOR, createNBTTag(""), AgeingConfig.SERVER.villagerToVindicatorAgeingTime.get());
            villagerToVindicator.setCriteria(new BaseCriteria[] { new LightCriteria(villagerToVindicator, AgeingConfig.SERVER.villagerToVindicatorMinLight.get(), AgeingConfig.SERVER.villagerToVindicatorMaxLight.get(), false, true)});
            INSTANCE.registerAgeing(villagerToVindicator);
        } else if (nameList.contains("VillagerToVindicator")){
            AgeingData villagerToVindicator = nameToAgeing.get("VillagerToVindicator");
            boolean ageingChanged = false;
            boolean criteriaChanged = false;
            int ageingTime = AgeingConfig.SERVER.villagerToVindicatorAgeingTime.get();
            if(villagerToVindicator.getAgeingTme() != ageingTime) {
                villagerToVindicator.setAgeingTme(ageingTime);
                ageingChanged = true;
            }
            BaseCriteria[] criterias = villagerToVindicator.getCriteria();
            if(criterias.length > 0) {
                for(BaseCriteria criteria : criterias) {
                    if(criteria instanceof LightCriteria) {
                        LightCriteria lightCriteria = (LightCriteria)criteria;
                        int minLight = AgeingConfig.SERVER.villagerToVindicatorMinLight.get();
                        int maxLight = AgeingConfig.SERVER.villagerToVindicatorMaxLight.get();
                        if(lightCriteria.getLightLevelMin() != minLight) {
                            lightCriteria.setLightLevelMin(minLight);
                            criteriaChanged = true;
                        }
                        if(lightCriteria.getLightLevelMax() != maxLight) {
                            lightCriteria.setLightLevelMax(maxLight);
                            criteriaChanged = true;
                        }
                    }
                }
            }
            if(criteriaChanged) {
                villagerToVindicator.setCriteria(criterias);
            }
            if(ageingChanged || criteriaChanged) {
                INSTANCE.replaceAgeing(villagerToVindicator);
            }
        }

        if(!nameList.contains("VindicatorToEvoker") && AgeingConfig.SERVER.vindicatorToEvokerAgeing.get()) {
            AgeingData vindicatorToEvoker = new AgeingData("VindicatorToEvoker", EntityType.VINDICATOR, createNBTTag(""), EntityType.EVOKER, createNBTTag(""), AgeingConfig.SERVER.vindicatorToEvokerAgeingTime.get());
            vindicatorToEvoker.setCriteria(new BaseCriteria[] { new MagicCriteria(vindicatorToEvoker, 5)});
            INSTANCE.registerAgeing(vindicatorToEvoker);
        } else if (nameList.contains("VindicatorToEvoker")){
            AgeingData vindicatorToEvoker = nameToAgeing.get("VindicatorToEvoker");
            int ageingTime = AgeingConfig.SERVER.vindicatorToEvokerAgeingTime.get();
            if(vindicatorToEvoker.getAgeingTme() != ageingTime) {
                vindicatorToEvoker.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(vindicatorToEvoker);
            }
        }

        if(!nameList.contains("GuardianToElder") && AgeingConfig.SERVER.guardianToElderAgeing.get()) {
            AgeingData guardianToElder = new AgeingData("GuardianToElder", EntityType.GUARDIAN, createNBTTag(""), EntityType.ELDER_GUARDIAN, createNBTTag(""), AgeingConfig.SERVER.guardianToElderAgeingTime.get());
            guardianToElder.setCriteria(new BaseCriteria[] { new BossCriteria(guardianToElder, AgeingConfig.SERVER.guardianToElderAgeingMax.get(), AgeingConfig.SERVER.guardianToElderRange.get())});
            INSTANCE.registerAgeing(guardianToElder);
        } else if (nameList.contains("GuardianToElder")){
            AgeingData guardianToElder = nameToAgeing.get("GuardianToElder");
            boolean ageingChanged = false;
            boolean criteriaChanged = false;
            int ageingTime = AgeingConfig.SERVER.guardianToElderAgeingTime.get();
            if(guardianToElder.getAgeingTme() != ageingTime) {
                guardianToElder.setAgeingTme(ageingTime);
                ageingChanged = true;
            }
            BaseCriteria[] criterias = guardianToElder.getCriteria();
            if(criterias.length > 0) {
                for(BaseCriteria criteria : criterias) {
                    if(criteria instanceof BossCriteria) {
                        BossCriteria bossCriteria = (BossCriteria)criteria;
                        int ageingMax = AgeingConfig.SERVER.guardianToElderAgeingMax.get();
                        int range = AgeingConfig.SERVER.guardianToElderRange.get();
                        if(bossCriteria.getMaxInArea() != ageingMax) {
                            bossCriteria.setMaxInArea(ageingMax);
                            criteriaChanged = true;
                        }
                        if(bossCriteria.getCheckRadius() != range) {
                            bossCriteria.setCheckRadius(range);
                            criteriaChanged = true;
                        }
                    }
                }
            }
            if(criteriaChanged) {
                guardianToElder.setCriteria(criterias);
            }
            if(ageingChanged || criteriaChanged) {
                INSTANCE.replaceAgeing(guardianToElder);
            }
        }

        if(!nameList.contains("BabyToZombie") && AgeingConfig.SERVER.babyToZombieAgeing.get()) {
            INSTANCE.registerAgeing(new AgeingData("BabyToZombie", EntityType.ZOMBIE, createNBTTag("{IsBaby:1b}"), EntityType.ZOMBIE, createNBTTag("{IsBaby:0b}"), AgeingConfig.SERVER.babyToZombieAgeingTime.get()));
        } else if (nameList.contains("BabyToZombie")){
            AgeingData babyToZombie = nameToAgeing.get("BabyToZombie");
            int ageingTime = AgeingConfig.SERVER.babyToZombieAgeingTime.get();
            if(babyToZombie.getAgeingTme() != ageingTime) {
                babyToZombie.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(babyToZombie);
            }
        }

        if(!nameList.contains("BabyToHusk") && AgeingConfig.SERVER.babyToZombieAgeing.get()) {
            INSTANCE.registerAgeing(new AgeingData("BabyToHusk", EntityType.HUSK, createNBTTag("{IsBaby:1b}"), EntityType.HUSK, createNBTTag("{IsBaby:0b}"), AgeingConfig.SERVER.babyToZombieAgeingTime.get()));
        } else if (nameList.contains("BabyToHusk")){
            AgeingData babyToHusk = nameToAgeing.get("BabyToHusk");
            int ageingTime = AgeingConfig.SERVER.babyToZombieAgeingTime.get();
            if(babyToHusk.getAgeingTme() != ageingTime) {
                babyToHusk.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(babyToHusk);
            }
        }

        //if(AgeingConfig.SERVER.endermiteToShulkerAgeing.get()) {
            //TODO: addEndermite();
        //}

        if(!nameList.contains("SkeletonToStray") && AgeingConfig.SERVER.skeletonToStrayAgeing.get()) {
            AgeingData skeletonToStray = new AgeingData("SkeletonToStray", EntityType.SKELETON, createNBTTag(""), EntityType.STRAY, createNBTTag(""), AgeingConfig.SERVER.skeletonToStrayAgeingTime.get());
            skeletonToStray.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(skeletonToStray, BiomeDictionary.Type.COLD)});
            INSTANCE.registerAgeing(skeletonToStray);
        } else if (nameList.contains("SkeletonToStray")){
            AgeingData skeletonToStray = nameToAgeing.get("SkeletonToStray");
            int ageingTime = AgeingConfig.SERVER.skeletonToStrayAgeingTime.get();
            if(skeletonToStray.getAgeingTme() != ageingTime) {
                skeletonToStray.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(skeletonToStray);
            }
        }

        if(!nameList.contains("StrayToSkeleton") && AgeingConfig.SERVER.strayToSkeletonAgeing.get()) {
            AgeingData strayToSkeleton = new AgeingData("StrayToSkeleton", EntityType.STRAY, createNBTTag(""), EntityType.SKELETON, createNBTTag(""), AgeingConfig.SERVER.strayToSkeletonAgeingTime.get());
            strayToSkeleton.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(strayToSkeleton, BiomeDictionary.Type.HOT)});
            INSTANCE.registerAgeing(strayToSkeleton);
        } else if (nameList.contains("StrayToSkeleton")){
            AgeingData strayToSkeleton = nameToAgeing.get("StrayToSkeleton");
            int ageingTime = AgeingConfig.SERVER.strayToSkeletonAgeingTime.get();
            if(strayToSkeleton.getAgeingTme() != ageingTime) {
                strayToSkeleton.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(strayToSkeleton);
            }
        }

        if(!nameList.contains("RabbitToKiller") && AgeingConfig.SERVER.rabbitToKillerAgeing.get()) {
            AgeingData rabbitToKiller = new AgeingData("RabbitToKiller", EntityType.RABBIT, createNBTTag(""), EntityType.RABBIT, createNBTTag("{RabbitType:99}"), AgeingConfig.SERVER.rabbitToKillerAgeingTime.get());
            rabbitToKiller.setCriteria(new BaseCriteria[] { new LightCriteria(rabbitToKiller, AgeingConfig.SERVER.rabbitToKillerMinLight.get(), AgeingConfig.SERVER.rabbitToKillerMaxLight.get(), true, false)});
            INSTANCE.registerAgeing(rabbitToKiller);
        } else if (nameList.contains("RabbitToKiller")){
            AgeingData rabbitToKiller = nameToAgeing.get("RabbitToKiller");
            boolean ageingChanged = false;
            boolean criteriaChanged = false;
            int ageingTime = AgeingConfig.SERVER.rabbitToKillerAgeingTime.get();
            if(rabbitToKiller.getAgeingTme() != ageingTime) {
                rabbitToKiller.setAgeingTme(ageingTime);
                ageingChanged = true;
            }
            BaseCriteria[] criterias = rabbitToKiller.getCriteria();
            if(criterias.length > 0) {
                for(BaseCriteria criteria : criterias) {
                    if(criteria instanceof LightCriteria) {
                        LightCriteria lightCriteria = (LightCriteria)criteria;
                        int minLight = AgeingConfig.SERVER.rabbitToKillerMinLight.get();
                        int maxLight = AgeingConfig.SERVER.rabbitToKillerMaxLight.get();
                        if(lightCriteria.getLightLevelMin() != minLight) {
                            lightCriteria.setLightLevelMin(minLight);
                            criteriaChanged = true;
                        }
                        if(lightCriteria.getLightLevelMax() != maxLight) {
                            lightCriteria.setLightLevelMax(maxLight);
                            criteriaChanged = true;
                        }
                    }
                }
            }
            if(criteriaChanged) {
                rabbitToKiller.setCriteria(criterias);
            }
            if(ageingChanged || criteriaChanged) {
                INSTANCE.replaceAgeing(rabbitToKiller);
            }
        }

        if(!nameList.contains("CowToMooshroom") && AgeingConfig.SERVER.cowToMooshroomAgeing.get()) {
            AgeingData cowToMooshroom = new AgeingData("CowToMooshroom", EntityType.COW, createNBTTag(""), EntityType.MOOSHROOM, createNBTTag(""), AgeingConfig.SERVER.cowToMooshroomAgeingTime.get());
            cowToMooshroom.setCriteria(new BaseCriteria[] { new BlockBasedCriteria(cowToMooshroom, new Block[]{Blocks.MYCELIUM, Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM, Blocks.RED_MUSHROOM_BLOCK}, false, AgeingConfig.SERVER.cowToMooshroomAgeingRadius.get())});
            INSTANCE.registerAgeing(cowToMooshroom);
        } else if (nameList.contains("CowToMooshroom")){
            AgeingData cowToMooshroom = nameToAgeing.get("CowToMooshroom");
            boolean ageingChanged = false;
            boolean criteriaChanged = false;
            int ageingTime = AgeingConfig.SERVER.cowToMooshroomAgeingTime.get();
            if(cowToMooshroom.getAgeingTme() != ageingTime) {
                cowToMooshroom.setAgeingTme(ageingTime);
                ageingChanged = true;
            }
            BaseCriteria[] criterias = cowToMooshroom.getCriteria();
            if(criterias.length > 0) {
                for(BaseCriteria criteria : criterias) {
                    if(criteria instanceof BlockBasedCriteria) {
                        BlockBasedCriteria blockCriteria = (BlockBasedCriteria)criteria;
                        int radius = AgeingConfig.SERVER.cowToMooshroomAgeingRadius.get();
                        if(blockCriteria.getRadius() != radius) {
                            blockCriteria.setRadius(radius);
                            criteriaChanged = true;
                        }
                    }
                }
            }
            if(criteriaChanged) {
                cowToMooshroom.setCriteria(criterias);
            }
            if(ageingChanged || criteriaChanged) {
                INSTANCE.replaceAgeing(cowToMooshroom);
            }
        }

        if(!nameList.contains("SkeletonToWitherSkelly") && AgeingConfig.SERVER.skeletonToWitherSkeletonAgeing.get()) {
            AgeingData skeletonToWitherSkelly = new AgeingData("SkeletonToWitherSkelly", EntityType.SKELETON, createNBTTag(""), EntityType.WITHER_SKELETON, createNBTTag(""), AgeingConfig.SERVER.skeletonToWitherSkeletonAgeingTime.get());
            skeletonToWitherSkelly.setCriteria(new BaseCriteria[] { new DimensionCriteria(skeletonToWitherSkelly, new ResourceLocation[]{new ResourceLocation("the_end")})});
            INSTANCE.registerAgeing(skeletonToWitherSkelly);
        } else if (nameList.contains("SkeletonToWitherSkelly")){
            AgeingData skeletonToWitherSkelly = nameToAgeing.get("SkeletonToWitherSkelly");
            int ageingTime = AgeingConfig.SERVER.skeletonToWitherSkeletonAgeingTime.get();
            if(skeletonToWitherSkelly.getAgeingTme() != ageingTime) {
                skeletonToWitherSkelly.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(skeletonToWitherSkelly);
            }
        }

        if(!nameList.contains("SlimeToMagmaCube") && AgeingConfig.SERVER.slimeToMagmaCubeAgeing.get()) {
            AgeingData slimeToMagmaCube = new AgeingData("SlimeToMagmaCube", EntityType.SLIME, createNBTTag(""), EntityType.MAGMA_CUBE, createNBTTag(""), AgeingConfig.SERVER.slimeToMagmaCubeAgeingTime.get());
            slimeToMagmaCube.setCriteria(new BaseCriteria[] { new DimensionCriteria(slimeToMagmaCube, new ResourceLocation[]{new ResourceLocation("the_end")})});
            INSTANCE.registerAgeing(slimeToMagmaCube);
        } else if (nameList.contains("SlimeToMagmaCube")){
            AgeingData slimeToMagmaCube = nameToAgeing.get("SlimeToMagmaCube");
            int ageingTime = AgeingConfig.SERVER.slimeToMagmaCubeAgeingTime.get();
            if(slimeToMagmaCube.getAgeingTme() != ageingTime) {
                slimeToMagmaCube.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(slimeToMagmaCube);
            }
        }

        if(!nameList.contains("BatToVex") && AgeingConfig.SERVER.batToVexAgeing.get()) {
            INSTANCE.registerAgeing(new AgeingData("BatToVex", EntityType.BAT, createNBTTag(""), EntityType.VEX, createNBTTag(""), AgeingConfig.SERVER.batToVexAgeingTime.get()));
        } else if (nameList.contains("BatToVex")){
            AgeingData batToVex = nameToAgeing.get("BatToVex");
            int ageingTime = AgeingConfig.SERVER.batToVexAgeingTime.get();
            if(batToVex.getAgeingTme() != ageingTime) {
                batToVex.setAgeingTme(ageingTime);
                INSTANCE.replaceAgeing(batToVex);
            }
        }
    }

    public void registerAgeing(AgeingData ageing)
    {
        nameList.add(ageing.getName());
        nameToAgeing.put(ageing.getName(), ageing);
        sortList(ageing);
    }

    public void removeAgeing(AgeingData ageing)
    {
        nameList.remove(ageing.getName());
        nameToAgeing.remove(ageing.getName(), ageing);
        removeAndSort(ageing);
    }

    public void replaceAgeing(AgeingData ageing)
    {
        AgeingData oldAgeing = nameToAgeing.get(ageing.getName());
        nameToAgeing.remove(oldAgeing.getName(), oldAgeing);
        nameToAgeing.put(oldAgeing.getName(), ageing);
        ageingList.remove(oldAgeing);
        sortList(ageing);
    }

    public boolean isIDUnique(String ID) {
        return !nameList.contains(ID);
    }

    private void sortList(@Nullable AgeingData ageing)
    {
        if(ageing != null)
            ageingList.add(ageing);

        ageingList.sort(Comparator.comparingInt(AgeingData::getAgeingTme));
    }

    private void removeAndSort(@Nullable AgeingData ageing) {
        if(ageing != null)
            ageingList.remove(ageing);

        ageingList.sort(Comparator.comparingInt(AgeingData::getAgeingTme));
    }

    public static CompoundNBT createNBTTag(String nbtData)
    {
        CompoundNBT tag = new CompoundNBT();

        try
        {
            if(nbtData.startsWith("{") && nbtData.endsWith("}"))
            {
                tag = JsonToNBT.getTagFromJson(nbtData);
            }
            else
            {
                tag = JsonToNBT.getTagFromJson("{" + nbtData + "}");
            }
        }
        catch (CommandSyntaxException nbtexception)
        {
            AgeingMobs.LOGGER.error("nope... " +  nbtexception);
        }

        return tag;
    }

    public void initializeMagicMap() {
        List<? extends String> magicalBlocks = AgeingConfig.SERVER.magical_blocks.get();
        if(!magicalBlocks.isEmpty()) {
            importanceList = new HashMap<>();

            for (String blockData : magicalBlocks) {
                String[] blockInfo = blockData.split(";");
                if (blockInfo.length > 2) {
                    AgeingMobs.LOGGER.error("An error has occured. " + blockData + " is using the wrong syntax.");
                } else if (blockInfo.length == 2) {
                    String blockName = blockInfo[0];
                    double importance = Double.parseDouble(blockInfo[1]);
                    Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName));

                    if (block != null && !importanceList.containsKey(block)) {
                        importanceList.put(block, importance);
                    }
                }
            }
        }
    }

    public HashMap<Block, Double> getMagicMap() {
        return importanceList;
    }

    public void initializeMoonDimensions() {
        List<? extends String> dimensions = AgeingConfig.SERVER.moon_dimensions.get();
        List<ResourceLocation> dimensionList = new ArrayList<>();
        for(String string : dimensions) {
            ResourceLocation dim = new ResourceLocation(string);
            if(!dimensionList.contains(dim)) {
                dimensionList.add(dim);
            }
        }
        moonDimensions = dimensionList;
    }

    public List<ResourceLocation> getMoonDimensions() {
        return moonDimensions;
    }

    public Map<String, AgeingData> getNameToAgeing() {
        return nameToAgeing;
    }

    public static CompoundNBT entityToNBT(Entity theEntity)
    {
        CompoundNBT nbttagcompound = theEntity.writeWithoutTypeId(new CompoundNBT());

        if (theEntity instanceof PlayerEntity)
        {
            ItemStack itemstack = ((PlayerEntity)theEntity).inventory.getCurrentItem();

            if (!itemstack.isEmpty())
            {
                nbttagcompound.put("SelectedItem", itemstack.write(new CompoundNBT()));
            }
        }

        return nbttagcompound;
    }
}
