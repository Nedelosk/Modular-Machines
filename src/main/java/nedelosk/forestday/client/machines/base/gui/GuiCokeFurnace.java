package nedelosk.forestday.client.machines.base.gui;

import nedelosk.forestday.client.machines.base.gui.widget.WidgetHeatBar;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.machines.base.fluid.heater.TileFluidHeater;
import nedelosk.forestday.common.machines.base.furnace.coke.TileCokeFurnace;
import nedelosk.forestday.common.machines.base.wood.kiln.TileKiln;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiCokeFurnace extends GuiMachine {
	
	protected WidgetFluidTank fluidTank = new WidgetFluidTank(((TileCokeFurnace)tile).getTank(), guiLeft + 148, guiTop + 12);
	//protected WidgetHeatBar heatBar = new WidgetHeatBar(((TileCokeFurnace)tile).getHeat(), 150, 71, 9);
	
	public GuiCokeFurnace(InventoryPlayer inventory, TileMachineBase tile) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

		fontRenderer.drawString(StatCollector.translateToLocal("container.machine.furnace.coke"), 8, ySize - 163, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		
        if(fluidTank != null)
        	fluidTank.draw(fluidTank.posX, fluidTank.posY, x, y);
        if (fluidTank != null)
            if (func_146978_c(fluidTank.posX, fluidTank.posY, 18, 73, x, y)) {
            	fluidTank.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
           }
           
       // if(heatBar != null)
         // heatBar.draw(heatBar.posX, heatBar.posY, x, y);
	}

	@Override
	protected void renderProgressBar() {
	    int sx = (width - xSize) / 2;
	    int sy = (height - ySize) / 2;
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    
	    RenderUtils.bindBlockTexture();
	    RenderUtils.renderGuiTank(((TileCokeFurnace)tile).getTank(), guiLeft + 148, guiTop + 15, zLevel, 16,52);
	}

	@Override
	protected String getGuiName() {
		return "machines/coke_furnace";
	}

}
