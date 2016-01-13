package nedelosk.modularmachines.client.gui.assembler.element;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;

/**
 * Represents a GUI element INSIDE the graphics. The coordinates all refer to
 * the coordinates inside the graphics!
 */
@SideOnly(Side.CLIENT)
public class GuiElement {

	public static int defaultTexW = 256;
	public static int defaultTexH = 256;
	public final int x;
	public final int y;
	public final int w;
	public final int h;
	public int texW;
	public int texH;

	public GuiElement(int x, int y, int w, int h, int texW, int texH) {
		this(x, y, w, h);
		setTextureSize(texW, texH);
		defaultTexW = texW;
		defaultTexH = texH;
	}

	public GuiElement(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		setTextureSize(defaultTexW, defaultTexH);
	}

	public GuiElement setTextureSize(int w, int h) {
		texW = w;
		texH = h;
		return this;
	}

	public GuiElement shift(int xd, int yd) {
		return new GuiElement(this.x + xd, this.y + yd, this.w, this.h, this.texW, this.texH);
	}

	public int draw(int xPos, int yPos) {
		drawModalRectWithCustomSizedTexture(xPos, yPos, x, y, w, h, texW, texH);
		return w;
	}

	public void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
		float f4 = 1.0F / textureWidth;
		float f5 = 1.0F / textureHeight;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y + height, 0.0D, u * f4, (v + height) * f5);
		tessellator.addVertexWithUV(x + width, y + height, 0.0D, (u + width) * f4, (v + height) * f5);
		tessellator.addVertexWithUV(x + width, y, 0.0D, (u + width) * f4, v * f5);
		tessellator.addVertexWithUV(x, y, 0.0D, u * f4, v * f5);
		tessellator.draw();
	}

	public static class Builder {

		public int w;
		public int h;

		public Builder(int w, int h) {
			this.w = w;
			this.h = h;
		}

		public GuiElement get(int x, int y, int w, int h) {
			return new GuiElement(x, y, w, h, this.w, this.h);
		}
	}
}
