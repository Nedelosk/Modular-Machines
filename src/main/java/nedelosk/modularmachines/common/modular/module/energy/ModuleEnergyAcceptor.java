package nedelosk.modularmachines.common.modular.module.energy;

import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.energy.IModuleEnergyAcceptor;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEnergyAcceptor extends Module implements IModuleEnergyAcceptor {

	public int energyIn;
	public int energyOut;
	
	public ModuleEnergyAcceptor(String modifier, int energyIn, int energyOut, String[][] entryKey) {
		super(modifier);
		this.energyIn = energyIn;
		this.energyOut = energyOut;
		this.entryKey = entryKey;
	}
	
	public ModuleEnergyAcceptor() {
		super();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("EnergyIn", energyIn);
		nbt.setInteger("EnergyOut", energyOut);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.energyIn = nbt.getInteger("EnergyIn");
		this.energyOut = nbt.getInteger("EnergyOut");
		
	}
	
	@Override
	public String[] getTechTreeKeys(int tier) {
		return entryKey[tier];
	}

	@Override
	public String getModuleName() {
		return "EnergyAcceptor";
	}

}
