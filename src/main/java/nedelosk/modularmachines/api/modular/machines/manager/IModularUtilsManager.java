package nedelosk.modularmachines.api.modular.machines.manager;

import cofh.api.energy.IEnergyHandler;
import nedelosk.forestday.api.INBTTagable;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularUtilsManager extends INBTTagable {

	IFluidHandler getFluidHandler();

	IEnergyHandler getEnergyHandler();

	void setFluidHandler(IFluidHandler fluidHandler);

	void setEnergyHandler(IEnergyHandler energyHandler);

	void setModular(IModular modular);

}
