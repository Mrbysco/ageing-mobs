package com.shynieke.ageingmobs.compat.ct.impl;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.google.common.collect.Lists;
import com.shynieke.ageingmobs.helper.NBTHelper;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
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
        this(new AgeingData(uniqueID, entity.getInternal(), NBTHelper.createNBTTag(entityData), transformedEntity.getInternal(), NBTHelper.createNBTTag(evolvedEntityEntityData), tickTime));
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

    @ZenCodeType.Method
    public MCAgeing setGamestage(String gamestage) {
        AgeingData internal = this.internal;
        internal.setGamestage(gamestage);
        return new MCAgeing(internal);
    }

    public AgeingData getInternal() {
        return this.internal;
    }
}
