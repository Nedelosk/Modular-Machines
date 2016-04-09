package de.nedelosk.forestmods.client.gui.widgets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestcore.utils.RenderUtil;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class WidgetHeatBar extends Widget {

	private final ResourceLocation widget = new ResourceLocation("forestmods", "textures/gui/widget_heat_bar.png");
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
	public ArrayList<String> getTooltip(IGuiBase gui) {
		ArrayList<String> description = new ArrayList<String>();
		description.add(heat + " Heat / " + heatTotal + " Heat");
		return description;
	}

	@Override
	public void draw(IGuiBase gui) {
		if (heatTotal == 0) {
			return;
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(widget);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 0, 0, 12, 69);
		int energy = (heat * 69) / heatTotal;
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 69 - energy, 12, 0 + 69 - energy, 12, energy);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}