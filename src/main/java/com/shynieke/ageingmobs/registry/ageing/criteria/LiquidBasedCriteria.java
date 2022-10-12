package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class LiquidBasedCriteria extends BaseCriteria {
	private String liquid;
	private boolean reversible;

	private boolean isReversing;

	public LiquidBasedCriteria(iAgeing ageing, String liquid, Boolean reversible) {
		super(ageing);
		this.liquid = liquid;
		this.reversible = reversible;

		this.isReversing = false;
	}

	public String getLiquid() {
		return liquid;
	}

	public void setLiquid(String liquid) {
		this.liquid = liquid;
	}

	public boolean isReversible() {
		return reversible;
	}

	public void setReversible(boolean reversible) {
		this.reversible = reversible;
	}

	@Override
	public boolean checkCriteria(Level level, Entity entityIn) {
		boolean inFluid = entityIn.isFree(entityIn.getX(), entityIn.getY() - 1, entityIn.getZ());
		if (inFluid) {
			ResourceLocation fluidLoc = ForgeRegistries.FLUIDS.getKey(level.getFluidState(entityIn.blockPosition().offset(0, -1, 0)).getType());
			if (fluidLoc != null && fluidLoc.equals(new ResourceLocation(getLiquid()))) {
				this.isReversing = false;
				return true;
			} else {
				if (isReversible()) {
					this.isReversing = true;
				}
				return false;
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
