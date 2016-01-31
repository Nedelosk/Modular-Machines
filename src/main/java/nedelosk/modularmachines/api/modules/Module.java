package nedelosk.modularmachines.api.modules;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.special.IModuleController;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class Module<S extends IModuleSaver> implements IModule<S> {

	protected ResourceLocation registry;
	private String moduleUID;
	private String categoryUID;

	public Module(String categoryUID, String moduleUID) {
		this.categoryUID = categoryUID;
		this.moduleUID = moduleUID;
	}

	@Override
	public String getName(ModuleStack stack) {
		return getCategoryUID() + "." + getModuleUID() + "." + stack.getMaterial().getName();
	}

	@Override
	public String getUnlocalizedName(ModuleStack stack) {
		return "module." + getCategoryUID() + "." + getModuleUID() + ".name";
	}

	@Override
	public S createSaver(ModuleStack stack) {
		return null;
	}

	@Override
	public List<String> getRequiredModules() {
		return new ArrayList<String>();
	}

	@Override
	public boolean canAssembleModular(IModular modular, ModuleStack stackModule, ModuleStack<IModuleController<IModuleSaver>, IModuleSaver> controller,
			List<ModuleStack> modules) throws ModularException {
		if (getRequiredModules().isEmpty()) {
			return true;
		}
		ArrayList<String> requiredModules = new ArrayList<>();
		requiredModules.addAll(getRequiredModules());
		for ( ModuleStack stack : modules ) {
			if (requiredModules.contains(stack.getModule().getName(stack))) {
				requiredModules.remove(stack.getModule().getName(stack));
			} else {
				throw new ModularException(StatCollector.translateToLocalFormatted("modular.ex.find.module", stack.getModule().getName(stack)));
			}
		}
		if (!requiredModules.isEmpty()) {
			throw new ModularException(StatCollector.translateToLocalFormatted("modular.ex.find.modules", requiredModules));
		}
		return true;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
	}

	@Override
	public void updateClient(IModular modular, ModuleStack stack) {
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
		return moduleUID;
	}

	@Override
	public String getCategoryUID() {
		return categoryUID;
	}
}