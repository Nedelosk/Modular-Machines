package de.nedelosk.modularmachines.common.plugins.jei;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.util.ResourceLocation;

public abstract class ModuleRecipeCategory extends BlankRecipeCategory<ModuleRecipeWrapper> {

	protected IDrawable background;
	protected String inventoryName;
	protected String uid;

	protected final static ResourceLocation widgetTexture = new ResourceLocation("forestmods", "textures/gui/widgets.png");
	protected final static ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/background.png");

	public ModuleRecipeCategory(IDrawable background, String inventoryName, String uid) {
		this.background = background;
		this.inventoryName = inventoryName;
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getTitle() {
		return inventoryName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
}
