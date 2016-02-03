package de.nedelosk.forestmods.api.client;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import net.minecraft.item.ItemStack;

public interface IModularRenderer<M extends IModular> {

	void renderMachineItemStack(IModular machine, ItemStack stack);

	void renderMachine(IModularTileEntity<M> entity, double x, double y, double z);
}
