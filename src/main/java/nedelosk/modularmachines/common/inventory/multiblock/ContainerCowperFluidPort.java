package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.forestcore.library.inventory.ContainerBase;
import nedelosk.modularmachines.common.multiblock.cowper.TileCowperBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCowperFluidPort extends ContainerBase<TileCowperBase> {

	public ContainerCowperFluidPort(TileCowperBase tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}

}
