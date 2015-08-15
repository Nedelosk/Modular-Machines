package nedelosk.modularmachines.common.modular.module;

import nedelosk.modularmachines.api.basic.modular.module.IModuleTank;
import nedelosk.modularmachines.api.basic.modular.module.Module;
import net.minecraft.nbt.NBTTagCompound;

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
