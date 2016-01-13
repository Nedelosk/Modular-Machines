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
		if (nbt.getBoolean("EH")) {
			energyHandler = new EnergyHandler(nbt);
		}
		if (nbt.getBoolean("FH")) {
			fluidHandler = new FluidHandler(modular);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (energyHandler != null) {
			nbt.setBoolean("EH", true);
			energyHandler.writeToNBT(nbt);
		} else {
			nbt.setBoolean("EH", false);
		}
		if (fluidHandler != null) {
			nbt.setBoolean("FH", true);
		} else {
			nbt.setBoolean("FH", false);
		}
	}

	@Override
	public void setModular(IModular modular) {
		this.modular = modular;
		if (fluidHandler != null) {
			fluidHandler.setMachine(modular);
		}
	}
}
