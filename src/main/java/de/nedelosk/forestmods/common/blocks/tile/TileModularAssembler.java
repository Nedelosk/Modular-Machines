package de.nedelosk.forestmods.common.blocks.tile;

import de.nedelosk.forestmods.common.modular.assembler.Assembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

public class TileModularAssembler extends TileMachineBase{

	public IAssembler assembler;

	public TileModularAssembler() {
		super(2 + 16*81);
		assembler = new Assembler(this);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		assembler.reload();
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return assembler.getGUIContainer(inventory);
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return assembler.getContainer(inventory);
	}

	@Override
	public String getTitle() {
		return "";
	}

	public IAssembler getAssembler() {
		return assembler;
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
	}
}
