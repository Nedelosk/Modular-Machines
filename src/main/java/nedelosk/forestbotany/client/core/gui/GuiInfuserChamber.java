package nedelosk.forestbotany.client.core.gui;

import nedelosk.forestbotany.common.blocks.tile.TileInfuserBase;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widgets.WidgetEnergyBar;
import nedelosk.nedeloskcore.client.gui.widgets.WidgetFluidTank;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiInfuserChamber extends GuiBase {

	protected WidgetFluidTank fluidTank = new WidgetFluidTank(((TileInfuserBase)tile).getTank(), this.guiLeft + 151, this.guiTop + 15);
	protected WidgetFluidTank fluidTankWater = new WidgetFluidTank(((TileInfuserBase)tile).getTankWater(), this.guiLeft + 22, this.guiTop + 15);
	protected WidgetEnergyBar energyBar = new WidgetEnergyBar(((TileInfuserBase)tile).getStorage(), this.guiLeft + 7, this.guiTop + 9);
	
	public GuiInfuserChamber(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
		ySize = 181;
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
        if(energyBar != null)
        	energyBar.draw(energyBar.posX, energyBar.posY, x, y);
        if (energyBar != null)
            if (func_146978_c(energyBar.posX, energyBar.posY, 12, 69, x, y)) {
            	energyBar.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
        if(fluidTank != null)
        	fluidTank.draw(fluidTank.posX, fluidTank.posY, x, y);
        if (fluidTank != null)
            if (func_146978_c(fluidTank.posX, fluidTank.posY, 18, 73, x, y)) {
            	fluidTank.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
        
        if(fluidTankWater != null)
        	fluidTankWater.draw(fluidTankWater.posX, fluidTankWater.posY, x, y);
        if (fluidTankWater != null)
            if (func_146978_c(fluidTankWater.posX, fluidTankWater.posY, 18, 73, x, y)) {
            	fluidTankWater.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
	}

	@Override
	protected void renderProgressBar() {
		
	}

	@Override
	protected String getGuiName() {
		return "infuser_chamber";
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		guiTexture = RenderUtils.getResourceLocationBotany(getGuiName(), "gui");
		RenderUtils.bindTexture(guiTexture);	    
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
	    renderProgressBar();
	}

}
