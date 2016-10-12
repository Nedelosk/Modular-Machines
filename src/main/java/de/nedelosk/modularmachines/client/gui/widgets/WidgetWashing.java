package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetWashing extends Widget {

	public int workTime;
	public int worktTimeTotal;

	public WidgetWashing(int posX, int posY, int workTime, int workTimeTotal) {
		super(posX, posY, 20, 19);
		this.workTime = workTime;
		this.worktTimeTotal = workTimeTotal;
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ArrayList<String> list = new ArrayList<>();
		if (worktTimeTotal != 0) {
			list.add(workTime + " / " + worktTimeTotal);
		}
		return list;
	}

	@Override
	public void draw(IGuiBase gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		int process = (worktTimeTotal == 0) ? 0 : workTime * pos.width / worktTimeTotal;
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 28, 171, pos.width, pos.height);
		if (workTime > 0) {
			gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 48, 171, process, pos.height);
		}
		GlStateManager.disableAlpha();
	}
}
