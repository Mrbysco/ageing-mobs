package com.shynieke.ageingmobs.compat.ct;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.shynieke.ageingmobs.compat.ct.impl.MCAgeingData;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ActionReplaceAgeing implements IUndoableAction {
	public final AgeingData ageingData;
	public final AgeingData oldAgeingData;

	public ActionReplaceAgeing(MCAgeingData data) {
		this.ageingData = data.getInternal();
		this.oldAgeingData = AgeingRegistry.INSTANCE.getByID(data.getInternal().getName());
	}

	@Override
	public void apply() {
		ResourceLocation resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getEntity());
		if (resourceLocation != null && !AgeingRegistry.INSTANCE.isIDUnique(resourceLocation, ageingData.getName())) {
			AgeingRegistry.INSTANCE.replaceAgeing(ageingData);
		}
	}

	@Override
	public String describe() {
		if (ageingData.getEntity() == null) {
			return "Unknown entity inserted at ageing ID '" + ageingData.getName() + "'";
		}
		if (ageingData.getTransformedEntity() == null) {
			return "Unknown transform entity inserted at ageing ID '" + ageingData.getName() + "'";
		}

		ResourceLocation resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getEntity());
		if (resourceLocation != null) {
			if (AgeingRegistry.INSTANCE.isIDUnique(resourceLocation, ageingData.getName())) {
				return "Ageing from <" + BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getEntity()) + "> to <" + BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getTransformedEntity()) + "> has been changed";
			} else {
				return "Ageing from <" + BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getEntity()) + "> to <" + BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getTransformedEntity()) + "> could not be changed";
			}
		} else {
			return "Could not replace ageing of %s as the resource location of the given entity is invalid, please check if your MCEntityType is correct";
		}
	}

	@Override
	public void undo() {
		AgeingRegistry.INSTANCE.replaceAgeing(oldAgeingData);
	}

	@Override
	public String describeUndo() {
		return "Ageing from <" + BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getEntity()) + "> to <" + BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getTransformedEntity()) + "> has been changed back";
	}

	@Override
	public String systemName() {
		return "Ageing Mobs";
	}
}
