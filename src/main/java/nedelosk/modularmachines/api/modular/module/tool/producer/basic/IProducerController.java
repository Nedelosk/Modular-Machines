package nedelosk.modularmachines.api.modular.module.tool.producer.basic;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public interface IProducerController extends IProducer {
	
	boolean buildMachine(IModular modular, ItemStack[] stacks, ModuleStack<IModule, IProducerController> moduleStack);
	
}
