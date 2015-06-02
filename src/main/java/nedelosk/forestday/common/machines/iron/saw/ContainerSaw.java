package nedelosk.forestday.common.machines.iron.saw;

import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import nedelosk.nedeloskcore.common.inventory.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSaw extends ContainerBase {
	
	public ContainerSaw(InventoryPlayer inventory, TileSaw tile) {
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
		addSlotToContainer(new Slot(tile, 0, 46, 35));
		
		//output
		addSlotToContainer(new SlotOutput(tile, 3, 111, 35));
		addSlotToContainer(new SlotOutput(tile, 4, 129, 35));
		//output sawdust
		addSlotToContainer(new SlotOutput(tile, 1, 111, 6));
		addSlotToContainer(new SlotOutput(tile, 2, 129, 6));
		
		//Fluid 
		
	}

}
