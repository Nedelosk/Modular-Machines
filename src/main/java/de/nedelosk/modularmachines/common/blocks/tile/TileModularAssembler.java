package de.nedelosk.modularmachines.common.blocks.tile;

import de.nedelosk.modularmachines.client.gui.GuiModularAssembler;
import de.nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

public class TileModularAssembler extends TileMachineBase{

	public TileModularAssembler() {
		super(19);
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return new GuiModularAssembler(this, inventory);
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerModularAssembler(this, inventory);
	}

	@Override
	public String getTitle() {
		return "";
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
	}
}
