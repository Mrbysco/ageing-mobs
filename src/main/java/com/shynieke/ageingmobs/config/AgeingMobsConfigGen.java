package com.shynieke.ageingmobs.config;

import com.shynieke.ageingmobs.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
@Config.LangKey(Reference.MOD_ID + ".config.title")
public class AgeingMobsConfigGen {
	@Config.Comment("General Settings")
	@Config.Name("General Settings")
	public static General general = new General();
	
	@Config.Comment("List Settings")
	@Config.Name("List Settings")
	public static List list = new List();
	
	public static class General{
		@Config.Comment("Creeper -> Charged Creeper")
		@Config.Name("Creeper -> Charged Creeper")
		public ChargedCreeper chargedcreeper = new ChargedCreeper();
		public class ChargedCreeper {
			@Config.Comment("Setting this to false disables the creeper -> charged creeper ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean creeperAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 900)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int creeperAgeingTime = 900;
		}
		
		@Config.Comment("Zombie -> Husk")
		@Config.Name("Zombie -> Husk")
		public ZombieHusk zombiehusk = new ZombieHusk();
		public class ZombieHusk {
			@Config.Comment("Setting this to false disables the zombie -> husk ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean zombieToHuskAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 30)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int zombieToHuskAgeingTime = 30;
		}
		
		@Config.Comment("Husk -> Zombie")
		@Config.Name("Husk -> Zombie")
		public HuskZombie huskzombie = new HuskZombie();
		public class HuskZombie {
			@Config.Comment("Setting this to false disables the husk -> zombie ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean huskToZombieAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 30)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int huskToZombieAgeingTime = 30;
		}
		
		@Config.Comment("Villager -> Vindicator")
		@Config.Name("Villager -> Vindicator")
		public VillagerVindicator villagervindicator = new VillagerVindicator();
		public class VillagerVindicator {
			@Config.Comment("Setting this to false disables the villager -> vindicator ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean villagerToVindicatorAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 1200)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int villagerToVindicatorAgeingTime = 1200;
			
			@Config.Comment("This specifies the minimum light level from which the mob starts to age (Default: 0)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0, max = 15)
			public int minimumLightLevel = 0;
			
			@Config.Comment("This specifies the maximum light level from which the mob starts to age (Default: 8)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0, max = 15)
			public int maximumLightLevel = 8;
		}
		
		@Config.Comment("Vindicator -> Evoker")
		@Config.Name("Vindicator -> Evoker")
		public VindicatorEvoker vindicatorevoker = new VindicatorEvoker();
		public class VindicatorEvoker {
			@Config.Comment("Setting this to false disables the vindicator -> evoker ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean vindicatorToEvokerAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 600)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int vindicatorToEvokerAgeingTime = 600;
		}
		
		@Config.Comment("Guardian -> Elder Guardian")
		@Config.Name("Guardian -> Elder Guardian")
		public GuardianElder guardianelder = new GuardianElder();
		public class GuardianElder {
			@Config.Comment("Setting this to false disables the guardian -> elder ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean guardianToElderAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 1800)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int guardianToElderAgeingTime = 1800;
			
			@Config.Comment("This specifies max amount of elder guardians in the area, 0 = infinite (Default: 3)")
			public int guardianToElderAgeingMax = 3;
			
			@Config.Comment("This specifies radius (not the diameter) around the original mob in which it checks for the boss mob (16 = infinite (Default: 16)")
			@Config.RangeInt(min = 1)
			public int guardianToElderRange = 16;
		}
		
		@Config.Comment("Baby Zombie -> Zombie")
		@Config.Name("Baby Zombie -> Zombie")
		public BabyZombie babyzombie = new BabyZombie();
		public class BabyZombie {
			@Config.Comment("Setting this to false disables the baby -> zombie ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean babyToZombieAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 1200)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int babyToZombieAgeingTime = 1200;
		}
		
		@Config.Comment("Endermite -> Shulker")
		@Config.Name("Endermite -> Shulker")
		public EndermiteShulker endermiteshulker = new EndermiteShulker();
		public class EndermiteShulker {
			@Config.Comment("Setting this to false disables the endermite -> shulker ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean endermiteToShulkerAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 360)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int endermiteToShulkerAgeingTime = 360;
			
			@Config.Comment("This specifies the time in second which dictates how long you need to wait when the endermite insert themselves into a purpur block (Default: 60)")
			@Config.RangeInt(min = 1)
			public int endermiteToShulkerBlockTime = 60;
		}
		
		@Config.Comment("Skeleton -> Stray")
		@Config.Name("Skeleton -> Stray")
		public SkeletonStray skeletonstray = new SkeletonStray();
		public class SkeletonStray {
			@Config.Comment("Setting this to false disables the skeleton -> stray ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean skeletonToStrayAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 30)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int skeletonToStrayAgeingTime = 30;
		}
		
		@Config.Comment("Stray -> Skeleton")
		@Config.Name("Stray -> Skeleton")
		public StraySkeleton strayskeleton = new StraySkeleton();
		public class StraySkeleton {
			@Config.Comment("Setting this to false disables the stray -> skeleton ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean strayToSkeletonAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 30)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int strayToSkeletonAgeingTime = 30;
		}
		
		@Config.Comment("Rabbit -> Killer")
		@Config.Name("Rabbit -> Killer")
		public RabbitKiller rabbitkiller = new RabbitKiller();
		public class RabbitKiller {
			@Config.Comment("Setting this to false disables the rabbit -> killer rabbit ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean rabbitToKillerAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 300)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int rabbitToKillerAgeingTime = 300;
			
			@Config.Comment("This specifies the minimum light level from which the mob starts to age (Default: 0)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0, max = 15)
			public int minimumLightLevel = 0;
			
			@Config.Comment("This specifies the maximum light level from which the mob starts to age (Default: 3)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 0, max = 15)
			public int maximumLightLevel = 3;
		}
		
		@Config.Comment("Cow -> Mooshroom")
		@Config.Name("Cow -> Mooshroom")
		public CowMooshroom cowmooshroom = new CowMooshroom();
		public class CowMooshroom {
			@Config.Comment("Setting this to false disables the cow -> mooshroom ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean cowToMooshroomAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 900)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int cowToMooshroomAgeingTime = 900;
			
			@Config.Comment("This specifies radius (not the diameter) around the original mob in which it checks for magic blocks (Default: 5)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int cowToMooshroomAgeingRadius = 5;
		}
		
		@Config.Comment("Skeleton -> Wither Skeleton")
		@Config.Name("Skeleton -> Wither Skeleton")
		public SkeletonWitherSkeleton skeletonwitherskelly = new SkeletonWitherSkeleton();
		public class SkeletonWitherSkeleton {
			@Config.Comment("Setting this to false disables the skeleton -> wither skeleton ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean skeletonToWitherSkeletonAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 120)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int skeletonToWitherSkeletonAgeingTime = 120;
		}
		
		@Config.Comment("Slime -> Magma Cube")
		@Config.Name("Slime -> Magma Cube")
		public SlimeMagmaCube slimemagma = new SlimeMagmaCube();
		public class SlimeMagmaCube {
			@Config.Comment("Setting this to false disables the slime -> magma cube ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean slimeToMagmaCubeAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 120)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int slimeToMagmaCubeAgeingTime = 120;
		}
		
		@Config.Comment("Bat -> Vex")
		@Config.Name("Bat -> Vex")
		public BatVex batvex = new BatVex();
		public class BatVex {
			@Config.Comment("Setting this to false disables the bat -> vex ageing (Default: true)")
			@Config.RequiresMcRestart
			public boolean batToVexAgeing = true;
			
			@Config.Comment("This specifies the time in second which dictates how long a mob needs to age (Default: 666)")
			@Config.RequiresMcRestart
			@Config.RangeInt(min = 1)
			public int batToVexAgeingTime = 666;
		}
	}
	
	public static class List{
		@Config.Comment("Blocks that are seen as magical, by removing the blocks they won't be seen as magical by the mod. syntax: modid:block;effectiveness")
		@Config.Name("Magical Block List")
		public String[] magical_blocks = new String[]
		{
			"minecraft:bookshelf;0.2",
			"minecraft:brewing_stand;0.5",
			"minecraft:enchanting_table;1"
		};
		
		@Config.Comment("Dimensions that the mod recognizes having a moon. By default only has the overworld")
		@Config.Name("Dimensions with a moon")
		public String[] moon_dimensions = new String[]
		{
			"0"
		};
	}
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Reference.MOD_ID)) {
				ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}
