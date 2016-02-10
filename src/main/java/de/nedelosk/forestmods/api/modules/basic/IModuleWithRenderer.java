package de.nedelosk.forestmods.api.modules.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularRenderer;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public interface IModuleWithRenderer extends IModule {

	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack);

	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile);
}
