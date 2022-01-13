package com.shynieke.ageingmobs.compat.ct.impl;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.collect.Lists;
import com.shynieke.ageingmobs.helper.NBTHelper;
import com.shynieke.ageingmobs.registry.ageing.AgeingData;
import com.shynieke.ageingmobs.registry.ageing.criteria.BaseCriteria;
import net.minecraft.world.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.openzen.zencode.java.ZenCodeType.Name;

import java.util.List;

@ZenRegister
@Name("mods.ageingmobs.AgeingData")
public class MCAgeingData {
    private final AgeingData internal;

    public MCAgeingData(AgeingData data) {
        this.internal = data;
    }

    @Method
    public static MCAgeingData of(String uniqueID, EntityType entity, String entityData, EntityType transformedEntity, String evolvedEntityEntityData, int tickTime) {
        return new MCAgeingData(new AgeingData(uniqueID, entity, NBTHelper.createNBTTag(entityData), transformedEntity, NBTHelper.createNBTTag(evolvedEntityEntityData), tickTime));
    }

    @Method
    public MCAgeingData setCriteria(MCAgeingCriteria[] criteria) {
        if(criteria.length > 0) {
            List<BaseCriteria> baseList = Lists.newArrayList();
            for (MCAgeingCriteria criterion : criteria) {
                BaseCriteria newInternal = criterion.getInternal();
                baseList.add(newInternal);
            }
            BaseCriteria[] criteriaArray = new BaseCriteria[baseList.size()];
            criteriaArray = baseList.toArray(criteriaArray);
            return new MCAgeingData(this.internal.addCriteria(criteriaArray));
        }

        return this;
    }

//    @Method
//    public MCAgeing setGamestage(String gamestage) {
//        AgeingData internal = this.internal;
//        internal.setGamestage(gamestage);
//        return new MCAgeing(internal);
//    }

    public AgeingData getInternal() {
        return this.internal;
    }
}
