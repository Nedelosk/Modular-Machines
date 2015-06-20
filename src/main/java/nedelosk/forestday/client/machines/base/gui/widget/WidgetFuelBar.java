package nedelosk.forestday.client.machines.base.gui.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cofh.api.energy.IEnergyStorage;

public class WidgetFuelBar extends Gui {

	private final ResourceLocation widget = new ResourceLocation("forestday", "textures/gui/widget_fuel_bar.png");
	public int fuelMax;
	public int fuel;
	public int posX, posY;

	public WidgetFuelBar(int fuel, int fuelMax, int posX, int posY) {
		this.fuel = fuel;
		this.posX = posX;
		this.posY = posY;
		this.fuelMax = fuelMax;
	}

	public void draw(int guiX, int guiY, int mouseX, int mouseY) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(this.posX, this.posY, 0, 0, 12, 69);

		int fuel = (this.fuel * 69) / this.fuelMax ;
		
        this.drawTexturedModalRect(this.posX, this.posY + 69 - fuel, 12, 0  + 69 - fuel, 12, fuel);

		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public void drawTooltip(int x, int y) {

		List<String> description = new ArrayList<String>();

		description.add(fuel + " Fuel / " + fuelMax + " Fuel");
		RenderUtils.renderTooltip(x, y, description);
	}
}