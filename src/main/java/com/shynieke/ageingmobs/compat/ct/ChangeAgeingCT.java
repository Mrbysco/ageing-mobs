package com.shynieke.ageingmobs.compat.ct;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.shynieke.ageingmobs.compat.ct.impl.MCAgeingData;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.openzen.zencode.java.ZenCodeType.Name;

@ZenRegister
@Name("mods.ageingmobs.ChangeAgeing")
public class ChangeAgeingCT {
	@Method
	public static void addAgeing(MCAgeingData ageing) {
		CraftTweakerAPI.apply(new ActionAddAgeing(ageing));
	}

	@Method
	public static void removeAgeing(String uniqueID) {
		CraftTweakerAPI.apply(new ActionRemoveAgeing(uniqueID));
	}

	@Method
	public static void replaceAgeing(MCAgeingData ageing) {
		CraftTweakerAPI.apply(new ActionReplaceAgeing(ageing));
	}
}
