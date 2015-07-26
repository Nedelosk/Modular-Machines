package nedelosk.modularmachines.client.gui.assembler;

import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonModularAssemblerSlotBack extends GuiButton {

	protected ResourceLocation guiTextureOverlay = RenderUtils.getResourceLocation("modularmachines", "modular_assembler_overlay", "gui");
	
	public GuiButtonModularAssemblerSlotBack(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_,18 , 9, null);
	}
	
	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
		RenderUtils.bindTexture(guiTextureOverlay);
		RenderUtils.drawTexturedModalRect(xPosition, yPosition, 1,174 ,247 ,18 , 9);
	}

}
