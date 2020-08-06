package com.shynieke.ageingmobs.helper;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.shynieke.ageingmobs.AgeingMobs;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;

public class NBTHelper {
    public static CompoundNBT createNBTTag(String nbtData)
    {
        CompoundNBT tag = new CompoundNBT();

        try {
            String data = nbtData;
            if(data.startsWith("{") && data.endsWith("}"))
            {
                tag = JsonToNBT.getTagFromJson(data);
            }
            else
            {
                tag = JsonToNBT.getTagFromJson("{" + data + "}");
            }
        } catch (CommandSyntaxException nbtexception) {
            AgeingMobs.LOGGER.error("nope... " +  nbtexception);
        }

        return tag;
    }
}
