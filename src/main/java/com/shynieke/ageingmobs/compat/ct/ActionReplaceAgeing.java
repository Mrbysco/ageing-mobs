package com.shynieke.ageingmobs.compat.ct;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.shynieke.ageingmobs.compat.ct.impl.MCAgeingData;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ActionReplaceAgeing implements IUndoableAction {
	public final AgeingData ageingData;
	public final AgeingData oldAgeingData;

	public ActionReplaceAgeing(MCAgeingData data) {
		this.ageingData = data.getInternal();
		this.oldAgeingData = AgeingRegistry.INSTANCE.getByID(data.getInternal().getName());
	}

	@Override
	public void apply() {
		ResourceLocation resourceLocation = ForgeRegistries.ENTITY_TYPES.getKey(ageingData.getEntity());
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

		ResourceLocation resourceLocation = ForgeRegistries.ENTITY_TYPES.getKey(ageingData.getEntity());
		if (resourceLocation != null) {
			if (AgeingRegistry.INSTANCE.isIDUnique(resourceLocation, ageingData.getName())) {
				return "Ageing from <" + ForgeRegistries.ENTITY_TYPES.getKey(ageingData.getEntity()) + "> to <" + ForgeRegistries.ENTITY_TYPES.getKey(ageingData.getTransformedEntity()) + "> has been changed";
			} else {
				return "Ageing from <" + ForgeRegistries.ENTITY_TYPES.getKey(ageingData.getEntity()) + "> to <" + ForgeRegistries.ENTITY_TYPES.getKey(ageingData.getTransformedEntity()) + "> could not be changed";
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
		return "Ageing from <" + ForgeRegistries.ENTITY_TYPES.getKey(ageingData.getEntity()) + "> to <" + ForgeRegistries.ENTITY_TYPES.getKey(ageingData.getTransformedEntity()) + "> has been changed back";
	}
}
