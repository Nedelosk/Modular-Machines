package de.nedelosk.modularmachines.common.plugins.ic2;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modules.storaged.storage.ModuleBattery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEUBattery extends ModuleBattery {

	public ModuleEUBattery() {
		super("euBattery");
	}

	@Override
	protected boolean showName(IModuleContainer container) {
		return false;
	}

	@Override
	protected boolean showMaterial(IModuleContainer container) {
		return false;
	}

	@Override
	public void setStorageEnergy(IModuleState state, long energy, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			itemStack.setTagCompound(new NBTTagCompound());
		}
		itemStack.getTagCompound().setDouble("energy", energy / 2);
	}

	@Override
	public long getStorageEnergy(IModuleState state, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			return 0;
		}
		NBTTagCompound nbtTag = itemStack.getTagCompound();
		return (long) nbtTag.getDouble("energy") * 2;
	}
}
