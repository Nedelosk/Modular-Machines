package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;

public class WidgetFuelBar extends Widget {

	public int fuelMax;
	public int fuel;

	public WidgetFuelBar(int fuel, int fuelMax, int posX, int posY) {
		super(posX, posY, 12, 69);
		this.fuel = fuel;
		this.fuelMax = fuelMax;
	}

	@Override
	public void draw(IGuiProvider gui) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(widgetTexture);
		gui.getGui().drawTexturedModalRect(pos.x + gui.getGuiLeft(), pos.y + gui.getGuiTop(), 132, 187, pos.width, pos.height);
		int fuel = (this.fuel * pos.height) / this.fuelMax;
		gui.getGui().drawTexturedModalRect(pos.x + gui.getGuiLeft(), pos.y + gui.getGuiTop() + 69 - fuel, 144, 256 - fuel, pos.width, fuel);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public ArrayList getTooltip(IGuiProvider gui) {
		ArrayList<String> description = new ArrayList<String>();
		description.add(fuel + " Fuel / " + fuelMax + " Fuel");
		return description;
	}
}