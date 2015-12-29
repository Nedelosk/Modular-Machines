package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.forestcore.library.inventory.ContainerBase;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.modularmachines.common.multiblocks.MultiblockAirHeatingPlant;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAirHeatingPlant extends ContainerBase<TileMultiblockBase<MultiblockAirHeatingPlant>> {

	public ContainerAirHeatingPlant(TileMultiblockBase<MultiblockAirHeatingPlant> tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}

}
