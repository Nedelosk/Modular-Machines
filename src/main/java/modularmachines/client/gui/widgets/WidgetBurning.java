package modularmachines.client.gui.widgets;

import java.util.Collections;
import java.util.List;

import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.modules.IModuleBurning;
import modularmachines.common.utils.Mod;
import modularmachines.common.utils.PluginUtil;
import modularmachines.common.utils.RenderUtil;

public class WidgetBurning<M extends IModuleBurning> extends Widget<IModuleLogic> {

	protected M module;
	
	public WidgetBurning(int posX, int posY, M module) {
		super(posX, posY, 14, 14);
		this.module = module;
	}

	@Override
	public List<String> getTooltip() {
		return Collections.emptyList();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		if (Mod.JEI.active()) {
			PluginUtil.show(PluginUtil.FUEL);
		}
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		RenderUtil.texture(widgetTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 0, 176, pos.width, pos.height);
		int burnTime = module.getFuel();
		if (burnTime > 0) {
			int burnTimeTotal = module.getFuelTotal();
			int fuel = (burnTime * pos.height) / burnTimeTotal;
			gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y + 14 - fuel, 14, 176 + 14 - fuel, pos.width, fuel);
		}
	}
}