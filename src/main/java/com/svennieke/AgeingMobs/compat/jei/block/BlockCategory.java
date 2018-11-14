package com.svennieke.AgeingMobs.compat.jei.block;

import com.svennieke.AgeingMobs.Reference;
import com.svennieke.AgeingMobs.compat.jei.JEIBlankCategory;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.util.Translator;
import net.minecraft.util.ResourceLocation;

public class BlockCategory extends JEIBlankCategory<BlockWrapper> {

	public static final String UID = "ageingmobs.block";
    private final IDrawableStatic background;
    private final String title;
    
	public BlockCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(new ResourceLocation(Reference.MOD_ID, "textures/gui/jei_icons.png"), 160, 0, 16, 16));
		
		title = Translator.translateToLocal("gui.ageingmobs.block");
		 
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "textures/gui/block_background.png");
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
	public void setRecipe(IRecipeLayout recipeLayout, BlockWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		int blockSlot = 1;
		guiItemStacks.init(blockSlot, true, 62, 48);
		guiItemStacks.set(ingredients);
	}
}
