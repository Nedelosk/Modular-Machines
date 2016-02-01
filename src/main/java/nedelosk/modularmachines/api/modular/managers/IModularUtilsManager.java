package nedelosk.modularmachines.api.modular.managers;

import cofh.api.energy.IEnergyHandler;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularUtilsManager extends IModularManager {

	IFluidHandler getFluidHandler();

	IEnergyHandler getEnergyHandler();

	void setFluidHandler(IFluidHandler fluidHandler);

	void setEnergyHandler(IEnergyHandler energyHandler);
}
