package modularmachines.common.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.BlankRecipeCategory;
import modularmachines.common.utils.Translator;
import net.minecraft.util.ResourceLocation;

public abstract class ModuleRecipeCategory extends BlankRecipeCategory<ModuleRecipeWrapper> {

	protected IDrawable background;
	protected IDrawable slot;
	protected IDrawable tank;
	protected IDrawable tankOverlay;
	protected String inventoryTitle;
	protected String uid;
	protected final static ResourceLocation WIDGET_TEXTURE = new ResourceLocation("modularmachines", "textures/gui/widgets.png");

	public ModuleRecipeCategory(IDrawable background, IGuiHelper guiHelper, String inventoryTitle, String uid) {
		this.background = background;
		this.slot = guiHelper.getSlotDrawable();
		this.tank = guiHelper.createDrawable(WIDGET_TEXTURE, 132, 127, 18, 60);
		this.tankOverlay = guiHelper.createDrawable(WIDGET_TEXTURE, 151, 128, 16, 58);
		this.inventoryTitle = inventoryTitle;
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getTitle() {
		return Translator.translateToLocal(inventoryTitle);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
}
