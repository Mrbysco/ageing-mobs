package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EffectCriteria extends BaseCriteria {
	private MobEffect mobEffect;

	public EffectCriteria(iAgeing ageing, MobEffect mobEffect) {
		super(ageing);
		this.mobEffect = mobEffect;
	}

	public MobEffect getMobEffect() {
		return mobEffect;
	}

	public void setMobEffect(MobEffect mobEffect) {
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
