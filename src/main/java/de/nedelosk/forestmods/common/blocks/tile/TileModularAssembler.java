package de.nedelosk.forestmods.common.blocks.tile;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class TileModularAssembler extends TileMachineBase {

	public TileModularAssembler() {
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
	}

	@Override
	public String getMachineName() {
		return null;
	}
}
