package de.nedelosk.modularmachines.common.plugins.thermalexpansion;

public class ModuleEnergyCell /*extends ModuleBattery*/ {

	/*public ModuleEnergyCell(int complexity, EnergyStorage defaultStorage) {
		super("energycell", complexity, defaultStorage);
	}

	@Override
	public void setStorageEnergy(IModuleBattery module, int energy, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			itemStack.setTagCompound(new NBTTagCompound());
		}
		itemStack.getTagCompound().setInteger("Energy", energy);
	}

	@Override
	public int getStorageEnergy(IModuleBattery module, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			return 0;
		}
		return itemStack.getTagCompound().getInteger("Energy");
	}*/
}
