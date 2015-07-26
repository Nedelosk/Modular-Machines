package nedelosk.modularmachines.client.gui.assembler;

import org.lwjgl.opengl.GL11;

import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiButtonModularAssemblerBuildMachine extends GuiButton {

	protected ResourceLocation guiTextureOverlay = RenderUtils.getResourceLocation("modularmachines", "modular_assembler_overlay", "gui");
	
	public GuiButtonModularAssemblerBuildMachine(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_) {
		super(p_i1020_1_, p_i1020_2_, p_i1020_3_, 47, 11, null);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		boolean inside = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.bindTexture(guiTextureOverlay);
		RenderUtils.drawTexturedModalRect(xPosition, yPosition, 1,56 ,245 ,47 , 11);
		
		RenderUtils.glDrawScaledString(mc.fontRenderer, StatCollector.translateToLocal("mm.modularassembler.build_machine"), xPosition + 2, yPosition + 3, 0.7F, 14737632);
	}

}
