package de.nedelosk.forestmods.client.gui;

import de.nedelosk.forestcore.library.gui.GuiBase;
import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenAccessPort;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCokeOvenAccessPort extends GuiBase<TileCokeOvenAccessPort> {

	public GuiCokeOvenAccessPort(TileCokeOvenAccessPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}

	@Override
	protected void renderProgressBar() {
	}

	@Override
	protected String getGuiName() {
		return "gui_coke_oven";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}
}
