package nedelosk.modularmachines.common.modular.module;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.api.modular.module.IModuleTank;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class ModuleTank extends Module implements IModuleTank{

	public int capacity;
	
	public ModuleTank() {
	}
	
	public ModuleTank(int capacity) {
		this.capacity = capacity;
	}
	
	public ModuleTank(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("Capacity", capacity);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.capacity = nbt.getInteger("Capacity");
	}

	@Override
	public String getModuleName() {
		return "Tank";
	}

	@Override
	public int getCapacity() {
		return capacity;
	}
}
