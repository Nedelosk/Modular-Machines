package modularmachines.api.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.IModulePosition;

/**
 * A factory to create {@link IModuleDataContainer}s, {@link IModuleData}s and {@link IModule}s.
 */
public interface IModuleFactory {
	
	IModuleData createData();
	
	IModuleDataContainer createContainer(ItemStack parent, IModuleData data);
	
	IModuleDataContainer createCapabilityContainer(ItemStack parent, IModuleData data);
	
	IModuleDataContainer createNBTContainer(ItemStack parent, IModuleData data);
	
	IModuleDataContainer createDamageContainer(ItemStack parent, IModuleData data);
	
	IModuleContainer createContainer(ILocatable locatable);
	
	IModule createModule(IModuleHandler parent, IModulePosition position, IModuleDataContainer container, ItemStack parentItem);
	
	IModule createModule(NBTTagCompound compound, IModuleHandler parent, IModuleData moduleData, IModulePosition position);
}
