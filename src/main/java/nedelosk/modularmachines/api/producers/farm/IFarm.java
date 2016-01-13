package nedelosk.modularmachines.api.producers.farm;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IFarm {

	void updateFarm(ModuleStack stack, IModular modular);

	String getName();
}
