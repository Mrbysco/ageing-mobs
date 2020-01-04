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
        if (ageingData != null) {
            return String.format("Ageing from " + ageingData.getEntity().getTranslationKey() + " to " + ageingData.getTransformedEntity().getTranslationKey() + " has been removed with unique ID: " + ageingData.getName());
        } else {
            return String.format("Ageing from " + ageingData.getEntity().getTranslationKey() + " to " + ageingData.getTransformedEntity().getTranslationKey() + " could not be removed, ID: " + ageingData.getName() + " doesn't exists");
        }
    }

    @Override
    public void undo() {
        if(oldAgeingData != null) {
            AgeingRegistry.INSTANCE.registerAgeing(oldAgeingData);
        }
    }

    @Override
    public String describeUndo() {
        return String.format("Ageing from " + oldAgeingData.getEntity().getTranslationKey() + " to " + oldAgeingData.getTransformedEntity().getTranslationKey() + " has been re-added, unique ID: " + oldAgeingData.getName());
    }
}
