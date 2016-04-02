package de.nedelosk.forestmods.client.gui;

import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiModularAssembler extends GuiForestBase<TileModularAssembler> {

	public GuiModularAssembler(TileModularAssembler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	@Override
	protected void render() {
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}
}
