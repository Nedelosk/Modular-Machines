package nedelosk.modularmachines.api.basic.machine;

import net.minecraft.item.ItemStack;

public interface IModularWorkbench {

	ItemStack getStackInRowAndColumn(int par1, int par2);
	
	int getTier();
}
