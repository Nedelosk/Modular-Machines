package nedelosk.modularmachines.common.inventory;

import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerModularAssembler extends ContainerBase{
	
	public ContainerModularAssembler(TileModularAssembler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}
	
	@Override
	protected void addInventory(InventoryPlayer inventory) {
		
	}

}
