package nedelosk.forestday.common.inventory;

import nedelosk.forestcore.library.inventory.ContainerBase;
import nedelosk.forestday.common.blocks.tiles.TileMachineBase;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench;
import nedelosk.forestday.common.crafting.WorkbenchRecipeManager;
import nedelosk.forestday.common.inventory.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWorkbench extends ContainerBase<TileWorkbench> {

	public ContainerWorkbench(TileWorkbench tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		// Input
		addSlotToContainer(new Slot(inventoryBase, 0, 26, 36){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return WorkbenchRecipeManager.isInput(stack);
			}
		});

		// Output
		addSlotToContainer(new SlotOutput(inventoryBase, 1, 134, 36));

		// Tool
		addSlotToContainer(new Slot(inventoryBase, 2, 80, 13){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return WorkbenchRecipeManager.isTool(stack);
			}
		});

		// Pattern
		addSlotToContainer(new Slot(inventoryBase, 3, 26, 13){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return WorkbenchRecipeManager.isPattern(stack);
			}
		});

		// Storage
		addSlotToContainer(new SlotOutput(inventoryBase, 4, 80, 61));

		// Chest Storage
		if (((TileMachineBase) inventoryBase).getBlockMetadata() == 2) {
			addSlotToContainer(new Slot(inventoryBase, 5, 177, 22));
			addSlotToContainer(new Slot(inventoryBase, 6, 177, 40));
			addSlotToContainer(new Slot(inventoryBase, 7, 177, 58));
			addSlotToContainer(new Slot(inventoryBase, 8, 177, 76));

			addSlotToContainer(new Slot(inventoryBase, 9, 195, 22));
			addSlotToContainer(new Slot(inventoryBase, 10, 195, 40));
			addSlotToContainer(new Slot(inventoryBase, 11, 195, 58));
			addSlotToContainer(new Slot(inventoryBase, 12, 195, 76));

			addSlotToContainer(new Slot(inventoryBase, 13, 213, 22));
			addSlotToContainer(new Slot(inventoryBase, 14, 213, 40));
			addSlotToContainer(new Slot(inventoryBase, 15, 213, 58));
			addSlotToContainer(new Slot(inventoryBase, 16, 213, 76));
		}
	}
	
    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotID == 37)
            {
                if (!this.mergeItemStack(itemstack1, 0, 36, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slotID != 36 && slotID != 38 && slotID != 39)
            {
                if (WorkbenchRecipeManager.isInput(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 36, 37, false))
                    {
                        return null;
                    }
                }
                else if (WorkbenchRecipeManager.isTool(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 38, 39, false))
                    {
                        return null;
                    }
                }else if (WorkbenchRecipeManager.isPattern(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 39, 40, false))
                    {
                        return null;
                    }
                }
                else if (slotID >= 0 && slotID < 27)
                {
                    if (!this.mergeItemStack(itemstack1, 27, 36, false))
                    {
                        return null;
                    }
                }
                else if (slotID >= 27 && slotID < 36 && !this.mergeItemStack(itemstack1, 0, 27, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 36, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

}
