package nedelosk.forestday.client.gui;

import nedelosk.forestday.client.gui.widget.WidgetFuelBar;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.configs.ForestDayConfig;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCampfire extends GuiMachine<TileCampfire> {

	public GuiCampfire(TileCampfire tile, InventoryPlayer inventory) {
		super(tile, inventory);
		widgetManager.add(new WidgetFuelBar(tile.fuelStorage,
				ForestDayConfig.campfireFuelStorageMax[(tile.getStackInSlot(4) != null) ? tile.getStackInSlot(4).getItemDamage() : 0], 7, 9));
	}

	@Override
	protected void renderProgressBar() {
		if (tile.isWorking) {
			this.drawTexturedModalRect(guiLeft + 66, guiTop + 36, 0, 166, 14, 14);
		}
		if (tile.getBurnTime() > 0 && tile.getBurnTime() < tile.getBurnTimeTotal()) {
			this.drawTexturedModalRect(guiLeft + 84, guiTop + 35, 0, 180, this.tile.getScaledProcess(24), 15);
		}
	}

	@Override
	protected String getGuiName() {
		return "machines/campfire";
	}
}
