package com.shynieke.ageingmobs.registry.ageing;

import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nonnull;

public interface iAgeing {

	EntityType<? extends Entity> getEntity();

	CompoundTag getEntityData();

	EntityType<? extends Entity> getTransformedEntity();

	CompoundTag getTransformedEntityData();

	/*
	 * @return Returns the time (in ticks) it takes for a mob to age
	 */
	int getAgeingTme();

	/*
	 * @return Set the time (in ticks) it takes for a mob to age
	 */
	void setAgeingTme(int time);

	/*
	 * @return Unique name
	 */
	@Nonnull
	String getName();

	//Optional

	/*
	 * @return Returns the gamestage required
	 */
//    String getGamestage();

	/*
	 * @return Set the gamestage required
	 */
//    void setGamestage(String gamestage);

	void setCriteria(BaseCriteria[] criteria);

	BaseCriteria[] getCriteria();
}