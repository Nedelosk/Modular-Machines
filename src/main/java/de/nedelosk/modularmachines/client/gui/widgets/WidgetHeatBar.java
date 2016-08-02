package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetHeatBar extends Widget {

	public int heat;
	public int heatTotal;
	public int posX, posY;

	public WidgetHeatBar(int heat, int heatTotal, int posX, int posY) {
		super(posX, posY, 12, 69);
		this.heat = heat;
		this.heatTotal = heatTotal;
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiProvider gui) {
		ArrayList<String> description = new ArrayList<String>();
		description.add(heat + " HU");
		return description;
	}

	@Override
	public void draw(IGuiProvider gui) {
		if (heatTotal == 0) {
			return;
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(widgetTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 156, 187, pos.width, pos.height);
		int energy = (heat * pos.height) / heatTotal;
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 69 - energy, 168, 256 - energy, pos.width, energy);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}