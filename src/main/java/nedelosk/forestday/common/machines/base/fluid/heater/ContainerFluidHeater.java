package nedelosk.forestday.common.machines.base.fluid.heater;

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

public class ContainerFluidHeater extends ContainerBase {
	
	public ContainerFluidHeater(InventoryPlayer inventory, TileFluidHeater tile) {
		super(tile, inventory);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) 
	{
        return null;
    }

	@Override
	protected void addSlots(InventoryPlayer inventory) {	
		addSlotToContainer(new Slot(inventoryBase, 0, 8, 33));
		addSlotToContainer(new Slot(inventoryBase, 1, 139, 33));
	}

}
