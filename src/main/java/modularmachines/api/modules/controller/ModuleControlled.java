package modularmachines.api.modules.controller;

import java.util.Collections;
import java.util.List;

import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleControlled extends Module implements IModuleControlled {

	public ModuleControlled(String name) {
		super(name);
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createHandlers(state);
		handlers.add(new ModuleControl(state));
		return handlers;
	}

	@Override
	public IModuleControl getModuleControl(IModuleState state) {
		return state.getContentHandler(IModuleControl.class);
	}

	@Override
	public void onModularAssembled(IModuleState state) {
		super.onModularAssembled(state);
		if (state.getModular().getModule(IModuleController.class) != null) {
			state.addPage(getControllerPage(state));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<IModuleState> getUsedModules(IModuleState state) {
		return Collections.emptyList();
	}

	protected abstract IModulePage getControllerPage(IModuleState state);
}
