package nedelosk.forestday.client.machines.base.gui.widget;

import java.util.ArrayList;
import java.util.List;

import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cofh.api.energy.IEnergyStorage;

public class WidgetHeatBar extends Gui {

	private final ResourceLocation widget = new ResourceLocation("forestday", "textures/gui/widget_heat_bar.png");
	public int heat;
	public int heatMax;
	public int posX, posY;
	
	public WidgetHeatBar(int heat, int heatMax, int posX, int posY) {
		this.heatMax = heatMax;
		this.heat = heat;
		this.posX = posX;
		this.posY = posY;
	}

	public void draw(int guiX, int guiY, int mouseX, int mouseY) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(this.posX, this.posY, 0, 0, 34, 6);

		int heat = (this.heat * 32) / this.heatMax;
		
        drawTexturedModalRect(this.posX + 1, this.posY + 1, 34, 0, heat, 4);

		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
}
