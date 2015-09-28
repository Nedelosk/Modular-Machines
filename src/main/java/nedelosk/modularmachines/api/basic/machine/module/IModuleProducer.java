package nedelosk.modularmachines.api.basic.machine.module;

import java.util.ArrayList;

import nedelosk.modularmachines.api.basic.machine.module.recipes.NeiStack;

public interface IModuleProducer extends IModuleTool, IModuleInventory {

	int getBurnTime();
	
	int getBurnTimeTotal();
	
	ArrayList<NeiStack> addNEIStacks();
	
}
