package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.utils.RenderUtil;

public class WidgetBurningBar extends Widget {

	public int fuel;
	public int fuelTotal;

	public WidgetBurningBar(int posX, int posY, int burntime, int burntimeTotal) {
		super(posX, posY, 14, 14);
		widget = RenderUtil.getResourceLocation("modularmachines", "widgets", "gui");
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
		RenderUtil.bindTexture(widget);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.drawTexturedModalRect(sx + pos.x, sy + pos.y, 0, 176, 14, 14);
		if (fuel > 0) {
			int fuel = (this.fuel * 14) / this.fuelTotal;

			gui.drawTexturedModalRect(sx + pos.x, sy + pos.y + 14 - fuel, 14, 176 + 14 - fuel, 14, fuel);
		}
	}

}
