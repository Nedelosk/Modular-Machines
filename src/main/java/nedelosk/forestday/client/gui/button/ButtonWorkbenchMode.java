package nedelosk.forestday.client.gui.button;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nedelosk.forestcore.library.utils.RenderUtil;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class ButtonWorkbenchMode extends GuiButton {

	private Mode mode;
	private final ResourceLocation texture;

	public ButtonWorkbenchMode(int arg0, int x, int y, Mode mode, ResourceLocation texture) {
		super(arg0, x, y, 20, 20, "");
		this.mode = mode;
		this.texture = texture;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int mx, int my) {
		boolean inside = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;

		int i = (mode == Mode.stop_processing) ? 20 : 0;
		GL11.glPushMatrix();
		RenderUtil.bindTexture(texture);
		RenderUtil.drawTexturedModalRect(xPosition, yPosition, zLevel * 2, 0 + i, 0, 20, 20, 1F / 40F, 1F / 20F);
		GL11.glPopMatrix();

		if (mode != null && inside) {
			ArrayList tooltip = new ArrayList();
			tooltip.add(StatCollector.translateToLocal("forestday.tooltip.workbanch.mode." + mode.ordinal()));
			int tooltipY = (tooltip.size() - 1) * 10;
			RenderUtil.renderTooltip(mx, my + tooltipY, tooltip);
		}
	}

}
