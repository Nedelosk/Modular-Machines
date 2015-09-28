package nedelosk.modularmachines.common.machines.manager;

import cofh.api.energy.IEnergyHandler;
import nedelosk.modularmachines.api.basic.machine.manager.IModularUtilsManager;
import net.minecraftforge.fluids.IFluidHandler;

public class ModularUtilsManager implements IModularUtilsManager {

	protected IEnergyHandler energyHandler;
	protected IFluidHandler fluidHandler;
	
	public ModularUtilsManager(IEnergyHandler energyHandler, IFluidHandler fluidHandler) {
		this.fluidHandler = fluidHandler;
		this.energyHandler = energyHandler;
	}
	
	@Override
	public IEnergyHandler getEnergyHandler() {
		return energyHandler;
	}
	
	@Override
	public IFluidHandler getFluidHandler() {
		return fluidHandler;
	}

	@Override
	public void setFluidHandler(IFluidHandler fluidHandler) {
		this.fluidHandler = fluidHandler;
	}

	@Override
	public void setEnergyHandler(IEnergyHandler energyHandler) {
		this.energyHandler = energyHandler;
	}
	
}
