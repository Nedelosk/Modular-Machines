package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;

public class WidgetBurning extends Widget {

	public int burnTime;
	public int burnTimeTotal;

	public WidgetBurning(int posX, int posY, int burntime, int burntimeTotal) {
		super(posX, posY, 14, 14);
		this.burnTime = burntime;
		this.burnTimeTotal = burntimeTotal;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase gui) {
		return new ArrayList<>();
	}

	@Override
	public void draw(IGuiBase gui) {
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 0, 176, 14, 14);
		if (burnTime > 0) {
			int fuel = (this.burnTime * 14) / this.burnTimeTotal;
			gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y + 14 - fuel, 14, 176 + 14 - fuel, 14, fuel);
		}
	}
}