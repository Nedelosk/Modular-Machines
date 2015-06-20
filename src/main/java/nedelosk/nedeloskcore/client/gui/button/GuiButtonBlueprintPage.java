package nedelosk.nedeloskcore.client.gui.button;

import java.util.Arrays;

import nedelosk.nedeloskcore.client.gui.book.GuiBook;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiButtonBlueprintPage extends GuiButton {

	boolean right;

	public GuiButtonBlueprintPage(int par1, int par2, int par3, boolean right) {
		super(par1, par2, par3, 18, 10, "");
		this.right = right;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(enabled) {
			field_146123_n = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			int k = getHoverState(field_146123_n);

			par1Minecraft.renderEngine.bindTexture(new ResourceLocation("nedeloskcore", "textures/gui/blueprint.png"));
			GL11.glColor4f(1F, 1F, 1F, 1F);
			drawTexturedModalRect(xPosition, yPosition, k == 2 ? 18 : 0, right ? 104 : 114, 18, 10);

			if(k == 2)
				RenderUtils.renderTooltip(par2, par3, Arrays.asList(StatCollector.translateToLocal(right ? "nc.nextPage" : "nc.prevPage")));
		}
	}
	
	

}
