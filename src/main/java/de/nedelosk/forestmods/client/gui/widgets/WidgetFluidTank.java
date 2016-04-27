package de.nedelosk.forestmods.client.gui.widgets;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.gui.Widget;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class WidgetFluidTank extends Widget {

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
		/*GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(widgetTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 132, 127, 18, 60);
		int iconHeightRemainder = (60 - 4) % 16;
		if (tank != null) {
			FluidStack fluid = this.tank.getFluid();
			if (fluid != null && fluid.amount > 0) {
				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
				IIcon fluidIcon = fluid.getFluid().getStillIcon();
				if (iconHeightRemainder > 0) {
					RenderUtil.drawTexturedModelRectFromIcon(gui.getGuiLeft() + pos.x + 1, gui.getGuiTop() + pos.y + 1, gui.getZLevel(), fluidIcon, 16,
							iconHeightRemainder);
				}
				for(int i = 0; i < (60 - 6) / 16; i++) {
					RenderUtil.drawTexturedModelRectFromIcon(gui.getGuiLeft() + pos.x + 1, gui.getGuiTop() + pos.y + 1 + i * 16 + iconHeightRemainder,
							gui.getZLevel(), fluidIcon, 16, 18);
				}
				RenderUtil.bindTexture(widgetTexture);
				gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x + 1, gui.getGuiTop() + pos.y + 1, 133, 128, 16,
						58 - (int) (60 * ((float) fluid.amount / this.tank.getCapacity())));
			}
			RenderUtil.bindTexture(widgetTexture);
			gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 150, 127, 18, 60);
		}
		GL11.glEnable(GL11.GL_LIGHTING);*/
		RenderUtil.bindTexture(widgetTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 132, 127, 18, 60);
		if (tank == null || tank.getCapacity() <= 0) {
			return;
		}

		FluidStack contents = tank.getFluid();
		if (contents == null || contents.amount <= 0 || contents.getFluid() == null) {
			return;
		}

		IIcon liquidIcon = contents.getFluid().getIcon(contents);
		if (liquidIcon == null) {
			return;
		}

		int scaledLiquid = (contents.amount * pos.height) / tank.getCapacity();
		if (scaledLiquid > pos.height) {
			scaledLiquid = pos.height;
		}

		RenderUtil.bindTexture(TextureMap.locationBlocksTexture);
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);

			int start = 0;
			while (scaledLiquid > 0) {
				int x;

				if (scaledLiquid > 16) {
					x = 16;
					scaledLiquid -= 16;
				} else {
					x = scaledLiquid;
					scaledLiquid = 0;
				}

				gui.getGui().drawTexturedModelRectFromIcon(gui.getGuiLeft() + pos.x + 1, gui.getGuiTop() - 1 + pos.y + pos.height - x - start, liquidIcon, 16, x);
				start += 16;
			}

			RenderUtil.bindTexture(widgetTexture);
			gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 150, 127, 16, 60);
		}
		GL11.glPopAttrib();
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase gui) {
		ArrayList<String> description = new ArrayList<String>();
		if (tank == null || tank.getFluidAmount() == 0) {
			description.add(StatCollector.translateToLocal("nc.tooltip.nonefluid"));
		} else {
			description.add(tank.getFluidAmount() + " " + StatCollector.translateToLocal(tank.getFluid().getLocalizedName()) + " mb / " + tank.getCapacity()
			+ " " + StatCollector.translateToLocal(tank.getFluid().getLocalizedName()) + " mb");
		}
		return description;
	}
}