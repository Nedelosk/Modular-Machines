package modularmachines.common.modules.components;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.modules.ModuleHandler;

public abstract class ModuleProviderComponent extends ModuleComponent implements IModuleProvider, INBTWritable, INBTReadable {
	
	protected ModuleHandler moduleHandler;
	protected final IModulePosition[] positions;
	
	public ModuleProviderComponent(IModulePosition... positions) {
		this.positions = positions;
	}
	
	@Override
	public void onCreateModule() {
		moduleHandler = new ModuleHandler(this, positions);
	}
	
	@Override
	public IModuleHandler getHandler() {
		return moduleHandler;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		moduleHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		moduleHandler.readFromNBT(compound);
	}
	
	@Override
	public IModuleContainer getContainer() {
		return provider.getContainer();
	}
	
	@Override
	public Collection<IModule> getModules() {
		Set<IModule> modules = new HashSet<>();
		moduleHandler.getModules().forEach(m -> addToList(modules, m));
		return modules;
	}
	
	private void addToList(Set<IModule> modules, IModule module) {
		modules.add(module);
		IModuleProvider moduleProvider = module.getInterface(IModuleProvider.class);
		if (moduleProvider == null) {
			return;
		}
		IModuleHandler moduleHandler = moduleProvider.getHandler();
		moduleHandler.getModules().forEach(m -> addToList(modules, m));
	}
}
