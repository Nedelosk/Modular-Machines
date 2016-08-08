package de.nedelosk.modularmachines.api.modules.state;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleStateClient<M extends IModule> extends ModuleState<M> implements IModuleStateClient<M>{

	@SideOnly(Side.CLIENT)
	protected IModelHandler modelHandler;

	public ModuleStateClient(IModular modular, IModuleContainer container) {
		super(modular, container);
	}

	@Override
	public IModuleState<M> build() {
		super.build();
		modelHandler = container.getModule().createModelHandler(this);	
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler getModelHandler() {
		return modelHandler;
	}
}
