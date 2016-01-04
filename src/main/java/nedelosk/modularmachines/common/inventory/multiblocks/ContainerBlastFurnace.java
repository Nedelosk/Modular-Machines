package nedelosk.modularmachines.common.inventory.multiblocks;

import nedelosk.forestcore.library.inventory.ContainerBase;
import nedelosk.forestday.api.multiblocks.MultiblockModifierValveType.ValveType;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.modularmachines.common.crafting.BlastFurnaceRecipeManager;
import nedelosk.modularmachines.common.multiblocks.MultiblockBlastFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBlastFurnace extends ContainerBase<TileMultiblockBase<MultiblockBlastFurnace>> {

	public ContainerBlastFurnace(TileMultiblockBase<MultiblockBlastFurnace> tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		if (((TileMultiblockBase) inventoryBase).modifier.filter == null
				&& ((TileMultiblockBase) inventoryBase).master != null
				&& ((TileMultiblockBase) inventoryBase).modifier.valveType == ValveType.INPUT) {
			addSlotToContainer(new SlotBlastFurnace(((TileMultiblockBase) inventoryBase).master, 0, 53, 35));
			addSlotToContainer(new SlotBlastFurnace(((TileMultiblockBase) inventoryBase).master, 1, 71, 35));
			addSlotToContainer(new SlotBlastFurnace(((TileMultiblockBase) inventoryBase).master, 2, 89, 35));
			addSlotToContainer(new SlotBlastFurnace(((TileMultiblockBase) inventoryBase).master, 3, 107, 35));
		}
	}

	public class SlotBlastFurnace extends Slot {

		public SlotBlastFurnace(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
			super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return BlastFurnaceRecipeManager.isItemInput(stack);
		}
	}

}
