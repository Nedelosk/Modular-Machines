package de.nedelosk.modularmachines.client.gui;

import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiModularAssembler extends GuiForestBase<TileModularAssembler> {

	public GuiModularAssembler(TileModularAssembler assembler, InventoryPlayer inventory) {
		super(assembler, inventory);

		xSize = 180;
		ySize = 256;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	}

	@Override
	protected String getGuiTexture() {
		return "modular_assembler";
	}
}
