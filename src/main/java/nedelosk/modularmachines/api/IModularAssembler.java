package nedelosk.modularmachines.api;

import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IModularAssembler extends INBTTagable, IInventory {

	ItemStack getStackInSlot(String page, int id);
	
}

