package com.shynieke.ageingmobs.compat.ct;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.shynieke.ageingmobs.compat.ct.impl.MCAgeing;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.ageingmobs.ChangeAgeing")
public class ChangeAgeingCT {
    @ZenCodeType.Method
    public static void addAgeing(MCAgeing ageing) {
        CraftTweakerAPI.apply(new ActionAddAgeing(ageing));
    }

    @ZenCodeType.Method
    public static void removeAgeing(String uniqueID) {
        CraftTweakerAPI.apply(new ActionRemoveAgeing(uniqueID));
    }
}
