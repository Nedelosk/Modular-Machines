package de.nedelosk.forestmods.client.gui;

import de.nedelosk.forestcore.library.gui.GuiBase;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperAccessPort;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCowperAccessPort extends GuiBase<TileCowperAccessPort> {

	public GuiCowperAccessPort(TileCowperAccessPort tile, InventoryPlayer inventory) {
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
		return "modularmachines";
	}
}
