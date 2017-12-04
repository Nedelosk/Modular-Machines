package modularmachines.common.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleDataBuilder;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleFactory;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleEvents;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IModuleComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.ModularMachines;
import modularmachines.common.modules.container.ModuleContainer;
import modularmachines.common.modules.data.ModuleData;

public enum ModuleFactory implements IModuleFactory {
	INSTANCE;
	
	@Override
	public IModuleContainer createContainer(ILocatable locatable) {
		IModuleContainer container = new ModuleContainer(locatable);
		MinecraftForge.EVENT_BUS.post(new ModuleEvents.ContainerCreationEvent(container));
		return container;
	}
	
	@Override
	public IModuleDataBuilder createData() {
		return new ModuleData.Builder();
	}
	
	@Override
	public IModule createModule(IModuleHandler parent, IModulePosition position, IModuleType container, ItemStack parentItem) {
		IModule module = new Module(parent, position, container.getData(), parentItem);
		createModule(module);
		return module;
	}
	
	@Override
	public IModule createEmptyModule(IModuleHandler parent, IModulePosition position) {
		IModule module = new Module(parent, position, ModuleManager.registry.getEmpty(), ItemStack.EMPTY);
		createModule(module);
		return module;
	}
	
	@Override
	public IModule createModule(NBTTagCompound compound, IModuleHandler parent, IModuleData moduleData, IModulePosition position) {
		IModule module = new Module(parent, moduleData, position, compound);
		createModule(module);
		module.readFromNBT(compound);
		return module;
	}
	
	private void createModule(IModule module) {
		IModuleDefinition definition = module.getData().getDefinition();
		definition.addComponents(module, ModuleManager.componentFactory);
		//Add the model component on the client side
		ModularMachines.proxy.addComponents(module);
		for (IModuleComponent component : module.getComponents()) {
			component.onCreateModule();
		}
		MinecraftForge.EVENT_BUS.post(new ModuleEvents.CreationEvent(module));
	}
	
}
