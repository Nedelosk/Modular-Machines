package de.nedelosk.forestmods.client.gui.widgets;

import java.util.ArrayList;

import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.gui.Widget;
import de.nedelosk.forestmods.library.recipes.IMachineMode;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import net.minecraft.util.StatCollector;

public class WidgetButtonMode extends Widget {

	public IMachineMode mode;

	public WidgetButtonMode(int posX, int posY, IMachineMode mode) {
		super(posX, posY, 18, 18);
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
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 0, 18, 18);
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 18 * mode.ordinal() + 18, 18, 18);
	}

	public void setMode(IMachineMode mode) {
		this.mode = mode;
	}

	public IMachineMode getMode() {
		return mode;
	}
}