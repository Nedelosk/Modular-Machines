package nedelosk.modularmachines.api.client.widget;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.api.utils.RenderUtils;
import nedelosk.modularmachines.api.recipes.IMachineMode;
import net.minecraft.util.StatCollector;

public class WidgetButtonMode extends Widget {

	public IMachineMode mode;

	public WidgetButtonMode(int posX, int posY, IMachineMode mode) {
		super(posX, posY, 18, 18);
		widget = RenderUtils.getResourceLocation("modularmachines", "widgets", "gui");
		this.mode = mode;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase gui) {
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
	
	public void setMode(IMachineMode mode) {
		this.mode = mode;
	}
	
	public IMachineMode getMode() {
		return mode;
	}

}
