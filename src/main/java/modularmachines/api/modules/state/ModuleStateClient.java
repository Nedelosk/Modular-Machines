package modularmachines.api.modules.state;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.models.IModelHandler;

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
