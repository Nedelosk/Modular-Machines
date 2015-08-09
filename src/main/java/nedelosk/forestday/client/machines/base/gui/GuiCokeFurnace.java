package nedelosk.forestday.client.machines.base.gui;

import nedelosk.forestday.common.machines.base.furnace.coke.TileCokeFurnace;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiCokeFurnace extends GuiMachine {
	
	//protected WidgetHeatBar heatBar = new WidgetHeatBar(((TileCokeFurnace)tile).getHeat(), 150, 71, 9);
	
	public GuiCokeFurnace(InventoryPlayer inventory, TileMachineBase tile) {
		super(tile, inventory);
		widgetManager.add(new WidgetFluidTank(((TileCokeFurnace)tile).getTank(), guiLeft + 148, guiTop + 12));
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

		fontRenderer.drawString(StatCollector.translateToLocal("container.machine.furnace.coke"), 8, ySize - 163, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
           
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
