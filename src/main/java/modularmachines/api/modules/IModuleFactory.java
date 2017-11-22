package modularmachines.api.modules;

import net.minecraft.item.ItemStack;

import modularmachines.api.ILocatable;
import modularmachines.api.components.IComponentProvider;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;

public interface IModuleFactory {
	
	IModuleData createData();
	
	IModuleDataContainer createContainer(ItemStack parent, IModuleData data);
	
	IModuleDataContainer createCapabilityContainer(ItemStack parent, IModuleData data);
	
	IModuleDataContainer createNBTContainer(ItemStack parent, IModuleData data);
	
	IModuleDataContainer createDamageContainer(ItemStack parent, IModuleData data);
	
	IModuleContainer createContainer(ILocatable locatable);
	
	IComponentProvider createComponentProvider();
}
