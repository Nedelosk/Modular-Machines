package de.nedelosk.forestmods.client.gui.multiblocks;

import de.nedelosk.forestcore.gui.GuiBase;
import de.nedelosk.forestmods.common.blocks.tile.TileBlastFurnaceAccessPort;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBlastFurnaceAccessPort extends GuiBase<TileBlastFurnaceAccessPort> {

	public GuiBlastFurnaceAccessPort(TileBlastFurnaceAccessPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	@Override
	protected void renderProgressBar() {
	}

	@Override
	protected String getGuiName() {
		return "gui_blastfurnace_item";
	}

	@Override
	protected String getModName() {
		return "forestmods";
	}
}
