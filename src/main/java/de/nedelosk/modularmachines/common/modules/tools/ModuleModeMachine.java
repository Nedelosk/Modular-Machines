package de.nedelosk.modularmachines.common.modules.tools;

import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleModeMachine;
import de.nedelosk.modularmachines.api.modules.tools.ModuleMachine;
import de.nedelosk.modularmachines.api.property.PropertyToolMode;
import de.nedelosk.modularmachines.api.recipes.IToolMode;
import de.nedelosk.modularmachines.common.modules.pages.ControllerPage;
import de.nedelosk.modularmachines.common.modules.pages.MainPage;

public abstract class ModuleModeMachine extends ModuleMachine implements IModuleModeMachine {

	public final PropertyToolMode MODE;

	public ModuleModeMachine(String name, IToolMode defaultMode) {
		super(name);
		MODE = new PropertyToolMode("mode", getModeClass(), defaultMode);
	}

	@Override
	public IModuleState createState(IModuleProvider provider, IModuleContainer container) {
		return super.createState(provider, container).register(MODE);
	}

	protected abstract Class<? extends IToolMode> getModeClass();

	@Override
	public IToolMode getNextMode(IModuleState state) {
		IToolMode[] modes = getModeClass().getEnumConstants();
		IToolMode mode = getCurrentMode(state);
		if (mode.ordinal() == modes.length - 1) {
			return getMode(0);
		}
		return getMode(mode.ordinal() + 1);
	}

	@Override
	public IToolMode getMode(int index) {
		IToolMode[] modes = getModeClass().getEnumConstants();
		if (modes.length <= index) {
			return null;
		}
		return modes[index];
	}

	@Override
	public IToolMode getCurrentMode(IModuleState state) {
		return state.get(MODE);
	}

	@Override
	public void setCurrentMode(IModuleState state, IToolMode mode) {
		state.set(MODE, mode);
	}

	@Override
	protected Class<? extends IModulePage> getMainPageClass() {
		return MainPage.class;
	}

	@Override
	protected IModulePage getControllerPage(IModuleState state) {
		return new ControllerPage(state);
	}
}
