package com.shynieke.ageingmobs.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class AgeingHelper {
	public static ResourceLocation getEntityLocation(String name)
	{
		String[] splitResource = name.split(":");
		if (splitResource.length != 2)
			return null;
		else
			return new ResourceLocation(splitResource[0], splitResource[1]);
	}
	
	public static NBTTagCompound entityToNBT(Entity theEntity)
    {
        NBTTagCompound nbttagcompound = theEntity.writeToNBT(new NBTTagCompound());

        if (theEntity instanceof EntityPlayer)
        {
            ItemStack itemstack = ((EntityPlayer)theEntity).inventory.getCurrentItem();

            if (!itemstack.isEmpty())
            {
                nbttagcompound.setTag("SelectedItem", itemstack.writeToNBT(new NBTTagCompound()));
            }
        }

        return nbttagcompound;
    }
	
	public static String getTiming(int time)
	{
		String second = "second";
		String minute = "minute";
		String hour = "minute";
		
		if(time > 3600)
		{
			double hours = (double)time / 3600;
			String total = String.valueOf(hours) + " " + hour;
			
			return hours > 1 ? total + "(s)" : total;
		}
		else if(time > 60)
		{
			double minutes = (double)time / 60;
			String total = String.valueOf(time) + " " + minute;
			
			return minutes > 1 ? total + "(s)" : total;
		}
		else
		{
			String total = String.valueOf(time) + " " + second;
			
			return time > 1 ? total + "(s)" : total;
		}
	}
}
