package nedelosk.modularmachines.api.client.renderer;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import net.minecraft.item.ItemStack;

public interface IModularRenderer<M extends IModular> {

	void renderMachineItemStack(IModular machine, ItemStack stack);

	void renderMachine(IModularTileEntity<M> entity, double x, double y, double z);

}
