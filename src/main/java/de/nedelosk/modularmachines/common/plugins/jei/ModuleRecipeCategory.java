package de.nedelosk.modularmachines.common.plugins.jei;

import de.nedelosk.modularmachines.common.utils.Translator;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.util.ResourceLocation;

public abstract class ModuleRecipeCategory extends BlankRecipeCategory<ModuleRecipeWrapper> {

	protected IDrawable background;
	protected IDrawable slot;
	protected IDrawable tank;
	protected IDrawable tankOverlay;
	protected String inventoryName;
	protected String uid;
	protected final static ResourceLocation widgetTexture = new ResourceLocation("modularmachines", "textures/gui/widgets.png");
	protected final static ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/jei/background.png");

	public ModuleRecipeCategory(IDrawable background, IGuiHelper guiHelper, String inventoryName, String uid) {
		this.background = background;
		this.slot = guiHelper.getSlotDrawable();
		this.tank = guiHelper.createDrawable(widgetTexture, 132, 127, 18, 60);
		this.tankOverlay = guiHelper.createDrawable(widgetTexture, 151, 128, 16, 58);
		this.inventoryName = inventoryName;
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getTitle() {
		return Translator.translateToLocal(inventoryName);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
}
