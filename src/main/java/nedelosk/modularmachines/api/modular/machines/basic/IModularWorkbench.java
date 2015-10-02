package nedelosk.modularmachines.api.modular.machines.basic;

import net.minecraft.item.ItemStack;

public interface IModularWorkbench {

	ItemStack getStackInRowAndColumn(int par1, int par2);
	
	int getTier();
}
