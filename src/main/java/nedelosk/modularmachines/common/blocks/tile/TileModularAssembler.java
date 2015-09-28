package nedelosk.modularmachines.common.blocks.tile;

import nedelosk.modularmachines.client.gui.assembler.GuiModularAssembler;
import nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class TileModularAssembler extends TileBaseInventory {
	
	public TileModularAssembler() {
		super(9);
	}

	@Override
	public String getMachineTileName() {
		return "modular.assembler";
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerModularAssembler(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiModularAssembler(this, inventory);
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int p_102008_3_) 
	{
		return false;
	}

}
