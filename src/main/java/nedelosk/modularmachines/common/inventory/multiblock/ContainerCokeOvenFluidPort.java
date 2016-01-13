package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.forestcore.library.inventory.ContainerBase;
import nedelosk.modularmachines.common.multiblock.cokeoven.TileCokeOvenFluidPort;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCokeOvenFluidPort extends ContainerBase<TileCokeOvenFluidPort> {

	public ContainerCokeOvenFluidPort(TileCokeOvenFluidPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}
}
