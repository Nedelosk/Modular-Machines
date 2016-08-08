package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.recipes.IToolMode;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;

public class WidgetButtonMode extends Widget {

	public IToolMode mode;

	public WidgetButtonMode(int posX, int posY, IToolMode mode) {
		super(posX, posY, 18, 18);
		this.mode = mode;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiProvider gui) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(Translator.translateToLocal("mode." + mode.getName() + ".name"));
		return list;
	}

	@Override
	public void draw(IGuiProvider gui) {
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 0, 18, 18);
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 18 * mode.ordinal() + 18, 18, 18);
	}

	public void setMode(IToolMode mode) {
		this.mode = mode;
	}

	public IToolMode getMode() {
		return mode;
	}
}