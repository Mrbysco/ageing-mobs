package com.shynieke.ageingmobs.compat.ct;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.shynieke.ageingmobs.compat.ct.impl.MCAgeingData;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import net.minecraft.resources.ResourceLocation;

public class ActionAddAgeing implements IUndoableAction {
	public final AgeingData ageingData;

	public ActionAddAgeing(MCAgeingData data) {
		this.ageingData = data.getInternal();
	}

	@Override
	public void apply() {
		ResourceLocation resourceLocation = ageingData.getEntity().getRegistryName();
		if (resourceLocation != null && AgeingRegistry.INSTANCE.isIDUnique(resourceLocation, ageingData.getName())) {
			AgeingRegistry.INSTANCE.registerAgeing(ageingData);
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

		ResourceLocation resourceLocation = ageingData.getEntity().getRegistryName();
		if (resourceLocation != null) {
			if (AgeingRegistry.INSTANCE.isIDUnique(resourceLocation, ageingData.getName())) {
				return "Ageing from <" + ageingData.getEntity().getRegistryName() + "> to <" + ageingData.getTransformedEntity().getRegistryName() + "> has been added with unique ID: " + ageingData.getName();
			} else {
				return "Ageing from <" + ageingData.getEntity().getRegistryName() + "> to <" + ageingData.getTransformedEntity().getRegistryName() + "> could not be added, ID: " + ageingData.getName() + " already exists";
			}
		} else {
			return "Could not replace ageing of %s as the resource location of the given entity is invalid, please check if your MCEntityType is correct";
		}
	}

	@Override
	public void undo() {
		AgeingRegistry.INSTANCE.removeAgeing(ageingData);
	}

	@Override
	public String describeUndo() {
		return "Ageing from <" + ageingData.getEntity().getRegistryName() + "> to <" + ageingData.getTransformedEntity().getRegistryName() + "> has been removed again, unique ID: " + ageingData.getName();
	}
}
