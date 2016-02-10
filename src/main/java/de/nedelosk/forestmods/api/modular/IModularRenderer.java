package de.nedelosk.forestmods.api.modular;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public interface IModularRenderer<M extends IModular> {

	void renderMachineItemStack(IModular machine, ItemStack stack);

	void renderMachine(IModularTileEntity<M> entity, double x, double y, double z);
}
