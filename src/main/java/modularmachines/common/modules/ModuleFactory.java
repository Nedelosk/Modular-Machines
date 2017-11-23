package modularmachines.common.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleFactory;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.ModularMachines;
import modularmachines.common.modules.container.ModuleContainer;
import modularmachines.common.modules.data.ModuleData;
import modularmachines.common.modules.data.ModuleDataContainer;
import modularmachines.common.modules.data.ModuleDataContainerCapability;
import modularmachines.common.modules.data.ModuleDataContainerDamage;
import modularmachines.common.modules.data.ModuleDataContainerNBT;

public enum ModuleFactory implements IModuleFactory {
	INSTANCE;
	
	@Override
	public IModuleContainer createContainer(ILocatable locatable) {
		return new ModuleContainer(locatable);
	}
	
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
	
	@Override
	public IModule createModule(IModuleHandler parent, IModulePosition position, IModuleDataContainer container, ItemStack parentItem) {
		IModule module = new Module(parent, position, container, parentItem);
		container.getData().getDefinition().addComponents(module);
		ModularMachines.proxy.addComponents(module);
		return module;
	}
	
	@Override
	public IModule createModule(NBTTagCompound compound, IModuleHandler parent, IModuleData moduleData, IModulePosition position) {
		IModule module = new Module(parent, moduleData, position);
		moduleData.getDefinition().addComponents(module);
		ModularMachines.proxy.addComponents(module);
		module.readFromNBT(compound);
		return module;
	}
	
	
}
