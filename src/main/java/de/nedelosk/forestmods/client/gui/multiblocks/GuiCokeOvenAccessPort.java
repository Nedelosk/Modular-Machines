package de.nedelosk.forestmods.client.gui.multiblocks;

import de.nedelosk.forestmods.client.gui.GuiForestBase;
import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenAccessPort;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCokeOvenAccessPort extends GuiForestBase<TileCokeOvenAccessPort> {

	public GuiCokeOvenAccessPort(TileCokeOvenAccessPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected String getGuiTexture() {
		return "gui_coke_oven";
	}
}
