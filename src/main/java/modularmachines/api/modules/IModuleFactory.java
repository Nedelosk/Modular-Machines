package modularmachines.api.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.positions.IModulePosition;

/**
 * A factory to create {@link IModuleType}s, {@link IModuleData}s and {@link IModule}s.
 */
public interface IModuleFactory {
	
	IModuleData createData();
	
	IModuleContainer createContainer(ILocatable locatable);
	
	IModule createEmptyModule(IModuleHandler parent, IModulePosition position);
	
	IModule createModule(IModuleHandler parent, IModulePosition position, IModuleType container, ItemStack parentItem);
	
	IModule createModule(NBTTagCompound compound, IModuleHandler parent, IModuleData moduleData, IModulePosition position);
}
