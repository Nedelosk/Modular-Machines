package nedelosk.nedeloskcore.client.gui.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

public class WidgetEnergyBar extends Gui {

	private final ResourceLocation widget = new ResourceLocation("forestbotany", "textures/gui/widgets/widget_energy_bar.png");
	IEnergyStorage storage;
	public int posX, posY;

	public WidgetEnergyBar(IEnergyStorage storage, int posX, int posY) {
		this.storage = storage;
		this.posX = posX;
		this.posY = posY;
	}

	public void draw(int guiX, int guiY, int mouseX, int mouseY) {
		if(storage == null)return;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(this.posX, this.posY, 0, 0, 12, 69);

		int energy = (this.storage.getEnergyStored() * 32) / this.storage.getMaxEnergyStored() ;
		
        this.drawTexturedModalRect(this.posX, this.posY, 69, 0, energy, 10);

		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public void drawTooltip(int x, int y) {

		List<String> description = new ArrayList<String>();

		description.add(storage.getEnergyStored() + " RF / " + storage.getMaxEnergyStored() + " RF");
		RenderUtils.renderTooltip(x, y, description);
	}
}