package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EffectCriteria extends BaseCriteria {
	private Holder<MobEffect> mobEffect;

	public EffectCriteria(iAgeing ageing, Holder<MobEffect> mobEffect) {
		super(ageing);
		this.mobEffect = mobEffect;
	}

	public EffectCriteria(iAgeing ageing, MobEffect mobEffect) {
		super(ageing);
		Identifier effectLocation = BuiltInRegistries.MOB_EFFECT.getKey(mobEffect);
		if (effectLocation != null) {
			var optionalHolder = BuiltInRegistries.MOB_EFFECT.get(effectLocation);
			if (optionalHolder.isPresent()) {
				this.mobEffect = optionalHolder.get();
			} else {
				throw new IllegalArgumentException("Unknown effect: " + mobEffect);
			}
		} else {
			throw new IllegalArgumentException("Unknown effect: " + mobEffect);
		}
	}

	public Holder<MobEffect> getMobEffect() {
		return mobEffect;
	}

	public void setMobEffect(Holder<MobEffect> mobEffect) {
		this.mobEffect = mobEffect;
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		if (entityIn instanceof LivingEntity livingEntity) {
			return livingEntity.hasEffect(mobEffect);
		}
		return false;
	}
}
