package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.forestcore.library.inventory.ContainerBase;
import nedelosk.modularmachines.common.multiblock.blastfurnace.TileBlastFurnaceBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBlastFurnaceFluidPort extends ContainerBase<TileBlastFurnaceBase> {

	public ContainerBlastFurnaceFluidPort(TileBlastFurnaceBase tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}
}
