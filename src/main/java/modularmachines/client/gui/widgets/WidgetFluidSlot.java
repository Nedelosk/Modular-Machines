package modularmachines.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import modularmachines.common.utils.RenderUtil;

public class WidgetFluidSlot extends Widget {

	private IFluidTank tank;
	
	public WidgetFluidSlot(int posX, int posY, IFluidTank tank) {
		super(posX, posY, 18, 18);
		this.tank = tank;
	}
	
	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.color(1, 1, 1, 1);
		RenderUtil.texture(widgetTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 148, 18, pos.width, pos.height);
		drawFluid(guiLeft + pos.x + 1, guiTop + pos.y + 1, tank.getFluid());
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
	}
	
	public void drawFluid(int xPos, int yPos, FluidStack fluidStack){
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
		RenderUtil.bindBlockTexture();
		setGLColorFromInt(fluidColor);
		gui.drawTexturedModalRect(xPos, yPos, fluidStillSprite, 16, 16);
	}
	
	private static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		GlStateManager.color(red, green, blue, 1.0F);
	}

}
