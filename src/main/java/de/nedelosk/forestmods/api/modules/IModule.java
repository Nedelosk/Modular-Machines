package de.nedelosk.forestmods.api.modules;

import java.util.List;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleStack;

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
