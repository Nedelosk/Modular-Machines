package nedelosk.forestday.client.machines.base.gui;

import nedelosk.forestbotany.common.blocks.tile.TileInfuserBase;
import nedelosk.forestday.client.machines.base.gui.widget.WidgetHeatBar;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.machines.base.fluid.heater.TileFluidHeater;
import nedelosk.forestday.common.machines.base.wood.kiln.TileKiln;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiFluidHeater extends GuiMachine {
	
	public WidgetHeatBar heatBar = new WidgetHeatBar(((TileFluidHeater)tile).getHeat(), ForestdayConfig.fluidheaterMinHeat, 76, 5);
	protected WidgetFluidTank fluidTank1 = new WidgetFluidTank(((TileFluidHeater)tile).getTankInput(), guiLeft + 30, guiTop + 12);
	protected WidgetFluidTank fluidTank2= new WidgetFluidTank(((TileFluidHeater)tile).getTankInput2(), guiLeft + 54, guiTop + 12);
	
	protected WidgetFluidTank fluidTankOutput= new WidgetFluidTank(((TileFluidHeater)tile).getTankOutput(), guiLeft + 113, guiTop + 12);
	
	public GuiFluidHeater(InventoryPlayer inventory, TileFluidHeater tile) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

    	fontRendererObj.drawString(StatCollector.translateToLocal("container.machine.furnace.fluidheater"), 8, ySize - 163, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        
        if(fluidTank1 != null)
        	fluidTank1.draw(fluidTank1.posX, fluidTank1.posY, x, y);
        if (fluidTank1 != null)
            if (func_146978_c(fluidTank1.posX, fluidTank1.posY, 18, 73, x, y)) {
            	fluidTank1.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
        
        if(fluidTank2 != null)
        	fluidTank2.draw(fluidTank2.posX, fluidTank2.posY, x, y);
        if (fluidTank2 != null)
            if (func_146978_c(fluidTank2.posX, fluidTank2.posY, 18, 73, x, y)) {
            	fluidTank2.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
        
        if(fluidTankOutput != null)
        	fluidTankOutput.draw(fluidTankOutput.posX, fluidTankOutput.posY, x, y);
        if (fluidTankOutput != null)
            if (func_146978_c(fluidTankOutput.posX, fluidTankOutput.posY, 18, 73, x, y)) {
            	fluidTankOutput.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
        
        if(heatBar != null)
        	heatBar.draw(heatBar.posX, heatBar.posY, x, y);
	}

	@Override
	protected void renderProgressBar() {
	    int sx = (width - xSize) / 2;
	    int sy = (height - ySize) / 2;
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    
	    RenderUtils.renderGuiTank(((TileFluidHeater)tile).getTankInput(), guiLeft + 30, guiTop + 16, zLevel, 16,52);
	    
	    RenderUtils.renderGuiTank(((TileFluidHeater)tile).getTankInput2(), guiLeft + 54, guiTop + 16, zLevel, 16,52);
	    
	    RenderUtils.renderGuiTank(((TileFluidHeater)tile).getTankOutput(), guiLeft + 113, guiTop + 16, zLevel, 16,52);
	}

	@Override
	protected String getGuiName() {
		return "machines/fluid_heater";
	}

}
