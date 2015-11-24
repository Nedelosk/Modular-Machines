package nedelosk.modularmachines.client.gui.widget;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.utils.RenderUtils;
import nedelosk.modularmachines.api.modular.module.basic.basic.IMachineMode;
import net.minecraft.util.StatCollector;

public class WidgetButtonMode extends Widget {

	public IMachineMode mode;

	public WidgetButtonMode(int posX, int posY, IMachineMode mode) {
		super(posX, posY, 18, 18);
		widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
		this.mode = mode;
	}

	@Override
	public ArrayList<String> getTooltip() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(StatCollector.translateToLocal("mode." + mode.getName() + ".name"));
		return list;
	}

	@Override
	public void draw(IGuiBase gui) {
		RenderUtils.bindTexture(widget);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 0, 18, 18);
		gui.drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 18*mode.ordinal()+18, 18, 18);
	}

}
