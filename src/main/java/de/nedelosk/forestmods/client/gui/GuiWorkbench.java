package de.nedelosk.forestmods.client.gui;

import de.nedelosk.forestmods.client.gui.buttons.ButtonWorkbenchMode;
import de.nedelosk.forestmods.common.blocks.tile.TileWorkbench;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiWorkbench extends GuiForestBase<TileWorkbench> {

	public GuiWorkbench(TileWorkbench tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if (tile.getBlockMetadata() == 2) {
			xSize = 234;
		}
	}

	@Override
	public void addButtons() {
		buttonManager.add(new ButtonWorkbenchMode(guiLeft + 130, guiTop + 10, tile.getMode()));
	}

	@Override
	protected void renderProgressBar() {
		if (tile.getBurnTime() > 0 && tile.getBurnTime() < tile.getBurnTimeTotal()) {
			this.drawTexturedModalRect(guiLeft + 49, guiTop + 37, 30, 234, this.tile.getScaledProcess(76), 22);
		}
	}

	@Override
	protected String getGuiName() {
		return "machines/workbench";
	}
}
