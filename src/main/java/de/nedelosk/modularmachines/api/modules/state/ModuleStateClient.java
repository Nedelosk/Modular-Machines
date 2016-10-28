package de.nedelosk.modularmachines.api.modules.state;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleStateClient<M extends IModule> extends ModuleState<M> implements IModuleStateClient<M> {

	@SideOnly(Side.CLIENT)
	protected IModelHandler modelHandler;

	public ModuleStateClient(IModuleProvider provider, IModuleContainer container) {
		super(provider, container);
	}

	@Override
	public IModuleState<M> build() {
		super.build();
		modelHandler = getModule().createModelHandler(this);
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler getModelHandler() {
		return modelHandler;
	}
}
