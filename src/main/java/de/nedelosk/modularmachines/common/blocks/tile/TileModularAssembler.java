package de.nedelosk.modularmachines.common.blocks.tile;

import de.nedelosk.modularmachines.client.gui.GuiModularAssembler;
import de.nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileModularAssembler extends TileMachineBase{

	public TileModularAssembler() {
		super(19);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return new GuiModularAssembler(this, inventory);
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerModularAssembler(this, inventory);
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, EnumFacing side) {
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side) {
		return false;
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
