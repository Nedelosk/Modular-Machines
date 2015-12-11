package nedelosk.forestcore.api.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import org.lwjgl.opengl.GL11;

import nedelosk.forestcore.api.utils.RenderUtils;

public class WidgetFluidTank extends Widget {

	private final ResourceLocation widget = new ResourceLocation("forestday", "textures/gui/widgets/widget_fluid_tank.png");
	public IFluidTank tank;
	public int posX, posY;
	public int ID;

	public WidgetFluidTank(IFluidTank tank, int posX, int posY) {
		super(posX, posY, 18, 60);
		this.tank = tank;
		this.posX = posX;
		this.posY = posY;
	}

	public WidgetFluidTank(IFluidTank tank, int posX, int posY, int ID) {
		super(posX, posY, 18, 60);
		this.tank = tank;
		this.posX = posX;
		this.posY = posY;
		this.ID = ID;
	}

	@Override
	public void draw(IGuiBase gui) {

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		RenderUtils.bindTexture(widget);
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 0, 0, 18, 73);

		int iconHeightRemainder = (60 - 4) % 16;

		if (tank != null) {
			FluidStack fluid = this.tank.getFluid();
			if (fluid != null && fluid.amount > 0) {
				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

				IIcon fluidIcon = fluid.getFluid().getStillIcon();

				if (iconHeightRemainder > 0) {
					RenderUtils.drawTexturedModelRectFromIcon(gui.getGuiLeft() + pos.x + 1, gui.getGuiTop() + pos.y + 1, gui.getZLevel(), fluidIcon, 16, iconHeightRemainder);
				}
				for (int i = 0; i < (60 - 6) / 16; i++) {
					RenderUtils.drawTexturedModelRectFromIcon(gui.getGuiLeft() + pos.x + 1, gui.getGuiTop() + pos.y + 1 + i * 16 + iconHeightRemainder, gui.getZLevel(), fluidIcon, 16, 18);
				}

				RenderUtils.bindTexture(widget);
				gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x + 1, gui.getGuiTop() + pos.y + 1, 1, 1, 16, 72 - (int) (74 * ((float) fluid.amount / this.tank.getCapacity())));
			}
		}

		RenderUtils.bindTexture(widget);
		gui.drawTexturedModalRect(gui.getGuiLeft() + pos.x + 1, gui.getGuiTop() + pos.y + 1, 19, 1, 16, 60);

		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase gui) {
		ArrayList<String> description = new ArrayList<String>();

		if (tank == null || tank.getFluidAmount() == 0) {
			description.add(StatCollector.translateToLocal("nc.tooltip.nonefluid"));
		} else {
			description.add(tank.getFluidAmount() + " "
					+ StatCollector.translateToLocal(tank.getFluid().getLocalizedName()) + " mb / " + tank.getCapacity()
					+ " " + StatCollector.translateToLocal(tank.getFluid().getLocalizedName()) + " mb");
		}
		return description;
	}

	public void drawTooltip(int x, int y) {
		List<String> description = new ArrayList<String>();

		if (tank != null && tank.getFluidAmount() > 0) {
			description.add(tank.getFluidAmount() + " "
					+ StatCollector.translateToLocal(tank.getFluid().getLocalizedName()) + " mb / " + tank.getCapacity()
					+ " " + StatCollector.translateToLocal(tank.getFluid().getLocalizedName()) + " mb");
		}
		RenderUtils.renderTooltip(x, y, description);
	}
}