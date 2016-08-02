package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
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
	public ArrayList<String> getTooltip(IGuiProvider gui) {
		return new ArrayList<>();
	}

	@Override
	public void draw(IGuiProvider gui) {
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 0, 176, pos.width, pos.height);
		if (burnTime > 0) {
			int fuel = (this.burnTime * pos.height) / this.burnTimeTotal;
			gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y + 14 - fuel, 14, 176 + 14 - fuel, pos.width, fuel);
		}
	}
}