package nedelosk.modularmachines.api.basic.modular;

import nedelosk.modularmachines.api.basic.modular.module.ModuleEntry;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IModularAssembler extends INBTTagable, IInventory {

	ItemStack getStackInSlot(String page, int id);
	
	ModuleEntry getModuleEntry(String page, int ID);
	
}

