package nedelosk.modularmachines.api.producers.special;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public interface IProducerController extends IProducer {
	
	boolean buildMachine(IModular modular, ItemStack[] stacks, ModuleStack<IModule, IProducerController> moduleStack);
	
}
