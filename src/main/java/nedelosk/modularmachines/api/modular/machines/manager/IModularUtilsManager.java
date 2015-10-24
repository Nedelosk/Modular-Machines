package nedelosk.modularmachines.api.modular.machines.manager;

import cofh.api.energy.IEnergyHandler;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularUtilsManager extends INBTTagable{

	IFluidHandler getFluidHandler();
	
	IEnergyHandler getEnergyHandler();
	
	void setFluidHandler(IFluidHandler fluidHandler);
	
	void setEnergyHandler(IEnergyHandler energyHandler);
	
	void setModular(IModular modular);
	
}
