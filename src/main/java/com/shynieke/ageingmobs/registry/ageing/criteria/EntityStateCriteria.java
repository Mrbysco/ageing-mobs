package com.shynieke.ageingmobs.registry.ageing.criteria;

import com.shynieke.ageingmobs.registry.ageing.iAgeing;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class EntityStateCriteria extends BaseCriteria {

    private Predicate<Entity> stateChecker;

    public EntityStateCriteria(iAgeing ageing, Predicate<Entity> stateChecker) {
        super(ageing);
        this.stateChecker = stateChecker;
    }

    public Predicate<Entity> getStateChecker() {
        return this.stateChecker;
    }

    public void setStateChecker(Predicate<Entity> stateChecker) {
        this.stateChecker = stateChecker;
    }

    @Override
    public boolean checkCriteria(Level level, Entity entityIn) {
        return this.getStateChecker().test(entityIn);
    }
}
