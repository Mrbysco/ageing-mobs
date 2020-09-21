package com.shynieke.ageingmobs.compat.jei;

import com.shynieke.ageingmobs.Reference;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nullable;

public abstract class JEIBlankCategory <T extends IRecipeWrapper> implements IRecipeCategory<T> {
    private final IDrawable icon;

    protected JEIBlankCategory(IDrawable icon) {
        this.icon = icon;
    }

    @Override
    public String getModName() {
        return Reference.MOD_NAME;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }
}