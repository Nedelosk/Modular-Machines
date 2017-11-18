package modularmachines.common.modules.storages;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModulePosition;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.Module;
import modularmachines.common.modules.ModuleHandler;

public abstract class ModuleContainer extends Module implements IModuleProvider {
	
	public ModuleHandler moduleHandler;
	
	public ModuleContainer(IModulePosition... positions) {
		moduleHandler = new ModuleHandler(this, positions);
	}
	
	@Override
	public IModuleHandler getHandler() {
		return moduleHandler;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		moduleHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		moduleHandler.readFromNBT(compound);
	}
	
	@Override
	public boolean isModelNeedReload() {
		return super.isModelNeedReload() || moduleHandler.getModules().stream().allMatch(Module::isModelNeedReload);
	}
}
