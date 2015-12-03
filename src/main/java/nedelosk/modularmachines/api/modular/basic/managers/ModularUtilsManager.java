package nedelosk.modularmachines.api.modular.basic.managers;

import cofh.api.energy.IEnergyHandler;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.handlers.EnergyHandler;
import nedelosk.modularmachines.api.modular.handlers.FluidHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidHandler;

public class ModularUtilsManager implements IModularUtilsManager {

	protected EnergyHandler energyHandler;
	protected FluidHandler fluidHandler;
	protected IModular modular;

	public ModularUtilsManager() {
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
		this.fluidHandler = (FluidHandler) fluidHandler;
	}

	@Override
	public void setEnergyHandler(IEnergyHandler energyHandler) {
		this.energyHandler = (EnergyHandler) energyHandler;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("FluidHandler"))
			fluidHandler = new FluidHandler(nbt);
		if (nbt.hasKey("EnergyManager"))
			energyHandler = new EnergyHandler(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (fluidHandler != null)
			fluidHandler.writeToNBT(nbt);
		if (energyHandler != null)
			energyHandler.writeToNBT(nbt);
	}

	@Override
	public void setModular(IModular modular) {
		this.modular = modular;
		if (fluidHandler != null)
			fluidHandler.setMachine(modular);
	}

}
