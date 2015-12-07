package nedelosk.modularmachines.api.producers.fluids;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IProducerWithFluid {

	int getItemInputs(ModuleStack<IModule, IProducer> stack);
	
	int getItemOutputs(ModuleStack<IModule, IProducer> stack);
	
	boolean useFluids(ModuleStack<IModule, IProducer> stack);
	
	int getFluidInputs(ModuleStack<IModule, IProducer> stack);
	
	int getFluidOutputs(ModuleStack<IModule, IProducer> stack);
	
}
