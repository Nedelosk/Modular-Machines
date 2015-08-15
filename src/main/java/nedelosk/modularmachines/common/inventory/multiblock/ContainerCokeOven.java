package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.modularmachines.common.crafting.CokeOvenRecipeManager;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.core.registry.NCBlocks;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import nedelosk.nedeloskcore.common.inventory.slots.SlotOutput;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCokeOven extends ContainerBase {

	public ContainerCokeOven(IInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		if(((TileMultiblockBase)inventoryBase).master != null && ((TileMultiblockBase)inventoryBase).getBlockType() == NCBlocks.Multiblock.block())
		{
			addSlotToContainer(new SlotCokeOven(((TileMultiblockBase)inventoryBase).master, 0, 53, 35));
			addSlotToContainer(new SlotOutput(((TileMultiblockBase)inventoryBase).master, 1, 107, 35));
		}
	}
	
	public class SlotCokeOven extends Slot
	{

		public SlotCokeOven(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
			super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return CokeOvenRecipeManager.isItemInput(stack);
		}
	}

}
