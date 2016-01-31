package nedelosk.modularmachines.api.modules;

import java.util.List;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.special.IModuleController;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModule {

	List<String> getRequiredModules();

	boolean canAssembleModular(IModular modular, ModuleStack stack, ModuleStack<IModuleController, IModuleSaver> controller, List<ModuleStack> modules)
			throws ModularException;

	String getUID();

	String getUnlocalizedName(ModuleStack stack);

	String getCategoryUID();

	String getModuleUID();

	IModuleSaver createSaver(ModuleStack stack);
}
