package de.nedelosk.modularmachines.common.plugins.mekanism;

import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.ModuleContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleContainerEnergyCube extends ModuleContainer {

	private final int tier;

	public ModuleContainerEnergyCube(IModule module, IModuleProperties properties, IMaterial material, int tier) {
		super(module, properties, new ItemStack(PluginMekanism.energyCube), material, true);
		this.tier = tier;
	}

	@Override
	public boolean matches(ItemStack stackToTest) {
		boolean matches = super.matches(stackToTest);
		if(!matches){
			return false;
		}
		if(!stackToTest.hasTagCompound()){
			return false;
		}
		NBTTagCompound nbtTag = stackToTest.getTagCompound();
		if(!nbtTag.hasKey("tier")) {
			return false;
		}
		int nbtTier = nbtTag.getInteger("tier");
		if(nbtTier != tier){
			return false;
		}
		return true;
	}
}
