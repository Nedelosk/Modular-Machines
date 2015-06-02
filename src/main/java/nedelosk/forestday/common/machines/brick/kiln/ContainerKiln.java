package nedelosk.forestday.common.machines.brick.kiln;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import nedelosk.nedeloskcore.common.inventory.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerKiln extends ContainerBase {
	
	public ContainerKiln(InventoryPlayer inventory, TileKiln tile) {
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
		addSlotToContainer(new Slot(tile, 0, 44, 26));
		addSlotToContainer(new Slot(tile, 1, 44, 44));
		
		//outnput
		addSlotToContainer(new SlotOutput(tile, 2, 116, 26));
		addSlotToContainer(new SlotOutput(tile, 3, 116, 44));
		
	}

}
