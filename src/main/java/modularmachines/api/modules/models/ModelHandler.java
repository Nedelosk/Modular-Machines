package modularmachines.api.modules.models;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;

@SideOnly(Side.CLIENT)
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
