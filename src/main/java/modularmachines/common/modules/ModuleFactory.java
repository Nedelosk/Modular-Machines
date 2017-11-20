package modularmachines.common.modules;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModuleFactory;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.common.modules.data.ModuleData;
import modularmachines.common.modules.data.ModuleDataContainer;
import modularmachines.common.modules.data.ModuleDataContainerCapability;
import modularmachines.common.modules.data.ModuleDataContainerDamage;
import modularmachines.common.modules.data.ModuleDataContainerNBT;

public enum ModuleFactory implements IModuleFactory {
	INSTANCE;
	
	@Override
	public IModuleData createData() {
		return new ModuleData();
	}
	
	@Override
	public IModuleDataContainer createContainer(ItemStack parent, IModuleData data) {
		return new ModuleDataContainer(parent, data);
	}
	
	@Override
	public IModuleDataContainer createCapabilityContainer(ItemStack parent, IModuleData data) {
		return new ModuleDataContainerCapability(parent, data);
	}
	
	@Override
	public IModuleDataContainer createNBTContainer(ItemStack parent, IModuleData data) {
		return new ModuleDataContainerNBT(parent, data);
	}
	
	@Override
	public IModuleDataContainer createDamageContainer(ItemStack parent, IModuleData data) {
		return new ModuleDataContainerDamage(parent, data);
	}
}
