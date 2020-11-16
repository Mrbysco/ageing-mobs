package com.shynieke.ageingmobs.helper;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.shynieke.ageingmobs.AgeingMobs;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;

public class NBTHelper {
    public static CompoundNBT createNBTTag(String nbtData)
    {
        CompoundNBT tag = new CompoundNBT();

        try
        {
            if(nbtData.startsWith("{") && nbtData.endsWith("}"))
            {
                tag = JsonToNBT.getTagFromJson(nbtData);
            }
            else
            {
                tag = JsonToNBT.getTagFromJson("{" + nbtData + "}");
            }
        }
        catch (CommandSyntaxException exception)
        {
            AgeingMobs.LOGGER.error("nope... " +  exception);
        }

        return tag;
    }
}
