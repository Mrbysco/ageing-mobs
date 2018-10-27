package com.svennieke.AgeingMobs.endermite;

import com.svennieke.AgeingMobs.AgeingMobs;
import com.svennieke.AgeingMobs.lists.info.RegularAgingInfo;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class EndermiteToShulkerAgeingInfo extends RegularAgingInfo{
	
	public EndermiteToShulkerAgeingInfo(String uniqueID, String entity, String transformedEntity, int tickTime) {
		super(uniqueID, entity, createNBTTag(""), transformedEntity, createNBTTag(""), tickTime);
	}
	
	public static NBTTagCompound createNBTTag(String nbtData)
	{
        NBTTagCompound tag = new NBTTagCompound();

		try
        {
        	String data = nbtData;
        	if(data.startsWith("{") && data.endsWith("}"))
        	{
        		tag = JsonToNBT.getTagFromJson(data);
        	}
        	else
        	{
        		tag = JsonToNBT.getTagFromJson("{" + data + "}");
        	}
        }
        catch (NBTException nbtexception)
        {
        	AgeingMobs.logger.error("nope... " + nbtexception);
        }
		
		return tag;
	}
}
