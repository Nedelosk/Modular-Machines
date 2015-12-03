package nedelosk.forestday.client.gui.widget;

import java.util.ArrayList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.api.utils.RenderUtils;

public class WidgetFuelBar extends Widget {

	private final ResourceLocation widget = new ResourceLocation("forestday", "textures/gui/widget_fuel_bar.png");
	public int fuelMax;
	public int fuel;

	public WidgetFuelBar(int fuel, int fuelMax, int posX, int posY) {
		super(posX, posY, 12, 69);
		this.fuel = fuel;
		this.fuelMax = fuelMax;
	}
	
	@Override
	public void draw(IGuiBase gui) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		gui.drawTexturedModalRect(pos.x + gui.getGuiLeft(), pos.y + gui.getGuiTop(), 0, 0, 12, 69);

		int fuel = (this.fuel * 69) / this.fuelMax;

		gui.drawTexturedModalRect(pos.x + gui.getGuiLeft(), pos.y + gui.getGuiTop() + 69 - fuel, 12, 0 + 69 - fuel, 12, fuel);

		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	@Override
	public ArrayList getTooltip() {
		ArrayList<String> description = new ArrayList<String>();
		description.add(fuel + " Fuel / " + fuelMax + " Fuel");
		return description;
	}
}