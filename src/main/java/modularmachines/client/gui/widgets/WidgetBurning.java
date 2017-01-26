package modularmachines.client.gui.widgets;

import java.util.Collections;
import java.util.List;

import net.minecraftforge.fml.common.Loader;

import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import modularmachines.common.utils.RenderUtil;

public class WidgetBurning<M extends IModuleBurning> extends Widget<IModuleState<M>> {

	public WidgetBurning(int posX, int posY, IModuleState<M> provider) {
		super(posX, posY, 14, 14, provider);
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		return Collections.emptyList();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		if (Loader.isModLoaded("JEI")) {
			JeiPlugin.jeiRuntime.getRecipesGui().showCategories(Collections.singletonList(VanillaRecipeCategoryUid.FUEL));
		}
	}

	@Override
	public void draw(IGuiBase gui) {
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + positon.x, sy + positon.y, 0, 176, positon.width, positon.height);
		if (source.getModule().getBurnTime(source) > 0) {
			int fuel = (source.getModule().getBurnTime(source) * positon.height) / source.getModule().getBurnTimeTotal(source);
			gui.getGui().drawTexturedModalRect(sx + positon.x, sy + positon.y + 14 - fuel, 14, 176 + 14 - fuel, positon.width, fuel);
		}
	}
}