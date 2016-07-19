package de.nedelosk.modularmachines.client.modules;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import net.minecraft.client.renderer.block.model.IBakedModel;

public abstract class ModelHandler<M extends IModule> implements IModelHandler<M> {

	public IBakedModel bakedModel;
	public boolean needReload;

	@Override
	public void setNeedReload(boolean needReload) {
		this.needReload = needReload;
	}

	@Override
	public boolean needReload() {
		return needReload;
	}

	@Override
	public IBakedModel getModel() {
		return bakedModel;
	}
}
