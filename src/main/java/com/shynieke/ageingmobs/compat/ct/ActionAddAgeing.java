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
            return String.format("Ageing from <%s> to <%s> has been added with unique ID: %s", ageingData.getEntity().getRegistryName(), ageingData.getTransformedEntity().getRegistryName(), ageingData.getName());
        } else {
            return String.format("Ageing from <%s> to <%s> could not be added, ID: %s", ageingData.getEntity().getRegistryName(), ageingData.getTransformedEntity().getRegistryName(), ageingData.getName());
        }
    }

    @Override
    public void undo() {
        AgeingRegistry.INSTANCE.removeAgeing(ageingData);
    }

    @Override
    public String describeUndo() {
        return String.format("Ageing from <%s> to <%s> has been removed again, unique ID: %s", ageingData.getEntity().getRegistryName(), ageingData.getTransformedEntity().getRegistryName(), ageingData.getName());
    }
}
