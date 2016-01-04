package nedelosk.modularmachines.api.producers.fluids;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IProducerWithFluid {

	boolean useFluids(ModuleStack<IModule, IProducer> stack);

	int getFluidInputs(ModuleStack<IModule, IProducer> stack);

	int getFluidOutputs(ModuleStack<IModule, IProducer> stack);

}
