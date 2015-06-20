package nedelosk.forestday.client.machines.base.gui;

import nedelosk.forestday.client.machines.base.gui.widget.WidgetFuelBar;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCampfire extends GuiMachine {

	protected WidgetFuelBar fuelBar = new WidgetFuelBar(((TileCampfire)tile).fuelStorage, ForestdayConfig.campfireFuelStorageMax[(((TileBaseInventory)tile).getStackInSlot(4) != null) ? ((TileBaseInventory)tile).getStackInSlot(4).getItemDamage() : 0], this.guiLeft + 7, this.guiTop + 9);;
	
	public GuiCampfire(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
        if(fuelBar != null)
        {
        	fuelBar.fuel = ((TileCampfire)tile).fuelStorage;
        	fuelBar.fuelMax = ForestdayConfig.campfireFuelStorageMax[(((TileBaseInventory)tile).getStackInSlot(4) != null) ? ((TileBaseInventory)tile).getStackInSlot(4).getItemDamage() : 0];
        	fuelBar.draw(fuelBar.posX, fuelBar.posY, x, y);
        }
        if (fuelBar != null)
            if (func_146978_c(fuelBar.posX, fuelBar.posY, 12, 69, x, y)) {
            	fuelBar.drawTooltip(x - this.guiLeft, y
                        - this.guiTop);
            }
	}

	@Override
	protected void renderProgressBar() {
		
	}

	@Override
	protected String getGuiName() {
		return "machines/campfire";
	}

}
