package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetProgressBar extends Widget {

	public int burntime;
	public int burntimeTotal;

	public WidgetProgressBar(int posX, int posY, int burntime, int burntimeTotal) {
		super(posX, posY, 22, 17);
		this.burntime = burntime;
		this.burntimeTotal = burntimeTotal;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiProvider gui) {
		ArrayList<String> list = new ArrayList<String>();
		if (burntimeTotal != 0) {
			list.add(burntime + " / " + burntimeTotal);
		}
		return list;
	}

	@Override
	public void draw(IGuiProvider gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		int process = (burntimeTotal == 0) ? 0 : burntime * pos.width / burntimeTotal;
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 54, 0, pos.width, pos.height);
		if (burntime > 0) {
			gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 76, 0, process, pos.height);
		}
		GlStateManager.disableAlpha();
	}
}