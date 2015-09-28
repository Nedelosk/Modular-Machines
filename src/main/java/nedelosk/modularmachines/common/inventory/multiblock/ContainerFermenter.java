package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.modularmachines.common.multiblocks.MultiblockFermenter;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerFermenter extends ContainerBase<TileMultiblockBase<MultiblockFermenter>> {

	public ContainerFermenter(TileMultiblockBase<MultiblockFermenter> tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}

}
