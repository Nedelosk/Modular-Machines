package nedelosk.modularmachines.api.modules.special;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public interface IModuleController<S extends IModuleSaver> extends IModule<S> {

	boolean buildMachine(IModular modular, ItemStack[] stacks, ModuleStack<IModuleController> moduleStack);
}
