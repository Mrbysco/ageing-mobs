package com.shynieke.ageingmobs.compat.ct;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.shynieke.ageingmobs.compat.ct.impl.MCAgeing;
import com.shynieke.ageingmobs.registry.AgeingRegistry;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;

public class ActionReplaceAgeing implements IUndoableAction {
    public final AgeingData ageingData;
    public final AgeingData oldAgeingData;

    public ActionReplaceAgeing(MCAgeing data) {
        this.ageingData = data.getInternal();
        this.oldAgeingData = AgeingRegistry.INSTANCE.getNameToAgeing().get(data.getInternal().getName());
    }

    @Override
    public void apply() {
        if (!AgeingRegistry.INSTANCE.isIDUnique(ageingData.getName())) {
            AgeingRegistry.INSTANCE.replaceAgeing(ageingData);
        }
    }

    @Override
    public String describe() {
        if (AgeingRegistry.INSTANCE.isIDUnique(ageingData.getName())) {
            return "Ageing from <" + ageingData.getEntity().getRegistryName() + "> to <" + ageingData.getTransformedEntity().getRegistryName() + "> has been changed";
        } else {
            return "Ageing from <" + ageingData.getEntity().getRegistryName() + "> to <" + ageingData.getTransformedEntity().getRegistryName() + "> could not be changed";
        }
    }

    @Override
    public void undo() {
        AgeingRegistry.INSTANCE.replaceAgeing(oldAgeingData);
    }

    @Override
    public String describeUndo() {
        return "Ageing from <" + ageingData.getEntity().getRegistryName() + "> to <" + ageingData.getTransformedEntity().getRegistryName() + "> has been changed back";
    }
}
