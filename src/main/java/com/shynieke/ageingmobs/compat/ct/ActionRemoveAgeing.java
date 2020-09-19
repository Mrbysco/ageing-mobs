package com.shynieke.ageingmobs.compat.ct;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;

public class ActionRemoveAgeing implements IUndoableAction {
    public final AgeingData ageingData;
    public final AgeingData oldAgeingData;

    public ActionRemoveAgeing(String ageingID) {
        this.ageingData = AgeingRegistry.INSTANCE.getNameToAgeing().get(ageingID);
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
        if(ageingData.getEntity() == null) {
            return String.format("Unknown entity inserted at ageing ID '%s'", ageingData.getName());
        }
        if(ageingData.getTransformedEntity() == null) {
            return String.format("Unknown transform entity inserted at ageing ID '%s'", ageingData.getName());
        }
        return "Ageing from <" + ageingData.getEntity().getRegistryName() + "> to <" + ageingData.getTransformedEntity().getRegistryName() + "> has been removed with unique ID: " + ageingData.getName();
    }

    @Override
    public void undo() {
        if(oldAgeingData != null) {
            AgeingRegistry.INSTANCE.registerAgeing(oldAgeingData);
        }
    }

    @Override
    public String describeUndo() {
        return "Ageing from <" + oldAgeingData.getEntity().getRegistryName() + "> to <" + oldAgeingData.getTransformedEntity().getRegistryName() + "> has been re-added, unique ID: " + oldAgeingData.getName();
    }
}
