package com.shynieke.ageingmobs.compat.ct;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.shynieke.ageingmobs.compat.ct.impl.MCAgeing;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;

public class ActionAddAgeing implements IUndoableAction {
    public final AgeingData ageingData;

    public ActionAddAgeing(MCAgeing data) {
        this.ageingData = data.getInternal();
    }

    @Override
    public void apply() {
        if (AgeingRegistry.INSTANCE.isIDUnique(ageingData.getName())) {
            AgeingRegistry.INSTANCE.registerAgeing(ageingData);
        }
    }

    @Override
    public String describe() {
        if (AgeingRegistry.INSTANCE.isIDUnique(ageingData.getName())) {
            return String.format("Ageing from " + ageingData.getEntity().getTranslationKey() + " to " + ageingData.getTransformedEntity().getTranslationKey() + " has been added with unique ID: " + ageingData.getName());
        } else {
            return String.format("Ageing from " + ageingData.getEntity().getTranslationKey() + " to " + ageingData.getTransformedEntity().getTranslationKey() + " could not be added, ID: " + ageingData.getName() + " already exists");
        }
    }

    @Override
    public void undo() {
        AgeingRegistry.INSTANCE.removeAgeing(ageingData);
    }

    @Override
    public String describeUndo() {
        return String.format("Ageing from " + ageingData.getEntity().getTranslationKey() + " to " + ageingData.getTransformedEntity().getTranslationKey() + " has been removed again, unique ID: " + ageingData.getName());
    }
}
