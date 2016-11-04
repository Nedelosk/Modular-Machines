package modularmachines.client.gui.widgets;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import modularmachines.api.gui.IGuiBase;
import modularmachines.api.gui.Widget;
import modularmachines.api.modules.IModuleBurning;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.plugins.jei.JeiPlugin;
import modularmachines.common.utils.RenderUtil;
import net.minecraftforge.fml.common.Loader;

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
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 0, 176, pos.width, pos.height);
		if (provider.getModule().getBurnTime(provider) > 0) {
			int fuel = (provider.getModule().getBurnTime(provider) * pos.height) / provider.getModule().getBurnTimeTotal(provider);
			gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y + 14 - fuel, 14, 176 + 14 - fuel, pos.width, fuel);
		}
	}
}