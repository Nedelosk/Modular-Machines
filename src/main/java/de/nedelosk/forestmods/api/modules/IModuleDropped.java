package de.nedelosk.forestmods.api.modules;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public interface IModuleDropped extends IModule {

	ItemStack onDropItem(ModuleStack<IModule, IModuleSaver> stack, IModular modular);
}
