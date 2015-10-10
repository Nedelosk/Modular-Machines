package nedelosk.modularmachines.common.modular.machines.modular.managers;

import cofh.api.energy.IEnergyHandler;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.manager.IModularUtilsManager;
import nedelosk.modularmachines.common.modular.machines.modular.handlers.EnergyHandler;
import nedelosk.modularmachines.common.modular.machines.modular.handlers.FluidHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidHandler;

public class ModularUtilsManager implements IModularUtilsManager {

	protected EnergyHandler energyHandler;
	protected FluidHandler fluidHandler;
	protected IModularInventory modular;
	
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
		if(nbt.hasKey("FluidHandler"))
			fluidHandler = new FluidHandler(nbt, modular);
		if(nbt.hasKey("EnergyManager"))
			energyHandler = new EnergyHandler(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if(fluidHandler != null)
			fluidHandler.writeToNBT(nbt);
		if(energyHandler != null)
			energyHandler.writeToNBT(nbt);
	}
	
	public void setModular(IModularInventory modular) {
		this.modular = modular;
	}
	
}