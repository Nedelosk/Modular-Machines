package de.nedelosk.forestmods.api.modular.managers;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularUtilsManager extends IModularManager {

	IFluidHandler getFluidHandler();

	<E extends IEnergyProvider & IEnergyReceiver> E getEnergyHandler();

	void setFluidHandler(IFluidHandler fluidHandler);

	<E extends IEnergyProvider & IEnergyReceiver> void setEnergyHandler(E energyHandler);
}
