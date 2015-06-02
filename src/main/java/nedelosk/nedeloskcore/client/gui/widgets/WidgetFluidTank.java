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

public class WidgetFluidTank extends Gui {

	private final ResourceLocation widget = new ResourceLocation("forestbotany", "textures/gui/widgets/widget_fluid_tank.png");
	IFluidTank tank;
	public int posX, posY;

	public WidgetFluidTank(IFluidTank tank, int posX, int posY) {
		this.tank = tank;
		this.posX = posX;
		this.posY = posY;
	}
	
	public void draw(int guiX, int guiY, int mouseX, int mouseY) {
		if (this.tank == null)
			return;

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(this.posX, this.posY, 0, 0, 18, 73);

		int iconHeightRemainder = (60 - 4) % 16;

		FluidStack fluid = this.tank.getFluid();
		if (fluid != null && fluid.amount > 0) {
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

			IIcon fluidIcon = fluid.getFluid().getStillIcon();

			if (iconHeightRemainder > 0) {
				drawTexturedModelRectFromIcon(this.posX + 1, this.posY + 1, fluidIcon, 16, iconHeightRemainder);
			}
			for (int i = 0; i < (60 - 6) / 16; i++) {
				drawTexturedModelRectFromIcon(this.posX + 1, this.posY + 1 + i * 16 + iconHeightRemainder, fluidIcon, 16, 18);
			}

			RenderUtils.bindTexture(widget);
			drawTexturedModalRect(this.posX + 1, this.posY + 1, 1, 1, 16, 72 - (int) (74 * ((float) fluid.amount / this.tank.getCapacity())));
		}

		RenderUtils.bindTexture(widget);
		drawTexturedModalRect(this.posX + 1, this.posY + 1, 19, 1, 16, 60);

		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public void drawTooltip(int x, int y) {

		List<String> description = new ArrayList<String>();

		if(tank.getFluidAmount() == 0)
		{
		}
		else{
		description.add(tank.getFluidAmount() + " " + StatCollector.translateToLocal(tank.getFluid().getLocalizedName()) + " mb / " + tank.getCapacity() + " " + StatCollector.translateToLocal(tank.getFluid().getLocalizedName()) + " mb");
		}
		RenderUtils.renderTooltip(x, y, description);
	}
}