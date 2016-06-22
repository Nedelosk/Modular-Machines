package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.client.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;

public class WidgetBurningBar extends Widget {

	public int fuel;
	public int fuelTotal;

	public WidgetBurningBar(int posX, int posY, int burntime, int burntimeTotal) {
		super(posX, posY, 14, 14);
		this.fuel = burntime;
		this.fuelTotal = burntimeTotal;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase gui) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(fuel + " / " + fuelTotal);
		return list;
	}

	@Override
	public void draw(IGuiBase gui) {
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 0, 176, 14, 14);
		if (fuel > 0) {
			int fuel = (this.fuel * 14) / this.fuelTotal;
			gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y + 14 - fuel, 14, 176 + 14 - fuel, 14, fuel);
		}
	}
}