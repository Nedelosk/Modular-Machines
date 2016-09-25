package de.nedelosk.modularmachines.common.modules.pages;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public class MainPage<M extends IModule> extends ModulePage<M>{

	public MainPage(String title, IModuleState<M> module) {
		super("MainPage", title, module);
	}
}
