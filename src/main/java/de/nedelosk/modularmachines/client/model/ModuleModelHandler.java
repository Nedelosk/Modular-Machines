package de.nedelosk.modularmachines.client.model;

import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.client.model.IModel;

public class ModuleModelHandler implements IModuleModelHandler{

	public IBakedModel model;
	
	public ModuleModelHandler(IBakedModel model) {
		this.model = model;
	}
	
	@Override
	public IBakedModel getModel(IModuleState state) {
		return model;
	}

}
