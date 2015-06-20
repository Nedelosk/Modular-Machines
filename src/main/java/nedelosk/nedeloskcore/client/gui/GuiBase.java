package nedelosk.nedeloskcore.client.gui;

import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.common.inventory.slots.SlotPlan;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class GuiBase extends GuiContainer {

	protected ResourceLocation guiTexture = RenderUtils.getResourceLocation(getModName(), getGuiName(), "gui");
	protected ResourceLocation guiUpdateInventory = RenderUtils.getResourceLocation("nedeloskcore", "updateInventory", "gui");
	protected TileBase tile;
	
	public GuiBase(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile.getContainer(inventory));
		this.tile = tile;
	}
	
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {

        //fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        
        renderStrings(fontRendererObj, param1, param2);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		RenderUtils.bindTexture(guiTexture);	    
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
	    renderProgressBar();
	    
        if(tile instanceof TileMachineBase)
        {
    		//RenderUtils.bindTexture(guiUpdateInventory);
    		//drawTexturedModalRect(this.guiLeft + 83, this.guiTop - 30, 0, 0, 30, 30);
        }
	}
	
	protected abstract void renderStrings(FontRenderer fontRenderer, int x, int y);
	
	protected abstract void renderProgressBar();
    
    protected abstract String getGuiName();
    
    protected abstract String getModName();

}
