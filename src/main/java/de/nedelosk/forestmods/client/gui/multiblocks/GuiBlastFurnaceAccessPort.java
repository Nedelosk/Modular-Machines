package de.nedelosk.forestmods.client.gui.multiblocks;

import de.nedelosk.forestmods.client.gui.GuiForestBase;
import de.nedelosk.forestmods.common.blocks.tile.TileBlastFurnaceAccessPort;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBlastFurnaceAccessPort extends GuiForestBase<TileBlastFurnaceAccessPort> {

	public GuiBlastFurnaceAccessPort(TileBlastFurnaceAccessPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected String getGuiTexture() {
		return "gui_blastfurnace_item";
	}
}