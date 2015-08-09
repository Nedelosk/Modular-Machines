package nedelosk.forestday.common.machines.base.furnace.coke;

import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import nedelosk.nedeloskcore.common.inventory.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCokeFurnace extends ContainerBase {

	private int heat;
	
	public ContainerCokeFurnace(InventoryPlayer inventory, TileCokeFurnace tile) {
		super(tile, inventory);
		
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) 
    {
        return null;
    }

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		
		//input
		addSlotToContainer(new Slot(inventoryBase, 0, 40, 34));
		
		//outnput
		addSlotToContainer(new SlotOutput(inventoryBase, 1, 111, 16));
		addSlotToContainer(new SlotOutput(inventoryBase, 2, 111, 34));
		addSlotToContainer(new SlotOutput(inventoryBase, 3, 111, 52));
	}

}
