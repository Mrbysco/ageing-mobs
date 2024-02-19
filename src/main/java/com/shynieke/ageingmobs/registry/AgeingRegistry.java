package com.shynieke.ageingmobs.registry;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.config.AgeingConfig;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BiomeTypeCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BlockBasedCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.BossCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.DimensionCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.EntityStateCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.LightCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.MagicCriteria;
import com.shynieke.ageingmobs.registry.ageing.criteria.WeatherCriteria;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

public class AgeingRegistry {
	public static AgeingRegistry INSTANCE = new AgeingRegistry();

	public static LinkedHashMap<ResourceLocation, List<AgeingData>> ageingList = new LinkedHashMap<>();

	private static HashMap<Block, Double> importanceList = new HashMap<>();
	private static List<ResourceLocation> moonDimensions = Lists.newArrayList();

	public void initializeAgeing() {
		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.CREEPER), "CreeperToCharged") && AgeingConfig.COMMON.creeperAgeing.get()) {
			AgeingData creeperToCharged = new AgeingData("CreeperToCharged", EntityType.CREEPER, createNBTTag(""), EntityType.CREEPER, createNBTTag("{powered:1b}"), AgeingConfig.COMMON.creeperAgeingTime.get());
			creeperToCharged.setCriteria(new BaseCriteria[]{new WeatherCriteria(creeperToCharged, "thunder")});
			INSTANCE.registerAgeing(creeperToCharged);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.CREEPER), "CreeperToCharged")) {
			AgeingData creeperToCharged = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.CREEPER), "CreeperToCharged");
			int ageingTime = AgeingConfig.COMMON.creeperAgeingTime.get();
			if (creeperToCharged.getAgeingTme() != ageingTime) {
				creeperToCharged.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(creeperToCharged);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE), "ZombieToHusk") && AgeingConfig.COMMON.zombieToHuskAgeing.get()) {
			AgeingData zombieToHusk = new AgeingData("ZombieToHusk", EntityType.ZOMBIE, createNBTTag(""), EntityType.HUSK, createNBTTag(""), AgeingConfig.COMMON.zombieToHuskAgeingTime.get());
			zombieToHusk.setCriteria(new BaseCriteria[]{new BiomeTypeCriteria(zombieToHusk, Tags.Biomes.IS_HOT)});
			INSTANCE.registerAgeing(zombieToHusk);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE), "ZombieToHusk")) {
			AgeingData zombieToHusk = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE), "ZombieToHusk");
			int ageingTime = AgeingConfig.COMMON.zombieToHuskAgeingTime.get();
			if (zombieToHusk.getAgeingTme() != ageingTime) {
				zombieToHusk.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(zombieToHusk);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE), "BabyZombieToBabyHusk") && AgeingConfig.COMMON.zombieToHuskAgeing.get()) {
			AgeingData babyZombieToBabyHusk = new AgeingData("BabyZombieToBabyHusk", EntityType.ZOMBIE, createNBTTag("{IsBaby:1b}"), EntityType.HUSK, createNBTTag("{IsBaby:1b}"), AgeingConfig.COMMON.zombieToHuskAgeingTime.get());
			babyZombieToBabyHusk.setCriteria(new BaseCriteria[]{new BiomeTypeCriteria(babyZombieToBabyHusk, Tags.Biomes.IS_HOT)});
			INSTANCE.registerAgeing(babyZombieToBabyHusk);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE), "BabyZombieToBabyHusk")) {
			AgeingData babyZombieToBabyHusk = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE), "BabyZombieToBabyHusk");
			int ageingTime = AgeingConfig.COMMON.zombieToHuskAgeingTime.get();
			if (babyZombieToBabyHusk.getAgeingTme() != ageingTime) {
				babyZombieToBabyHusk.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(babyZombieToBabyHusk);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK), "HuskToZombie") && AgeingConfig.COMMON.huskToZombieAgeing.get()) {
			AgeingData huskToZombie = new AgeingData("HuskToZombie", EntityType.HUSK, createNBTTag(""), EntityType.ZOMBIE, createNBTTag(""), AgeingConfig.COMMON.huskToZombieAgeingTime.get());
			huskToZombie.setCriteria(new BaseCriteria[]{new BiomeTypeCriteria(huskToZombie, Tags.Biomes.IS_COLD)});
			INSTANCE.registerAgeing(huskToZombie);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK), "HuskToZombie")) {
			AgeingData huskToZombie = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK), "HuskToZombie");
			int ageingTime = AgeingConfig.COMMON.huskToZombieAgeingTime.get();
			if (huskToZombie.getAgeingTme() != ageingTime) {
				huskToZombie.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(huskToZombie);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK), "BabyHuskToBabyZombie") && AgeingConfig.COMMON.huskToZombieAgeing.get()) {
			AgeingData babyHuskToBabyZombie = new AgeingData("BabyHuskToBabyZombie", EntityType.HUSK, createNBTTag("{IsBaby:1b}"), EntityType.ZOMBIE, createNBTTag("{IsBaby:1b}"), AgeingConfig.COMMON.huskToZombieAgeingTime.get());
			babyHuskToBabyZombie.setCriteria(new BaseCriteria[]{new BiomeTypeCriteria(babyHuskToBabyZombie, Tags.Biomes.IS_COLD)});
			INSTANCE.registerAgeing(babyHuskToBabyZombie);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK), "BabyHuskToBabyZombie")) {
			AgeingData babyHuskToBabyZombie = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK), "BabyHuskToBabyZombie");
			int ageingTime = AgeingConfig.COMMON.huskToZombieAgeingTime.get();
			if (babyHuskToBabyZombie.getAgeingTme() != ageingTime) {
				babyHuskToBabyZombie.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(babyHuskToBabyZombie);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PILLAGER), "PassivePillagerToVillager") && AgeingConfig.COMMON.passivePillagerToVillagerAgeing.get()) {
			AgeingData passivePillagerToVillager = new AgeingData("PassivePillagerToVillager", EntityType.PILLAGER, createNBTTag(""), EntityType.VILLAGER, createNBTTag(""), AgeingConfig.COMMON.passivePillagerToVillagerAgeingTime.get());
			passivePillagerToVillager.setCriteria(new BaseCriteria[]{new EntityStateCriteria(passivePillagerToVillager, entity -> entity instanceof LivingEntity living && !living.isHolding(stack -> stack.getItem() instanceof CrossbowItem))});
			INSTANCE.registerAgeing(passivePillagerToVillager);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PILLAGER), "PassivePillagerToVillager")) {
			AgeingData passivePillagerToVillager = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PILLAGER), "PassivePillagerToVillager");
			int ageingTime = AgeingConfig.COMMON.passivePillagerToVillagerAgeingTime.get();
			if (passivePillagerToVillager.getAgeingTme() != ageingTime) {
				passivePillagerToVillager.setAgeingTme(ageingTime);
				INSTANCE.registerAgeing(passivePillagerToVillager);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VILLAGER), "VillagerToVindicator") && AgeingConfig.COMMON.villagerToVindicatorAgeing.get()) {
			AgeingData villagerToVindicator = new AgeingData("VillagerToVindicator", EntityType.VILLAGER, createNBTTag(""), EntityType.VINDICATOR, createNBTTag(""), AgeingConfig.COMMON.villagerToVindicatorAgeingTime.get());
			villagerToVindicator.setCriteria(new BaseCriteria[]{new LightCriteria(villagerToVindicator, AgeingConfig.COMMON.villagerToVindicatorMinLight.get(), AgeingConfig.COMMON.villagerToVindicatorMaxLight.get(), false, true)});
			INSTANCE.registerAgeing(villagerToVindicator);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VILLAGER), "VillagerToVindicator")) {
			AgeingData villagerToVindicator = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VILLAGER), "VillagerToVindicator");
			boolean ageingChanged = false;
			boolean criteriaChanged = false;
			int ageingTime = AgeingConfig.COMMON.villagerToVindicatorAgeingTime.get();
			if (villagerToVindicator.getAgeingTme() != ageingTime) {
				villagerToVindicator.setAgeingTme(ageingTime);
				ageingChanged = true;
			}
			BaseCriteria[] criterias = villagerToVindicator.getCriteria();
			if (criterias.length > 0) {
				for (BaseCriteria criteria : criterias) {
					if (criteria instanceof LightCriteria lightCriteria) {
						int minLight = AgeingConfig.COMMON.villagerToVindicatorMinLight.get();
						int maxLight = AgeingConfig.COMMON.villagerToVindicatorMaxLight.get();
						if (lightCriteria.getLightLevelMin() != minLight) {
							lightCriteria.setLightLevelMin(minLight);
							criteriaChanged = true;
						}
						if (lightCriteria.getLightLevelMax() != maxLight) {
							lightCriteria.setLightLevelMax(maxLight);
							criteriaChanged = true;
						}
					}
				}
			}
			if (criteriaChanged) {
				villagerToVindicator.setCriteria(criterias);
			}
			if (ageingChanged || criteriaChanged) {
				INSTANCE.replaceAgeing(villagerToVindicator);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VINDICATOR), "VindicatorToEvoker") && AgeingConfig.COMMON.vindicatorToEvokerAgeing.get()) {
			AgeingData vindicatorToEvoker = new AgeingData("VindicatorToEvoker", EntityType.VINDICATOR, createNBTTag(""), EntityType.EVOKER, createNBTTag(""), AgeingConfig.COMMON.vindicatorToEvokerAgeingTime.get());
			vindicatorToEvoker.setCriteria(new BaseCriteria[]{new MagicCriteria(vindicatorToEvoker, 5)});
			INSTANCE.registerAgeing(vindicatorToEvoker);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VINDICATOR), "VindicatorToEvoker")) {
			AgeingData vindicatorToEvoker = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VINDICATOR), "VindicatorToEvoker");
			int ageingTime = AgeingConfig.COMMON.vindicatorToEvokerAgeingTime.get();
			if (vindicatorToEvoker.getAgeingTme() != ageingTime) {
				vindicatorToEvoker.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(vindicatorToEvoker);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.GUARDIAN), "GuardianToElder") && AgeingConfig.COMMON.guardianToElderAgeing.get()) {
			AgeingData guardianToElder = new AgeingData("GuardianToElder", EntityType.GUARDIAN, createNBTTag(""), EntityType.ELDER_GUARDIAN, createNBTTag(""), AgeingConfig.COMMON.guardianToElderAgeingTime.get());
			guardianToElder.setCriteria(new BaseCriteria[]{new BossCriteria(guardianToElder, AgeingConfig.COMMON.guardianToElderAgeingMax.get(), AgeingConfig.COMMON.guardianToElderRange.get())});
			INSTANCE.registerAgeing(guardianToElder);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.GUARDIAN), "GuardianToElder")) {
			AgeingData guardianToElder = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.GUARDIAN), "GuardianToElder");
			boolean ageingChanged = false;
			boolean criteriaChanged = false;
			int ageingTime = AgeingConfig.COMMON.guardianToElderAgeingTime.get();
			if (guardianToElder.getAgeingTme() != ageingTime) {
				guardianToElder.setAgeingTme(ageingTime);
				ageingChanged = true;
			}
			BaseCriteria[] criterias = guardianToElder.getCriteria();
			if (criterias.length > 0) {
				for (BaseCriteria criteria : criterias) {
					if (criteria instanceof BossCriteria bossCriteria) {
						int ageingMax = AgeingConfig.COMMON.guardianToElderAgeingMax.get();
						int range = AgeingConfig.COMMON.guardianToElderRange.get();
						if (bossCriteria.getMaxInArea() != ageingMax) {
							bossCriteria.setMaxInArea(ageingMax);
							criteriaChanged = true;
						}
						if (bossCriteria.getCheckRadius() != range) {
							bossCriteria.setCheckRadius(range);
							criteriaChanged = true;
						}
					}
				}
			}
			if (criteriaChanged) {
				guardianToElder.setCriteria(criterias);
			}
			if (ageingChanged || criteriaChanged) {
				INSTANCE.replaceAgeing(guardianToElder);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE), "BabyToZombie") && AgeingConfig.COMMON.babyToZombieAgeing.get()) {
			INSTANCE.registerAgeing(new AgeingData("BabyToZombie", EntityType.ZOMBIE, createNBTTag("{IsBaby:1b}"), EntityType.ZOMBIE, createNBTTag("{IsBaby:0b}"), AgeingConfig.COMMON.babyToZombieAgeingTime.get()));
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE), "BabyToZombie")) {
			AgeingData babyToZombie = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE), "BabyToZombie");
			int ageingTime = AgeingConfig.COMMON.babyToZombieAgeingTime.get();
			if (babyToZombie.getAgeingTme() != ageingTime) {
				babyToZombie.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(babyToZombie);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK), "BabyToHusk") && AgeingConfig.COMMON.babyToZombieAgeing.get()) {
			INSTANCE.registerAgeing(new AgeingData("BabyToHusk", EntityType.HUSK, createNBTTag("{IsBaby:1b}"), EntityType.HUSK, createNBTTag("{IsBaby:0b}"), AgeingConfig.COMMON.babyToZombieAgeingTime.get()));
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK), "BabyToHusk")) {
			AgeingData babyToHusk = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK), "BabyToHusk");
			int ageingTime = AgeingConfig.COMMON.babyToZombieAgeingTime.get();
			if (babyToHusk.getAgeingTme() != ageingTime) {
				babyToHusk.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(babyToHusk);
			}
		}

		//if(AgeingConfig.SERVER.endermiteToShulkerAgeing.get()) {
		//TODO: addEndermite();
		//}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SKELETON), "SkeletonToStray") && AgeingConfig.COMMON.skeletonToStrayAgeing.get()) {
			AgeingData skeletonToStray = new AgeingData("SkeletonToStray", EntityType.SKELETON, createNBTTag(""), EntityType.STRAY, createNBTTag(""), AgeingConfig.COMMON.skeletonToStrayAgeingTime.get());
			skeletonToStray.setCriteria(new BaseCriteria[]{new BiomeTypeCriteria(skeletonToStray, Tags.Biomes.IS_COLD)});
			INSTANCE.registerAgeing(skeletonToStray);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SKELETON), "SkeletonToStray")) {
			AgeingData skeletonToStray = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SKELETON), "SkeletonToStray");
			int ageingTime = AgeingConfig.COMMON.skeletonToStrayAgeingTime.get();
			if (skeletonToStray.getAgeingTme() != ageingTime) {
				skeletonToStray.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(skeletonToStray);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.STRAY), "StrayToSkeleton") && AgeingConfig.COMMON.strayToSkeletonAgeing.get()) {
			AgeingData strayToSkeleton = new AgeingData("StrayToSkeleton", EntityType.STRAY, createNBTTag(""), EntityType.SKELETON, createNBTTag(""), AgeingConfig.COMMON.strayToSkeletonAgeingTime.get());
			strayToSkeleton.setCriteria(new BaseCriteria[]{new BiomeTypeCriteria(strayToSkeleton, Tags.Biomes.IS_HOT)});
			INSTANCE.registerAgeing(strayToSkeleton);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.STRAY), "StrayToSkeleton")) {
			AgeingData strayToSkeleton = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.STRAY), "StrayToSkeleton");
			int ageingTime = AgeingConfig.COMMON.strayToSkeletonAgeingTime.get();
			if (strayToSkeleton.getAgeingTme() != ageingTime) {
				strayToSkeleton.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(strayToSkeleton);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.RABBIT), "RabbitToKiller") && AgeingConfig.COMMON.rabbitToKillerAgeing.get()) {
			AgeingData rabbitToKiller = new AgeingData("RabbitToKiller", EntityType.RABBIT, createNBTTag(""), EntityType.RABBIT, createNBTTag("{RabbitType:99}"), AgeingConfig.COMMON.rabbitToKillerAgeingTime.get());
			rabbitToKiller.setCriteria(new BaseCriteria[]{new LightCriteria(rabbitToKiller, AgeingConfig.COMMON.rabbitToKillerMinLight.get(), AgeingConfig.COMMON.rabbitToKillerMaxLight.get(), true, false)});
			INSTANCE.registerAgeing(rabbitToKiller);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.RABBIT), "RabbitToKiller")) {
			AgeingData rabbitToKiller = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.RABBIT), "RabbitToKiller");
			boolean ageingChanged = false;
			boolean criteriaChanged = false;
			int ageingTime = AgeingConfig.COMMON.rabbitToKillerAgeingTime.get();
			if (rabbitToKiller.getAgeingTme() != ageingTime) {
				rabbitToKiller.setAgeingTme(ageingTime);
				ageingChanged = true;
			}
			BaseCriteria[] criterias = rabbitToKiller.getCriteria();
			if (criterias.length > 0) {
				for (BaseCriteria criteria : criterias) {
					if (criteria instanceof LightCriteria lightCriteria) {
						int minLight = AgeingConfig.COMMON.rabbitToKillerMinLight.get();
						int maxLight = AgeingConfig.COMMON.rabbitToKillerMaxLight.get();
						if (lightCriteria.getLightLevelMin() != minLight) {
							lightCriteria.setLightLevelMin(minLight);
							criteriaChanged = true;
						}
						if (lightCriteria.getLightLevelMax() != maxLight) {
							lightCriteria.setLightLevelMax(maxLight);
							criteriaChanged = true;
						}
					}
				}
			}
			if (criteriaChanged) {
				rabbitToKiller.setCriteria(criterias);
			}
			if (ageingChanged || criteriaChanged) {
				INSTANCE.replaceAgeing(rabbitToKiller);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.COW), "CowToMooshroom") && AgeingConfig.COMMON.cowToMooshroomAgeing.get()) {
			AgeingData cowToMooshroom = new AgeingData("CowToMooshroom", EntityType.COW, createNBTTag(""), EntityType.MOOSHROOM, createNBTTag(""), AgeingConfig.COMMON.cowToMooshroomAgeingTime.get());
			cowToMooshroom.setCriteria(new BaseCriteria[]{new BlockBasedCriteria(cowToMooshroom, new Block[]{Blocks.MYCELIUM, Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM, Blocks.RED_MUSHROOM_BLOCK}, false, AgeingConfig.COMMON.cowToMooshroomAgeingRadius.get())});
			INSTANCE.registerAgeing(cowToMooshroom);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.COW), "CowToMooshroom")) {
			AgeingData cowToMooshroom = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.COW), "CowToMooshroom");
			boolean ageingChanged = false;
			boolean criteriaChanged = false;
			int ageingTime = AgeingConfig.COMMON.cowToMooshroomAgeingTime.get();
			if (cowToMooshroom.getAgeingTme() != ageingTime) {
				cowToMooshroom.setAgeingTme(ageingTime);
				ageingChanged = true;
			}
			BaseCriteria[] criterias = cowToMooshroom.getCriteria();
			if (criterias.length > 0) {
				for (BaseCriteria criteria : criterias) {
					if (criteria instanceof BlockBasedCriteria blockCriteria) {
						int radius = AgeingConfig.COMMON.cowToMooshroomAgeingRadius.get();
						if (blockCriteria.getRadius() != radius) {
							blockCriteria.setRadius(radius);
							criteriaChanged = true;
						}
					}
				}
			}
			if (criteriaChanged) {
				cowToMooshroom.setCriteria(criterias);
			}
			if (ageingChanged || criteriaChanged) {
				INSTANCE.replaceAgeing(cowToMooshroom);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SKELETON), "SkeletonToWitherSkelly") && AgeingConfig.COMMON.skeletonToWitherSkeletonAgeing.get()) {
			AgeingData skeletonToWitherSkelly = new AgeingData("SkeletonToWitherSkelly", EntityType.SKELETON, createNBTTag(""), EntityType.WITHER_SKELETON, createNBTTag(""), AgeingConfig.COMMON.skeletonToWitherSkeletonAgeingTime.get());
			skeletonToWitherSkelly.setCriteria(new BaseCriteria[]{new DimensionCriteria(skeletonToWitherSkelly, new ResourceLocation[]{new ResourceLocation("the_nether")})});

			INSTANCE.registerAgeing(skeletonToWitherSkelly);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SKELETON), "SkeletonToWitherSkelly")) {
			AgeingData skeletonToWitherSkelly = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SKELETON), "SkeletonToWitherSkelly");
			int ageingTime = AgeingConfig.COMMON.skeletonToWitherSkeletonAgeingTime.get();
			if (skeletonToWitherSkelly.getAgeingTme() != ageingTime) {
				skeletonToWitherSkelly.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(skeletonToWitherSkelly);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SLIME), "SlimeToMagmaCube") && AgeingConfig.COMMON.slimeToMagmaCubeAgeing.get()) {
			AgeingData slimeToMagmaCube = new AgeingData("SlimeToMagmaCube", EntityType.SLIME, createNBTTag(""), EntityType.MAGMA_CUBE, createNBTTag(""), AgeingConfig.COMMON.slimeToMagmaCubeAgeingTime.get());
			slimeToMagmaCube.setCriteria(new BaseCriteria[]{new DimensionCriteria(slimeToMagmaCube, new ResourceLocation[]{new ResourceLocation("the_nether")})});
			INSTANCE.registerAgeing(slimeToMagmaCube);
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SLIME), "SlimeToMagmaCube")) {
			AgeingData slimeToMagmaCube = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SLIME), "SlimeToMagmaCube");
			int ageingTime = AgeingConfig.COMMON.slimeToMagmaCubeAgeingTime.get();
			if (slimeToMagmaCube.getAgeingTme() != ageingTime) {
				slimeToMagmaCube.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(slimeToMagmaCube);
			}
		}

		if (INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.BAT), "BatToVex") && AgeingConfig.COMMON.batToVexAgeing.get()) {
			INSTANCE.registerAgeing(new AgeingData("BatToVex", EntityType.BAT, createNBTTag(""), EntityType.VEX, createNBTTag(""), AgeingConfig.COMMON.batToVexAgeingTime.get()));
		} else if (!INSTANCE.isIDUnique(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.BAT), "BatToVex")) {
			AgeingData batToVex = INSTANCE.getByID(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.BAT), "BatToVex");
			int ageingTime = AgeingConfig.COMMON.batToVexAgeingTime.get();
			if (batToVex.getAgeingTme() != ageingTime) {
				batToVex.setAgeingTme(ageingTime);
				INSTANCE.replaceAgeing(batToVex);
			}
		}
	}

	public void registerAgeing(AgeingData ageing) {
		ResourceLocation resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(ageing.getEntity());
		if (resourceLocation != null) {
			if (ageingList.containsKey(resourceLocation)) {
				List<AgeingData> dataList = new ArrayList<>(ageingList.get(resourceLocation));
				dataList.add(ageing);
				ageingList.put(resourceLocation, dataList);
			} else {
				ageingList.put(resourceLocation, Collections.singletonList(ageing));
			}
		} else {
			AgeingMobs.LOGGER.error(String.format("Failed to add Ageing Data with ID %s because the entity's resourcelocation is null", ageing.getName()));
		}
	}

	public void removeAgeing(AgeingData ageing) {
		ResourceLocation resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(ageing.getEntity());
		if (resourceLocation != null) {
			if (ageingList.containsKey(resourceLocation)) {
				List<AgeingData> dataList = new ArrayList<>(ageingList.get(resourceLocation));
				dataList.remove(ageing);
				if (dataList.isEmpty()) {
					ageingList.remove(resourceLocation);
				} else {
					ageingList.put(resourceLocation, dataList);
				}
			} else {
				AgeingMobs.LOGGER.error(String.format("Tried to remove Ageing Data with id %s but it didn't exist", ageing.getName()));
			}
		} else {
			AgeingMobs.LOGGER.error(String.format("Failed to remove Ageing Data with ID %s because the entity's resourcelocation is null", ageing.getName()));
		}
	}

	public void replaceAgeing(AgeingData ageing) {
		ResourceLocation resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(ageing.getEntity());
		String uniqueID = ageing.getName();
		if (resourceLocation != null) {
			if (ageingList.containsKey(resourceLocation)) {
				List<AgeingData> dataList = ageingList.get(resourceLocation);
				if (dataList == null) {
					dataList = Lists.newArrayList();
				}

				boolean found = false;
				if (!dataList.isEmpty()) {
					ListIterator<AgeingData> iterator = dataList.listIterator();
					while (iterator.hasNext()) {
						AgeingData data = iterator.next();
						if (data.getName().equals(uniqueID)) {
							iterator.set(ageing);
							found = true;
						}
					}

					if (!found) {
						AgeingMobs.LOGGER.error(String.format("Tried to change Ageing Data with id %s but it didn't exist", ageing.getName()));
					}
				}
			}
		} else {
			AgeingMobs.LOGGER.error(String.format("Failed to remove Ageing Data with ID %s because the entity's resourcelocation is null", ageing.getName()));
		}
	}

	public static List<AgeingData> getDataList(ResourceLocation resourceLocation) {
		if (ageingList.containsKey(resourceLocation)) {
			List<AgeingData> dataList = ageingList.get(resourceLocation);
			if (dataList == null) {
				return new ArrayList<>();
			}
			return dataList;
		}
		return new ArrayList<>();
	}

	public static boolean hasEntityAgeing(ResourceLocation resourceLocation) {
		return ageingList.containsKey(resourceLocation);
	}

	public AgeingData getByID(ResourceLocation resourceLocation, String uniqueID) {
		if (ageingList.containsKey(resourceLocation)) {
			List<AgeingData> dataList = ageingList.get(resourceLocation);
			if (dataList == null) {
				dataList = Lists.newArrayList();
			}
			if (!dataList.isEmpty()) {
				for (AgeingData data : dataList) {
					if (data.getName().equals(uniqueID)) {
						return data;
					}
				}
			}
		}
		return null;
	}

	public AgeingData getByID(String uniqueID) {
		for (Map.Entry<ResourceLocation, List<AgeingData>> entry : ageingList.entrySet()) {
			List<AgeingData> dataList = entry.getValue();
			if (dataList != null && !dataList.isEmpty()) {
				for (AgeingData data : dataList) {
					if (data.getName().equals(uniqueID)) {
						return data;
					}
				}
			}
		}
		return null;
	}

	public boolean isIDUnique(ResourceLocation resourceLocation, String uniqueID) {
		if (ageingList.containsKey(resourceLocation)) {
			List<AgeingData> dataList = ageingList.get(resourceLocation);
			if (dataList == null) {
				dataList = Lists.newArrayList();
			}
			if (!dataList.isEmpty()) {
				for (AgeingData data : dataList) {
					if (data.getName().equals(uniqueID)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static CompoundTag createNBTTag(String nbtData) {
		CompoundTag tag = new CompoundTag();

		try {
			if (nbtData.startsWith("{") && nbtData.endsWith("}")) {
				tag = TagParser.parseTag(nbtData);
			} else {
				tag = TagParser.parseTag("{" + nbtData + "}");
			}
		} catch (CommandSyntaxException nbtexception) {
			AgeingMobs.LOGGER.error("nope... " + nbtexception.getMessage());
		}

		return tag;
	}

	public void initializeMagicMap() {
		List<? extends String> magicalBlocks = AgeingConfig.COMMON.magical_blocks.get();
		if (!magicalBlocks.isEmpty()) {
			importanceList = new HashMap<>();

			for (String blockData : magicalBlocks) {
				String[] blockInfo = blockData.split(";");
				if (blockInfo.length > 2) {
					AgeingMobs.LOGGER.error("An error has occured. " + blockData + " is using the wrong syntax.");
				} else if (blockInfo.length == 2) {
					String blockName = blockInfo[0];
					double importance = Double.parseDouble(blockInfo[1]);
					Optional<Block> blockHolder = BuiltInRegistries.BLOCK.getOptional(new ResourceLocation(blockName));

					if (blockHolder.isPresent() && !importanceList.containsKey(blockHolder.get())) {
						importanceList.put(blockHolder.get(), importance);
					}
				}
			}
		}
	}

	public HashMap<Block, Double> getMagicMap() {
		return importanceList;
	}

	public void initializeMoonDimensions() {
		List<? extends String> dimensions = AgeingConfig.COMMON.moon_dimensions.get();
		List<ResourceLocation> dimensionList = new ArrayList<>();
		for (String string : dimensions) {
			ResourceLocation dim = new ResourceLocation(string);
			if (!dimensionList.contains(dim)) {
				dimensionList.add(dim);
			}
		}
		moonDimensions = dimensionList;
	}

	public List<ResourceLocation> getMoonDimensions() {
		return moonDimensions;
	}

	public static CompoundTag entityToNBT(Entity theEntity) {
		CompoundTag compoundTag = theEntity.saveWithoutId(new CompoundTag());

		if (theEntity instanceof Player) {
			ItemStack itemstack = ((Player) theEntity).getInventory().getSelected();

			if (!itemstack.isEmpty()) {
				compoundTag.put("SelectedItem", itemstack.save(new CompoundTag()));
			}
		}

		return compoundTag;
	}
}
