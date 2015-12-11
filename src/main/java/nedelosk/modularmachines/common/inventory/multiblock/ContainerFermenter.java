package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.forestcore.api.inventory.ContainerBase;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.modularmachines.common.multiblocks.MultiblockFermenter;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerFermenter extends ContainerBase<TileMultiblockBase<MultiblockFermenter>> {

	public ContainerFermenter(TileMultiblockBase<MultiblockFermenter> tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}

}
