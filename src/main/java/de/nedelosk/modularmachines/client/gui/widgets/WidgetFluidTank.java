package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.client.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class WidgetFluidTank extends Widget {
	private static final int TEX_WIDTH = 16;
	private static final int TEX_HEIGHT = 16;
	private static final int MIN_FLUID_HEIGHT = 1;
	
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
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableBlend();
		
		RenderUtil.bindTexture(widgetTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 132, 127, 18, 60);
		
		FluidStack fluidStack = tank.getFluid();
		if (fluidStack == null) {
			return;
		}
		Fluid fluid = fluidStack.getFluid();
		if (fluid == null) {
			return;
		}

		TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
		ResourceLocation fluidStill = fluid.getStill();
		TextureAtlasSprite fluidStillSprite = null;
		if (fluidStill != null) {
			fluidStillSprite = textureMapBlocks.getTextureExtry(fluidStill.toString());
		}
		if (fluidStillSprite == null) {
			fluidStillSprite = textureMapBlocks.getMissingSprite();
		}

		int fluidColor = fluid.getColor(fluidStack);

		int scaledAmount = (fluidStack.amount * pos.height) / tank.getCapacity();
		if (fluidStack.amount > 0 && scaledAmount < MIN_FLUID_HEIGHT) {
			scaledAmount = MIN_FLUID_HEIGHT;
		}
		if (scaledAmount > pos.height) {
			scaledAmount = pos.height;
		}

		RenderUtil.bindBlockTexture();
		setGLColorFromInt(fluidColor);

		final int xTileCount = pos.width / TEX_WIDTH;
		final int xRemainder = pos.width - (xTileCount * TEX_WIDTH);
		final int yTileCount = scaledAmount / TEX_HEIGHT;
		final int yRemainder = scaledAmount - (yTileCount * TEX_HEIGHT);

		final int yStart = pos.y + pos.height;

		for (int xTile = 0; xTile <= xTileCount; xTile++) {
			for (int yTile = 0; yTile <= yTileCount; yTile++) {
				int width = (xTile == xTileCount) ? xRemainder : TEX_WIDTH;
				int height = (yTile == yTileCount) ? yRemainder : TEX_HEIGHT;
				int x = pos.x + (xTile * TEX_WIDTH);
				int y = yStart - ((yTile + 1) * TEX_HEIGHT);
				if (width > 0 && height > 0) {
					int maskTop = TEX_HEIGHT - height;
					int maskRight = TEX_WIDTH - width;

					drawFluidTexture(x, y, fluidStillSprite, maskTop, maskRight, 100);
				}
			}
		}

		GlStateManager.color(1, 1, 1, 1);
		
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, 200);
		RenderUtil.bindTexture(widgetTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 150, 127, 16, 60);
		GlStateManager.popMatrix();

		GlStateManager.disableBlend();
		GlStateManager.disableAlpha();
		
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
		/*RenderUtil.bindTexture(widgetTexture);
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
		if (scaledLiquid > pos.height) {FluidStackRenderer
			scaledLiquid = pos.height;
		}

		RenderUtil.bindBlockTexture();
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
		GL11.glPopAttrib();*/
	}
	
	private static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;

		GlStateManager.color(red, green, blue, 1.0F);
	}

	private static void drawFluidTexture(double xCoord, double yCoord, TextureAtlasSprite textureSprite, int maskTop, int maskRight, double zLevel) {
		double uMin = textureSprite.getMinU();
		double uMax = textureSprite.getMaxU();
		double vMin = textureSprite.getMinV();
		double vMax = textureSprite.getMaxV();
		uMax = uMax - (maskRight / 16.0 * (uMax - uMin));
		vMax = vMax - (maskTop / 16.0 * (vMax - vMin));

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexBuffer = tessellator.getBuffer();
		vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexBuffer.pos(xCoord, yCoord + 16, zLevel).tex(uMin, vMax).endVertex();
		vertexBuffer.pos(xCoord + 16 - maskRight, yCoord + 16, zLevel).tex(uMax, vMax).endVertex();
		vertexBuffer.pos(xCoord + 16 - maskRight, yCoord + maskTop, zLevel).tex(uMax, vMin).endVertex();
		vertexBuffer.pos(xCoord, yCoord + maskTop, zLevel).tex(uMin, vMin).endVertex();
		tessellator.draw();
	}

	@Override
	public ArrayList<String> getTooltip(IGuiBase gui) {
		ArrayList<String> description = new ArrayList<String>();
		if (tank == null || tank.getFluidAmount() == 0) {
			description.add(Translator.translateToLocal("nc.tooltip.nonefluid"));
		} else {
			description.add(tank.getFluidAmount() + " " + Translator.translateToLocal(tank.getFluid().getLocalizedName()) + " mb / " + tank.getCapacity()
			+ " " + Translator.translateToLocal(tank.getFluid().getLocalizedName()) + " mb");
		}
		return description;
	}
}