package nedelosk.forestday.client.machines.brick.gui;

import nedelosk.forestday.client.machines.base.gui.widget.WidgetHeatBar;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.machines.brick.kiln.TileKiln;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiKiln extends GuiBase {
	
	public WidgetHeatBar heatBar = new WidgetHeatBar(((TileKiln)tile).getHeat(), ForestdayConfig.kilnMinHeat, 71, 9);
	
	public GuiKiln(InventoryPlayer inventory, TileKiln tile) {
		super(tile, inventory);
		this.tile = tile;
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

		fontRenderer.drawString(StatCollector.translateToLocal("container.machine.kiln.resin"), 8, ySize - 163, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		
        if(heatBar != null)
        	heatBar.draw(heatBar.posX, heatBar.posY, x, y);
	}

	@Override
	protected void renderProgressBar() {
	    int sx = (width - xSize) / 2;
	    int sy = (height - ySize) / 2;
        if (tile.getBurnTime() > 0 && tile.getBurnTime() < tile.getBurnTimeTotal()) {
        this.drawTexturedModalRect(sx + 77, sy + 36, 176, 4, this.tile.getScaledProcess(22), 15);
        }
        this.drawTexturedModalRect(sx + 72, sy + 10, 176, 0, ((TileKiln)this.tile).getScaledHeat(32), 4);
		
	}

	@Override
	protected String getGuiName() {
		return "kiln";
	}

}
