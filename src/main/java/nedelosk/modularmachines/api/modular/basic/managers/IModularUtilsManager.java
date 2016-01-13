package nedelosk.modularmachines.api.modular.basic.managers;

import cofh.api.energy.IEnergyHandler;
import nedelosk.forestcore.library.INBTTagable;
import nedelosk.modularmachines.api.modular.IModular;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularUtilsManager extends INBTTagable {

	IFluidHandler getFluidHandler();

	IEnergyHandler getEnergyHandler();

	void setFluidHandler(IFluidHandler fluidHandler);

	void setEnergyHandler(IEnergyHandler energyHandler);

	void setModular(IModular modular);
}
