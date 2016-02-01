package nedelosk.modularmachines.api.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.special.IModuleController;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.util.StatCollector;

public abstract class Module implements IModule {

	private String moduleUID;
	private String categoryUID;

	public Module(String categoryUID, String moduleUID) {
		this.categoryUID = categoryUID;
		this.moduleUID = moduleUID;
	}

	@Override
	public String getUID() {
		return getCategoryUID() + ":" + getModuleUID();
	}

	@Override
	public String getUnlocalizedName(ModuleStack stack) {
		return "module." + getCategoryUID().toLowerCase(Locale.ENGLISH) + "." + getModuleUID().toLowerCase(Locale.ENGLISH) + "."
				+ stack.getMaterial().getName().toLowerCase(Locale.ENGLISH) + ".name";
	}

	@Override
	public IModuleSaver createSaver(ModuleStack stack) {
		return null;
	}

	@Override
	public List<String> getRequiredModules() {
		return new ArrayList<String>();
	}

	@Override
	public boolean canAssembleModular(IModular modular, ModuleStack stackModule, ModuleStack<IModuleController, IModuleSaver> controller,
			List<ModuleStack> modules) throws ModularException {
		if (getRequiredModules().isEmpty()) {
			return true;
		}
		ArrayList<String> requiredModules = new ArrayList<>();
		requiredModules.addAll(getRequiredModules());
		for ( ModuleStack stack : modules ) {
			if (requiredModules.contains(stack.getModule().getUID())) {
				requiredModules.remove(stack.getModule().getUID());
			} else {
				throw new ModularException(StatCollector.translateToLocalFormatted("modular.ex.find.module", stack.getModule().getUID()));
			}
		}
		if (!requiredModules.isEmpty()) {
			throw new ModularException(StatCollector.translateToLocalFormatted("modular.ex.find.modules", requiredModules));
		}
		return true;
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