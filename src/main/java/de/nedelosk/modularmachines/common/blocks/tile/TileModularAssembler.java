package de.nedelosk.modularmachines.common.blocks.tile;

import de.nedelosk.modularmachines.client.gui.GuiModularAssembler;
import de.nedelosk.modularmachines.common.inventory.ContainerAssembler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
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
		return new ContainerAssembler(this, inventory);
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
