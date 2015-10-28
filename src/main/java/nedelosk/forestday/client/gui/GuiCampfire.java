package nedelosk.forestday.client.gui;

import nedelosk.forestday.client.gui.widget.WidgetFuelBar;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.configs.ForestDayConfig;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCampfire extends GuiMachine<TileCampfire> {

	protected WidgetFuelBar fuelBar = new WidgetFuelBar(tile.fuelStorage,
			ForestDayConfig.campfireFuelStorageMax[(tile.getStackInSlot(4) != null)
					? tile.getStackInSlot(4).getItemDamage() : 0],
			this.guiLeft + 7, this.guiTop + 9);;

	public GuiCampfire(TileCampfire tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		if (fuelBar != null) {
			fuelBar.fuel = tile.fuelStorage;
			fuelBar.fuelMax = ForestDayConfig.campfireFuelStorageMax[(tile.getStackInSlot(4) != null)
					? tile.getStackInSlot(4).getItemDamage() : 0];
			fuelBar.draw(fuelBar.posX, fuelBar.posY, x, y);
		}
		if (fuelBar != null)
			if (func_146978_c(fuelBar.posX, fuelBar.posY, 12, 69, x, y)) {
				fuelBar.drawTooltip(x - this.guiLeft, y - this.guiTop);
			}
	}

	@Override
	protected void renderProgressBar() {
		if (tile.isWorking) {
			this.drawTexturedModalRect(guiLeft + 66, guiTop + 36, 0, 166, 14, 14);
		}
		if (tile.getBurnTime() > 0 && tile.getBurnTime() <= tile.getBurnTimeTotal()) {
			this.drawTexturedModalRect(guiLeft + 84, guiTop + 35, 0, 180, this.tile.getScaledProcess(24), 15);
		}
	}

	@Override
	protected String getGuiName() {
		return "machines/campfire";
	}

}
