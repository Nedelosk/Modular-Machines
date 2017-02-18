package modularmachines.client.model.module;

import com.google.common.base.Function;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.storages.IStorage;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDataDefault extends ModelData {

	public static final String DEFAULT = "default";

	@Override
	public IBakedModel getModel(Module state, IStorage storage, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		return ModelLoader.getModel(get(DEFAULT), format);
	}
}
