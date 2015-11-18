package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.utils.RenderUtils;

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
	public ArrayList<String> getTooltip() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(fuel + " / " + fuelTotal);
		return list;
	}

	@Override
	public void draw(IGuiBase gui) {
		RenderUtils.bindTexture(widget);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		this.drawTexturedModalRect(sx + posX, sy + posY, 0, 176, 14, 14);
		if (fuel > 0) {
			int fuel = (this.fuel * 14) / this.fuelTotal;

			this.drawTexturedModalRect(sx + this.posX, sy + this.posY + 14 - fuel, 14, 176 + 14 - fuel, 14, fuel);
		}
	}

}
