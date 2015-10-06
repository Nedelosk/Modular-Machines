package nedelosk.modularmachines.api.modular.machines.basic;

import net.minecraft.item.ItemStack;

public interface IModularRenderer<M extends IModular> {

	void renderMachineItemStack(ItemStack stack);
	
	void renderMachine(IModularTileEntity<M> entity, double x, double y, double z);
	
}
