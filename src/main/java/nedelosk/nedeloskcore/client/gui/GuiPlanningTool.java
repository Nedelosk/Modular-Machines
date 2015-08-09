package nedelosk.nedeloskcore.client.gui;

import nedelosk.nedeloskcore.common.inventory.ContainerPlanningTool;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPlanningTool extends GuiContainer {

	protected ResourceLocation guiTexture = RenderUtils.getResourceLocation("nedeloskcore", "planning_tool", "gui");
	
	public GuiPlanningTool(ContainerPlanningTool toolCon, InventoryPlayer inventory) {
		super(toolCon);
	}
	
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {

    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		RenderUtils.bindTexture(guiTexture);	    
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
	}

}
