package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import nedelosk.forestcore.api.gui.IGuiBase;
import nedelosk.forestcore.api.gui.Widget;
import nedelosk.forestcore.api.utils.RenderUtils;

public class WidgetBurningBar extends Widget {

	public int fuel;
	public int fuelTotal;

	public WidgetBurningBar(int posX, int posY, int burntime, int burntimeTotal) {
		super(posX, posY, 14, 14);
		widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
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
		RenderUtils.bindTexture(widget);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.drawTexturedModalRect(sx + pos.x, sy + pos.y, 0, 176, 14, 14);
		if (fuel > 0) {
			int fuel = (this.fuel * 14) / this.fuelTotal;

			gui.drawTexturedModalRect(sx + pos.x, sy + pos.y + 14 - fuel, 14, 176 + 14 - fuel, 14, fuel);
		}
	}

}
