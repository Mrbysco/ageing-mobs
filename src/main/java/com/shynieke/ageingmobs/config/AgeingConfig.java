package com.shynieke.ageingmobs.config;

import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class AgeingConfig {

    public static class Server {

        //Conversion
        public final BooleanValue creeperAgeing;
        public final IntValue creeperAgeingTime;

        public final BooleanValue zombieToHuskAgeing;
        public final IntValue zombieToHuskAgeingTime;

        public final BooleanValue huskToZombieAgeing;
        public final IntValue huskToZombieAgeingTime;

        public final BooleanValue villagerToVindicatorAgeing;
        public final IntValue villagerToVindicatorAgeingTime;
        public final IntValue villagerToVindicatorMinLight;
        public final IntValue villagerToVindicatorMaxLight;

        public final BooleanValue vindicatorToEvokerAgeing;
        public final IntValue vindicatorToEvokerAgeingTime;

        public final BooleanValue guardianToElderAgeing;
        public final IntValue guardianToElderAgeingTime;
        public final IntValue guardianToElderAgeingMax;
        public final IntValue guardianToElderRange;

        public final BooleanValue babyToZombieAgeing;
        public final IntValue babyToZombieAgeingTime;

//        public final BooleanValue endermiteToShulkerAgeing;
//        public final IntValue endermiteToShulkerAgeingTime;
//        public final IntValue endermiteToShulkerBlockTime;

        public final BooleanValue skeletonToStrayAgeing;
        public final IntValue skeletonToStrayAgeingTime;

        public final BooleanValue strayToSkeletonAgeing;
        public final IntValue strayToSkeletonAgeingTime;

        public final BooleanValue rabbitToKillerAgeing;
        public final IntValue rabbitToKillerAgeingTime;
        public final IntValue rabbitToKillerMinLight;
        public final IntValue rabbitToKillerMaxLight;

        public final BooleanValue cowToMooshroomAgeing;
        public final IntValue cowToMooshroomAgeingTime;
        public final IntValue cowToMooshroomAgeingRadius;

        public final BooleanValue skeletonToWitherSkeletonAgeing;
        public final IntValue skeletonToWitherSkeletonAgeingTime;

        public final BooleanValue slimeToMagmaCubeAgeing;
        public final IntValue slimeToMagmaCubeAgeingTime;

        public final BooleanValue batToVexAgeing;
        public final IntValue batToVexAgeingTime;

        //Rest
        public final ConfigValue<List<? extends String>> magical_blocks;
        public final ConfigValue<List<? extends String>> moon_dimensions;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Creeper -> Charged Creeper")
                    .push("creeper_to_charged");

            creeperAgeing = builder
                    .comment("Setting this to false disables the creeper -> charged creeper ageing (Default: true)")
                    .define("creeperAgeing", true);

            creeperAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 900)")
                    .defineInRange("creeperAgeingTime", 900, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Zombie -> Husk")
                    .push("zombie_to_husk");

            zombieToHuskAgeing = builder
                    .comment("Setting this to false disables the zombie -> husk ageing (Default: true)")
                    .define("zombieToHuskAgeing", true);

            zombieToHuskAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 30)")
                    .defineInRange("zombieToHuskAgeingTime", 30, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Husk -> Zombie")
                    .push("husk_to_zombie");

            huskToZombieAgeing = builder
                    .comment("Setting this to false disables the husk -> zombie ageing (Default: true)")
                    .define("huskToZombieAgeing", true);

            huskToZombieAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 30)")
                    .defineInRange("huskToZombieAgeingTime", 30, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Villager -> Vindicator")
                    .push("villager_to_vindicator");

            villagerToVindicatorAgeing = builder
                    .comment("Setting this to false disables the villager -> vindicator ageing (Default: true)")
                    .define("villagerToVindicatorAgeing", true);

            villagerToVindicatorAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 1200)")
                    .defineInRange("villagerToVindicatorAgeingTime", 1200, 1, Integer.MAX_VALUE);

            villagerToVindicatorMinLight = builder
                    .comment("This specifies the minimum light level from which the mob starts to age (Default: 0)")
                    .defineInRange("villagerToVindicatorMinLight", 0, 0, 15);

            villagerToVindicatorMaxLight = builder
                    .comment("This specifies the maximum light level from which the mob starts to age (Default: 8)")
                    .defineInRange("villagerToVindicatorMaxLight", 8, 0, 15);

            builder.pop();

            builder.comment("Vindicator -> Evoker")
                    .push("vindicator_to_evoker");

            vindicatorToEvokerAgeing = builder
                    .comment("Setting this to false disables the vindicator -> evoker ageing (Default: true)")
                    .define("vindicatorToEvokerAgeing", true);

            vindicatorToEvokerAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 600)")
                    .defineInRange("vindicatorToEvokerAgeingTime", 600, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Guardian -> Elder Guardian")
                    .push("guardian_to_elder_guardian");

            guardianToElderAgeing = builder
                    .comment("Setting this to false disables the guardian -> elder ageing (Default: true)")
                    .define("guardianToElderAgeing", true);

            guardianToElderAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 1800)")
                    .defineInRange("guardianToElderAgeingTime", 1800, 1, Integer.MAX_VALUE);

            guardianToElderAgeingMax = builder
                    .comment("This specifies max amount of elder guardians in the area, 0 = infinite (Default: 2)")
                    .defineInRange("guardianToElderAgeingMax", 2, 0, Integer.MAX_VALUE);

            guardianToElderRange = builder
                    .comment("This specifies radius (not the diameter) around the original mob in which it checks for the boss mob, 16 = infinite (Default: 16)")
                    .defineInRange("guardianToElderRange", 16, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Baby Zombie -> Zombie")
                    .push("baby_zombie_to_zombie");

            babyToZombieAgeing = builder
                    .comment("Setting this to false disables the baby -> zombie ageing (Default: true)")
                    .define("babyToZombieAgeing", true);

            babyToZombieAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 1200)")
                    .defineInRange("babyToZombieAgeingTime", 1200, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Endermite -> Shulker")
                    .push("endermite_to_shulker");

//            endermiteToShulkerAgeing = builder
//                    .comment("Setting this to false disables the endermite -> shulker ageing (Default: true)")
//                    .define("endermiteToShulkerAgeing", true);
//
//            endermiteToShulkerAgeingTime = builder
//                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 360)")
//                    .defineInRange("endermiteToShulkerAgeingTime", 360, 1, Integer.MAX_VALUE);
//
//            endermiteToShulkerBlockTime = builder
//                    .comment("This specifies the time in second(s) which dictates how long you need to wait when the endermite insert themselves into a purpur block (Default: 60)")
//                    .defineInRange("endermiteToShulkerBlockTime", 60, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Skeleton -> Stray")
                    .push("skeleton_to_stray");

            skeletonToStrayAgeing = builder
                    .comment("Setting this to false disables the skeleton -> stray ageing (Default: true)")
                    .define("skeletonToStrayAgeing", true);

            skeletonToStrayAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 30)")
                    .defineInRange("skeletonToStrayAgeingTime", 30, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Stray -> Skeleton")
                    .push("stray_to_skeleton");

            strayToSkeletonAgeing = builder
                    .comment("Setting this to false disables the stray -> skeleton ageing (Default: true)")
                    .define("strayToSkeletonAgeing", true);

            strayToSkeletonAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 30)")
                    .defineInRange("strayToSkeletonAgeingTime", 30, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Skeleton -> Wither Skeleton")
                    .push("skeleton_wither_skeleton");

            skeletonToWitherSkeletonAgeing = builder
                    .comment("Setting this to false disables the skeleton -> wither skeleton ageing (Default: true)")
                    .define("skeletonToWitherSkeletonAgeing", true);

            skeletonToWitherSkeletonAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 120)")
                    .defineInRange("skeletonToWitherSkeletonAgeingTime", 120, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Rabbit -> Killer")
                    .push("rabbit_to_killer_rabbit");

            rabbitToKillerAgeing = builder
                    .comment("Setting this to false disables the rabbit -> killer rabbit ageing (Default: true)")
                    .define("rabbitToKillerAgeing", true);

            rabbitToKillerAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 300)")
                    .defineInRange("rabbitToKillerAgeingTime", 300, 1, Integer.MAX_VALUE);

            rabbitToKillerMinLight = builder
                    .comment("TThis specifies the minimum light level from which the mob starts to age (Default: 0)")
                    .defineInRange("rabbitToKillerMinLight", 0, 0, 15);

            rabbitToKillerMaxLight = builder
                    .comment("This specifies the maximum light level from which the mob starts to age (Default: 3)")
                    .defineInRange("rabbitToKillerMaxLight", 3, 0, 15);

            builder.pop();

            builder.comment("Cow -> Mooshroom")
                    .push("cow_to_mooshroom");

            cowToMooshroomAgeing = builder
                    .comment("Setting this to false disables the cow -> mooshroom ageing (Default: true)")
                    .define("cowToMooshroomAgeing", true);

            cowToMooshroomAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 900)")
                    .defineInRange("cowToMooshroomAgeingTime", 900, 1, Integer.MAX_VALUE);

            cowToMooshroomAgeingRadius = builder
                    .comment("This specifies radius (not the diameter) around the original mob in which it checks for magic blocks (Default: 5)")
                    .defineInRange("cowToMooshroomAgeingRadius", 5, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Slime -> Magma Cube")
                    .push("slime_to_magma_cube");

            slimeToMagmaCubeAgeing = builder
                    .comment("Setting this to false disables the slime -> magma cube ageing (Default: true)")
                    .define("slimeToMagmaCubeAgeing", true);

            slimeToMagmaCubeAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 120)")
                    .defineInRange("slimeToMagmaCubeAgeingTime", 120, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Bat -> Vex")
                    .push("bat_to_vex");

            batToVexAgeing = builder
                    .comment("Setting this to false disables the bat -> vex ageing (Default: true)")
                    .define("batToVexAgeing", true);

            batToVexAgeingTime = builder
                    .comment("This specifies the time in second(s) which dictates how long a mob needs to age (Default: 666)")
                    .defineInRange("batToVexAgeingTime", 666, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Other settings")
                    .push("other");

            String[] magicalList = new String[]
                    {
                            "minecraft:bookshelf;0.2",
                            "minecraft:brewing_stand;0.5",
                            "minecraft:enchanting_table;1"
                    };

            magical_blocks = builder
                    .comment("Blocks that are seen as magical, by removing the blocks they won't be seen as magical by the mod. syntax: modid:block;effectiveness")
                    .defineList("magical_blocks", Arrays.asList(magicalList), o -> (o instanceof String));

            String[] dimensionList = new String[]{ "minecraft:overworld" };
            moon_dimensions = builder
                    .comment("Dimensions that the mod recognizes having a moon. By default only has the overworld")
                    .defineList("moon_dimensions", Arrays.asList(dimensionList), o -> (o instanceof String));

            builder.pop();

        }
    }

    public static final ForgeConfigSpec serverSpec;
    public static final AgeingConfig.Server SERVER;
    static {
        final Pair<AgeingConfig.Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(AgeingConfig.Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        AgeingMobs.LOGGER.debug("Loaded Ageing Mobs' config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading configEvent) {
        AgeingMobs.LOGGER.fatal("Ageing Mobs' config just got changed on the file system!");

        AgeingRegistry.INSTANCE.initializeAgeing();
        AgeingRegistry.INSTANCE.initializeMagicMap();
        AgeingRegistry.INSTANCE.initializeMoonDimensions();
    }
}
