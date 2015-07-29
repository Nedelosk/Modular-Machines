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
	
	public GuiFluidHeater(InventoryPlayer inventory, TileFluidHeater tile) {
		super(tile, inventory);
		widgetManager.add(new WidgetFluidTank(((TileFluidHeater)tile).getTankInput(), guiLeft + 30, guiTop + 12));
		widgetManager.add(new WidgetFluidTank(((TileFluidHeater)tile).getTankInput2(), guiLeft + 54, guiTop + 12));
		widgetManager.add(new WidgetFluidTank(((TileFluidHeater)tile).getTankOutput(), guiLeft + 113, guiTop + 12));
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

    	fontRendererObj.drawString(StatCollector.translateToLocal("container.machine.furnace.fluidheater"), 8, ySize - 163, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        
        if(heatBar != null)
        	heatBar.draw(heatBar.posX, heatBar.posY, x, y);
	}

	@Override
	protected void renderProgressBar() {
	    int sx = (width - xSize) / 2;
	    int sy = (height - ySize) / 2;
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected String getGuiName() {
		return "machines/fluid_heater";
	}

}
