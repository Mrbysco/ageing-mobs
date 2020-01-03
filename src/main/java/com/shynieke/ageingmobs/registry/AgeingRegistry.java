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
import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgeingRegistry {
    public static AgeingRegistry INSTANCE = new AgeingRegistry();

    public static List<String> nameList = Lists.newArrayList();
    public static List<iAgeing> ageingList = Lists.newArrayList();
    private Map<String, iAgeing> nameToAgeing = Maps.newHashMap();

    private static HashMap<Block, Double> importanceList = new HashMap<>();
    private static List<Integer> moonDimensions = Lists.newArrayList();

    public static void initializeAgeing() {
        if(AgeingConfig.SERVER.creeperAgeing.get()) {
            AgeingData creeperToCharged = new AgeingData("CreeperToCharged", EntityType.CREEPER, createNBTTag(""), EntityType.CREEPER, createNBTTag("{powered:1b}"), AgeingConfig.SERVER.creeperAgeingTime.get());
            creeperToCharged.setCriteria(new BaseCriteria[] { new WeatherCriteria(creeperToCharged, "thunder") });
            INSTANCE.registerAgeing(creeperToCharged);
        }

        if(AgeingConfig.SERVER.zombieToHuskAgeing.get()) {
            AgeingData zombieToHusk = new AgeingData("ZombieToHusk", EntityType.ZOMBIE, createNBTTag("{IsBaby:0}"), EntityType.HUSK, createNBTTag("{IsBaby:0}"), AgeingConfig.SERVER.zombieToHuskAgeingTime.get());
            zombieToHusk.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(zombieToHusk, BiomeDictionary.Type.HOT)});
            INSTANCE.registerAgeing(zombieToHusk);

            AgeingData babyZombieToBabyHusk = new AgeingData("BabyZombieToBabyHusk", EntityType.ZOMBIE, createNBTTag("{IsBaby:1b}"), EntityType.HUSK, createNBTTag("{IsBaby:1b}"), AgeingConfig.SERVER.zombieToHuskAgeingTime.get());
            babyZombieToBabyHusk.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(babyZombieToBabyHusk, BiomeDictionary.Type.HOT)});
            INSTANCE.registerAgeing(babyZombieToBabyHusk);;
        }

        if(AgeingConfig.SERVER.huskToZombieAgeing.get()) {
            AgeingData huskToZombie = new AgeingData("HuskToZombie", EntityType.HUSK, createNBTTag("{IsBaby:0}"), EntityType.ZOMBIE, createNBTTag("{IsBaby:0}"), AgeingConfig.SERVER.huskToZombieAgeingTime.get());
            huskToZombie.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(huskToZombie, BiomeDictionary.Type.COLD)});
            INSTANCE.registerAgeing(huskToZombie);

            AgeingData babyHuskToBabyZombie = new AgeingData("BabyHuskToBabyZombie", EntityType.HUSK, createNBTTag("{IsBaby:1b}"), EntityType.ZOMBIE, createNBTTag("{IsBaby:1b}"), AgeingConfig.SERVER.huskToZombieAgeingTime.get());
            babyHuskToBabyZombie.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(babyHuskToBabyZombie, BiomeDictionary.Type.COLD)});
            INSTANCE.registerAgeing(babyHuskToBabyZombie);
        }

        if(AgeingConfig.SERVER.villagerToVindicatorAgeing.get()) {
            AgeingData villagerToVindicator = new AgeingData("BabyHuskToBabyZombie", EntityType.VILLAGER, createNBTTag(""), EntityType.VINDICATOR, createNBTTag(""), AgeingConfig.SERVER.villagerToVindicatorAgeingTime.get());
            villagerToVindicator.setCriteria(new BaseCriteria[] { new LightCriteria(villagerToVindicator, AgeingConfig.SERVER.villagerToVindicatorMinLight.get(), AgeingConfig.SERVER.villagerToVindicatorMaxLight.get(), false, true)});
            INSTANCE.registerAgeing(villagerToVindicator);
        }

        if(AgeingConfig.SERVER.vindicatorToEvokerAgeing.get()) {
            AgeingData vindicatorToEvoker = new AgeingData("VindicatorToEvoker", EntityType.VINDICATOR, createNBTTag(""), EntityType.EVOKER, createNBTTag(""), AgeingConfig.SERVER.vindicatorToEvokerAgeingTime.get());
            vindicatorToEvoker.setCriteria(new BaseCriteria[] { new MagicCriteria(vindicatorToEvoker, 5)});
            INSTANCE.registerAgeing(vindicatorToEvoker);
        }

        if(AgeingConfig.SERVER.guardianToElderAgeing.get()) {
            AgeingData guardianToElder = new AgeingData("GuardianToElder", EntityType.GUARDIAN, createNBTTag(""), EntityType.ELDER_GUARDIAN, createNBTTag(""), AgeingConfig.SERVER.guardianToElderAgeingTime.get());
            guardianToElder.setCriteria(new BaseCriteria[] { new BossCriteria(guardianToElder, AgeingConfig.SERVER.guardianToElderAgeingMax.get(), AgeingConfig.SERVER.guardianToElderRange.get())});
            INSTANCE.registerAgeing(guardianToElder);
        }

        if(AgeingConfig.SERVER.babyToZombieAgeing.get()) {
            INSTANCE.registerAgeing(new AgeingData("BabyToZombie", EntityType.ZOMBIE, createNBTTag("{IsBaby:1b}"), EntityType.ZOMBIE, createNBTTag("{IsBaby:0}"), AgeingConfig.SERVER.babyToZombieAgeingTime.get()));
            INSTANCE.registerAgeing(new AgeingData("BabyToHusk", EntityType.HUSK, createNBTTag("{IsBaby:1b}"), EntityType.HUSK, createNBTTag("{IsBaby:0}"), AgeingConfig.SERVER.babyToZombieAgeingTime.get()));
        }

        if(AgeingConfig.SERVER.endermiteToShulkerAgeing.get()) {
            //TODO: addEndermite();
        }

        if(AgeingConfig.SERVER.skeletonToStrayAgeing.get()) {
            AgeingData skeletonToStray = new AgeingData("SkeletonToStray", EntityType.SKELETON, createNBTTag(""), EntityType.STRAY, createNBTTag(""), AgeingConfig.SERVER.skeletonToStrayAgeingTime.get());
            skeletonToStray.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(skeletonToStray, BiomeDictionary.Type.COLD)});
            INSTANCE.registerAgeing(skeletonToStray);
        }

        if(AgeingConfig.SERVER.strayToSkeletonAgeing.get()) {
            AgeingData strayToSkeleton = new AgeingData("StrayToSkeleton", EntityType.STRAY, createNBTTag(""), EntityType.SKELETON, createNBTTag(""), AgeingConfig.SERVER.strayToSkeletonAgeingTime.get());
            strayToSkeleton.setCriteria(new BaseCriteria[] { new BiomeTypeCriteria(strayToSkeleton, BiomeDictionary.Type.HOT)});
            INSTANCE.registerAgeing(strayToSkeleton);
        }

        if(AgeingConfig.SERVER.rabbitToKillerAgeing.get()) {
            AgeingData rabbitToKiller = new AgeingData("RabbitToKiller", EntityType.RABBIT, createNBTTag(""), EntityType.RABBIT, createNBTTag("{RabbitType:99}"), AgeingConfig.SERVER.rabbitToKillerAgeingTime.get());
            rabbitToKiller.setCriteria(new BaseCriteria[] { new LightCriteria(rabbitToKiller, AgeingConfig.SERVER.rabbitToKillerMinLight.get(), AgeingConfig.SERVER.rabbitToKillerMaxLight.get(), true, false)});
            INSTANCE.registerAgeing(rabbitToKiller);
        }

        if(AgeingConfig.SERVER.cowToMooshroomAgeing.get()) {
            AgeingData cowToMooshroom = new AgeingData("CowToMooshroom", EntityType.COW, createNBTTag(""), EntityType.MOOSHROOM, createNBTTag(""), AgeingConfig.SERVER.cowToMooshroomAgeingTime.get());
            cowToMooshroom.setCriteria(new BaseCriteria[] { new BlockBasedCriteria(cowToMooshroom, new Block[]{Blocks.MYCELIUM, Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM, Blocks.RED_MUSHROOM_BLOCK}, false, AgeingConfig.SERVER.cowToMooshroomAgeingRadius.get())});
            INSTANCE.registerAgeing(cowToMooshroom);
        }

        if(AgeingConfig.SERVER.skeletonToWitherSkeletonAgeing.get()) {
            AgeingData skeletonToWitherSkelly = new AgeingData("SkeletonToWitherSkelly", EntityType.SKELETON, createNBTTag(""), EntityType.WITHER_SKELETON, createNBTTag(""), AgeingConfig.SERVER.skeletonToWitherSkeletonAgeingTime.get());
            skeletonToWitherSkelly.setCriteria(new BaseCriteria[] { new DimensionCriteria(skeletonToWitherSkelly, new Integer[]{-1})});
            INSTANCE.registerAgeing(skeletonToWitherSkelly);
        }

        if(AgeingConfig.SERVER.slimeToMagmaCubeAgeing.get()) {
            AgeingData slimeToMagmaCube = new AgeingData("SlimeToMagmaCube", EntityType.SLIME, createNBTTag(""), EntityType.MAGMA_CUBE, createNBTTag(""), AgeingConfig.SERVER.slimeToMagmaCubeAgeingTime.get());
            slimeToMagmaCube.setCriteria(new BaseCriteria[] { new DimensionCriteria(slimeToMagmaCube, new Integer[]{-1})});
            INSTANCE.registerAgeing(slimeToMagmaCube);
        }

        if(AgeingConfig.SERVER.batToVexAgeing.get()) {
            INSTANCE.registerAgeing(new AgeingData("BatToVex", EntityType.BAT, createNBTTag(""), EntityType.VEX, createNBTTag(""), AgeingConfig.SERVER.batToVexAgeingTime.get()));
        }
    }

    public void registerAgeing(iAgeing ageing)
    {
        this.nameList.add(ageing.getName());
        nameToAgeing.put(ageing.getName(), ageing);
        sortList(ageing);
    }

    public boolean isIDUnique(String ID) {
        return !this.nameList.contains(ID);
    }

    private void sortList(@Nullable iAgeing ageing)
    {
        if(ageing != null)
            ageingList.add(ageing);

        ageingList.sort(Comparator.comparingInt(iAgeing::getAgeingTme));
    }

    public void triggerAgeing(World worldIn, BlockPos pos, PlayerEntity playerIn) {

    }

    public static CompoundNBT createNBTTag(String nbtData)
    {
        CompoundNBT tag = new CompoundNBT();

        try
        {
            String data = nbtData;
            if(data.startsWith("{") && data.endsWith("}"))
            {
                tag = JsonToNBT.getTagFromJson(data);
            }
            else
            {
                tag = JsonToNBT.getTagFromJson("{" + data + "}");
            }
        }
        catch (CommandSyntaxException nbtexception)
        {
            AgeingMobs.LOGGER.error("nope... " +  nbtexception);
        }

        return tag;
    }

    public void initializeMagicMap() {
        List<String> magicalBlocks = AgeingConfig.SERVER.magical_blocks.get();
        if(!magicalBlocks.isEmpty()) {
            this.importanceList = new HashMap<>();

            for(int i = 0; i < magicalBlocks.size(); i++) {
                String blockData = magicalBlocks.get(i);
                String[] blockInfo = blockData.split(";");
                if (blockInfo.length > 2) {
                    AgeingMobs.LOGGER.error("An error has occured. %s is using the wrong syntax.", new Object[]{blockData});
                } else if (blockInfo.length == 2) {
                    String blockName = blockInfo[0];
                    double importance = Double.valueOf(blockInfo[1]);
                    Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName));

                    if (block != null && !importanceList.containsKey(block)) {
                        importanceList.put(block, importance);
                    }
                }
            }
        }
    }

    public HashMap<Block, Double> getMagicMap() {
        return this.importanceList;
    }

    public void initializeMoonDimensions() {
        this.moonDimensions = AgeingConfig.SERVER.moon_dimensions.get();
    }

    public List<Integer> getMoonDimensions() {
        return this.moonDimensions;
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
