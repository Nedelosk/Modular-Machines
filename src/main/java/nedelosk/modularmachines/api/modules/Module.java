package nedelosk.modularmachines.api.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.util.ResourceLocation;

public abstract class Module<S extends IModuleSaver> implements IModule<S> {

	public String modifier;
	protected ResourceLocation registry;

	public Module(String modifier) {
		this.modifier = modifier;
	}

	@Override
	public String getName(ModuleStack stack) {
		return registry.getResourcePath() + "." + modifier;
	}

	@Override
	public String getUnlocalizedName(ModuleStack stack) {
		return "producer." + getName(stack) + ".name";
	}

	@Override
	public String getModifier(ModuleStack stack) {
		return modifier;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui getGui(ModuleStack stack) {
		return null;
	}

	@Override
	public S getSaver(ModuleStack stack) {
		return null;
	}

	@Override
	public IModuleInventory getInventory(ModuleStack stack) {
		return null;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
	}

	@Override
	public void updateClient(IModular modular, ModuleStack stack) {
	}

	@Override
	public List<String> getRequiredModules() {
		return new ArrayList<String>();
	}

	@Override
	public boolean onBuildModular(IModular modular, ModuleStack stack, List<String> moduleNames) {
		ArrayList<String> requiredModules = new ArrayList<>();
		requiredModules.addAll(getRequiredModules());
		for ( String moduleName : getRequiredModules() ) {
			if (moduleNames.contains(moduleName)) {
				requiredModules.remove(moduleName);
			} else {
				return false;
			}
		}
		if (!requiredModules.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public ResourceLocation getRegistry() {
		return registry;
	}

	@Override
	public void setRegistry(ResourceLocation registry) {
		this.registry = registry;
	}

	@Override
	public String getModuleUID() {
		return null;
	}
}
