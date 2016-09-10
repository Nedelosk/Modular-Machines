package de.nedelosk.modularmachines.common.blocks.tile;

import de.nedelosk.modularmachines.client.gui.GuiModuleCrafter;
import de.nedelosk.modularmachines.common.inventory.ContainerModuleCrafter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class TileModuleCrafter extends TileBaseInventory {

	public TileModuleCrafter() {
		super(11);
	}

	@Override
	public GuiContainer createGui(InventoryPlayer inventory) {
		return new GuiModuleCrafter(this, inventory);
	}

	@Override
	public Container createContainer(InventoryPlayer inventory) {
		return new ContainerModuleCrafter(this, inventory);
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
	}

	@Override
	public String getTitle() {
		return "moduleCrafter";
	}
}
