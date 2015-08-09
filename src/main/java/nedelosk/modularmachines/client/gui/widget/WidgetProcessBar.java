package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import nedelosk.nedeloskcore.api.machines.Widget;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.utils.RenderUtils;

public class WidgetProcessBar extends Widget {

	public int burntime;
	public int burntimeTotal;
	
	public WidgetProcessBar(int posX, int posY, int width, int height, int burntime, int burntimeTotal) {
		super(posX, posY, width, height);
		widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
		this.burntime = burntime;
		this.burntimeTotal = burntimeTotal;
	}
	
	@Override
	public ArrayList<String> getTooltip() {
		ArrayList<String> list = new ArrayList<>();
		list.add(burntime + " / " + burntimeTotal);
		return list;
	}
	
	@Override
	public void draw(GuiBase gui) {
		RenderUtils.bindTexture(widget);
		int process = (burntimeTotal == 0) ? 0 : burntime * 22 / burntimeTotal;
	    int sx = gui.getGuiLeft();
	    int sy = gui.getGuiTop();
        this.drawTexturedModalRect(sx + posX, sy + posY, 54, 0, 22, 15);
        if (burntime > 0 && burntime <= burntimeTotal) {
        this.drawTexturedModalRect(sx + posX, sy + posY, 76, 0, process, 15);
        }
	}

}
