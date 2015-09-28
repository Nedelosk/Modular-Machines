package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.modularmachines.common.multiblocks.MultiblockAirHeatingPlant;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAirHeatingPlant extends ContainerBase<TileMultiblockBase<MultiblockAirHeatingPlant>> {

	public ContainerAirHeatingPlant(TileMultiblockBase<MultiblockAirHeatingPlant> tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}

}
