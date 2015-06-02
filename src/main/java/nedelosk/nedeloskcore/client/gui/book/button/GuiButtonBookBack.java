package nedelosk.nedeloskcore.client.gui.book.button;

import java.util.Arrays;
import java.util.List;

import nedelosk.nedeloskcore.client.gui.book.GuiBook;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiButtonBookBack extends GuiButtonBook {

	GuiBook gui;
	
	public GuiButtonBookBack(int par1, int par2, int par3, GuiBook gui) {
		super(par1, par2, par3, 18, 9, "");
		this.gui = gui;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(enabled)
		{
		field_146123_n = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
		int k = getHoverState(field_146123_n);

		par1Minecraft.renderEngine.bindTexture(gui.bookGuiTextures);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		drawTexturedModalRect(xPosition, yPosition, 36, k == 2 ? 180 : 189, 18, 9);

		List<String> tooltip = getTooltip();
		int tooltipY = (tooltip.size() - 1) * 10;
		if(k == 2)
			RenderUtils.renderTooltip(par2, par3 + tooltipY, tooltip);
		}
	}

	public List<String> getTooltip() {
		return Arrays.asList(StatCollector.translateToLocal("nc.back"));
	}

}
