package nedelosk.modularmachines.api.basic.modular;

import net.minecraft.item.ItemStack;

public interface IModularWorkbench {

	ItemStack getStackInRowAndColumn(int par1, int par2);
	
	int getTier();
	
	boolean canDrain(int tier);
	
	boolean drainMaterials(int tier);
}
