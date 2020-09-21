package com.shynieke.ageingmobs.compat.jei.regular;

import com.shynieke.ageingmobs.Reference;
import com.shynieke.ageingmobs.compat.jei.JEIBlankCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.util.Translator;
import net.minecraft.util.ResourceLocation;

public class RegularCategory extends JEIBlankCategory<RegularWrapper> {

	public static final String UID = "ageingmobs.regular";
    private final IDrawableStatic background;
    private final String title;
    
	public RegularCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(new ResourceLocation(Reference.MOD_ID, "textures/gui/jei_icons.png"), 0, 0, 16, 16));
		
		title = Translator.translateToLocal("gui.ageingmobs.regular");
		 
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "textures/gui/regular_background.png");
		background = guiHelper.createDrawable(location, 0, 0, 142, 102);
	}
	
	@Override
	public String getUid() {
		return UID;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RegularWrapper recipeWrapper, IIngredients ingredients) {
		
	}
}
