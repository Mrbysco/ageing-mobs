package com.shynieke.ageingmobs.helper;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.shynieke.ageingmobs.AgeingMobs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;

public class NBTHelper {
	public static CompoundTag createNBTTag(String nbtData) {
		CompoundTag tag = new CompoundTag();

		try {
			if (nbtData.startsWith("{") && nbtData.endsWith("}")) {
				tag = TagParser.parseTag(nbtData);
			} else {
				tag = TagParser.parseTag("{" + nbtData + "}");
			}
		} catch (CommandSyntaxException exception) {
			AgeingMobs.LOGGER.error("nope... " + exception);
		}

		return tag;
	}
}
