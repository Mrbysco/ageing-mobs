package com.shynieke.ageingmobs.compat.ct.impl;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.shynieke.ageingmobs.AgeingMobs;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.ageingmobs.AgeingData")
public class MCAgeing {
    private final AgeingData internal;

    public MCAgeing(AgeingData data) {
        this.internal = data;
    }

    @ZenCodeType.Constructor
    public MCAgeing(String uniqueID, MCEntityType entity, String entityData, MCEntityType transformedEntity, String evolvedEntityEntityData, int tickTime) {
        this(new AgeingData(uniqueID, entity.getInternal(), createNBTTag(entityData), transformedEntity.getInternal(), createNBTTag(evolvedEntityEntityData), tickTime));
    }

    @ZenCodeType.Method
    public MCAgeing setCriteria(MCCriteria[] criteria) {
        if(criteria.length > 0) {
            List<BaseCriteria> baseList = Lists.newArrayList();
            for(int i = 0; i < criteria.length; i++) {
                BaseCriteria newInternal = criteria[i].getInternal();
                baseList.add(newInternal);
            }
            BaseCriteria[] criteriaArray = new BaseCriteria[baseList.size()];
            criteriaArray = baseList.toArray(criteriaArray);
            return new MCAgeing(this.internal.addCriteria(criteriaArray));
        }

        return this;
    }

    public static CompoundNBT createNBTTag(String nbtData)
    {
        CompoundNBT tag = new CompoundNBT();

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
        catch (CommandSyntaxException nbtexception)
        {
            AgeingMobs.LOGGER.error("nope... " +  nbtexception);
        }

        return tag;
    }

    public AgeingData getInternal() {
        return this.internal;
    }
}
