package com.shynieke.ageingmobs.compat.ct;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import net.minecraft.core.registries.BuiltInRegistries;

public class ActionRemoveAgeing implements IUndoableAction {
	public final AgeingData ageingData;
	public final AgeingData oldAgeingData;

	public ActionRemoveAgeing(String ageingID) {
		this.ageingData = AgeingRegistry.INSTANCE.getByID(ageingID);
		this.oldAgeingData = ageingData;
	}

	@Override
	public void apply() {
		if (ageingData != null) {
			AgeingRegistry.INSTANCE.removeAgeing(ageingData);
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
		return "Ageing from <" + BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getEntity()) + "> to <" + BuiltInRegistries.ENTITY_TYPE.getKey(ageingData.getTransformedEntity()) + "> has been removed with unique ID: " + ageingData.getName();
	}

	@Override
	public void undo() {
		if (oldAgeingData != null) {
			AgeingRegistry.INSTANCE.registerAgeing(oldAgeingData);
		}
	}

	@Override
	public String describeUndo() {
		return "Ageing from <" + BuiltInRegistries.ENTITY_TYPE.getKey(oldAgeingData.getEntity()) + "> to <" + BuiltInRegistries.ENTITY_TYPE.getKey(oldAgeingData.getTransformedEntity()) + "> has been re-added, unique ID: " + oldAgeingData.getName();
	}

	@Override
	public String systemName() {
		return "Ageing Mobs";
	}
}
