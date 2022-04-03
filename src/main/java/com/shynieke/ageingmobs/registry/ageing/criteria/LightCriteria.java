package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class LightCriteria extends BaseCriteria {
	private int lightLevelMin;
	private int lightLevelMax;
	private boolean aloneBased;
	private boolean reversible;

	private boolean isReversing;

	public LightCriteria(iAgeing ageing, int lightLevelMin, int lightLevelMax, boolean aloneBased, boolean reversible) {
		super(ageing);
		this.lightLevelMin = lightLevelMin;
		this.lightLevelMax = lightLevelMax;
		this.aloneBased = aloneBased;
		this.reversible = reversible;

		this.isReversing = false;
	}

	public int getLightLevelMin() {
		return lightLevelMin;
	}

	public void setLightLevelMin(int lightLevelMin) {
		this.lightLevelMin = lightLevelMin;
	}

	public int getLightLevelMax() {
		return lightLevelMax;
	}

	public void setLightLevelMax(int lightLevelMax) {
		this.lightLevelMax = lightLevelMax;
	}

	public boolean isAloneBased() {
		return aloneBased;
	}

	public void setAloneBased(boolean aloneBased) {
		this.aloneBased = aloneBased;
	}

	public boolean isReversible() {
		return reversible;
	}

	public void setReversible(boolean reversible) {
		this.reversible = reversible;
	}

	@Override
	public boolean checkCriteria(Level worldIn, Entity entityIn) {
		BlockPos entityPos = entityIn.blockPosition();

		int entityLight = worldIn.getMaxLocalRawBrightness(entityPos);
		if (entityLight >= getLightLevelMin() && entityLight <= getLightLevelMax()) {
			if (isAloneBased()) {
				AABB areaHitbox = new AABB(entityPos.getX() - 0.5f, entityPos.getY() - 0.5f, entityPos.getZ() - 0.5f, entityPos.getX() + 0.5f, entityPos.getY() + 0.5f, entityPos.getZ() + 0.5f)
						.expandTowards(-5, -5, -5).expandTowards(5, 5, 5);
				if (!worldIn.getEntitiesOfClass(entityIn.getClass(), areaHitbox).contains(getEntity().create(worldIn))) {
					this.isReversing = false;
					return true;
				} else {
					if (isReversible()) {
						this.isReversing = true;
					}
					return false;
				}
			} else {
				this.isReversing = false;
				return true;
			}
		} else {
			if (isReversible()) {
				this.isReversing = true;
			}
			return false;
		}
	}

	@Override
	public boolean isReversing() {
		return isReversing;
	}
}
