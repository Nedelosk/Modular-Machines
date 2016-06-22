package de.nedelosk.modularmachines.common.modules.engine;

import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.engine.EnumEnigneSize;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.client.renderer.block.model.IBakedModel;

public class ModelEngineHandler implements IModuleModelHandler<IModuleEngine> {

	public IBakedModel model;
	
	@Override
	public IBakedModel getModel(IModuleState<IModuleEngine> state) {
		if(model == null){
			IModuleEngine engine = state.getModule();
			EnumEnigneSize size = engine.getSize();
		}
		return model;
	}

}
