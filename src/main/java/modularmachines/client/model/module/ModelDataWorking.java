package modularmachines.client.model.module;

import com.google.common.base.Function;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.common.modules.IModuleWorking;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDataWorking extends ModelData {

	public static final String ON = "on";
	public static final String OFF = "off";

	@Override
	public IBakedModel getModel(Module module, IStorage storage, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		if(module instanceof IModuleWorking){
			IModuleWorking working = (IModuleWorking) module;
			if (working.isWorking()) {
				return ModelLoader.getModel(get(ON), format);
			} else {
				return ModelLoader.getModel(get(OFF), format);
			}
		}
		return ModelLoaderRegistry.getMissingModel().bake(modelState, format, bakedTextureGetter);
	}
}
