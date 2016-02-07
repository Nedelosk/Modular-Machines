package de.nedelosk.forestmods.common.modular.managers;

import cofh.api.energy.IEnergyHandler;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.managers.IModularUtilsManager;
import de.nedelosk.forestmods.common.modular.handlers.EnergyHandler;
import de.nedelosk.forestmods.common.modular.handlers.FluidHandler;
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
			energyHandler = new EnergyHandler(modular);
		}
		if (nbt.getBoolean("FH")) {
			fluidHandler = new FluidHandler(modular);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (energyHandler != null) {
			nbt.setBoolean("EH", true);
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

	@Override
	public IModular getModular() {
		return modular;
	}
}
