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
        if(ageingData.getEntity() == null) {
            return "Unknown entity inserted at ageing ID '" + ageingData.getName() + "'";
        }
        if(ageingData.getTransformedEntity() == null) {
            return "Unknown transform entity inserted at ageing ID '" + ageingData.getName() + "'";
        }

        if (AgeingRegistry.INSTANCE.isIDUnique(ageingData.getName())) {
            return "Ageing from <" + ageingData.getEntity().getRegistryName() + "> to <" + ageingData.getTransformedEntity().getRegistryName() + "> has been added with unique ID: " + ageingData.getName();
        } else {
            return "Ageing from <" + ageingData.getEntity().getRegistryName() + "> to <" + ageingData.getTransformedEntity().getRegistryName() + "> could not be added, ID: " + ageingData.getName() + " already exists";
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
