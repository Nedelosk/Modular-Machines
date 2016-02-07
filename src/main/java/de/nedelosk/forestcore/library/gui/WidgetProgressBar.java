package de.nedelosk.forestcore.library.gui;

import java.util.ArrayList;

import de.nedelosk.forestcore.utils.RenderUtil;

public class WidgetProgressBar extends Widget {

	public int burntime;
	public int burntimeTotal;

	public WidgetProgressBar(int posX, int posY, int burntime, int burntimeTotal) {
		super(posX, posY, 22, 15);
		widget = RenderUtil.getResourceLocation("modularmachines", "widgets", "gui");
		this.burntime = burntime;
		this.burntimeTotal = burntimeTotal;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase gui) {
		ArrayList<String> list = new ArrayList<String>();
		if (burntimeTotal != 0) {
			list.add(burntime + " / " + burntimeTotal);
		}
		return list;
	}

	@Override
	public void draw(IGuiBase gui) {
		RenderUtil.bindTexture(widget);
		int process = (burntimeTotal == 0) ? 0 : burntime * 22 / burntimeTotal;
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.drawTexturedModalRect(sx + pos.x, sy + pos.y, 54, 0, 22, 15);
		if (burntime > 0) {
			gui.drawTexturedModalRect(sx + pos.x, sy + pos.y, 76, 0, process, 15);
		}
	}
}
