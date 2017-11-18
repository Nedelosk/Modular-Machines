package modularmachines.client.model.module;

import com.google.common.base.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.model.IModelState;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.model.ModelLocationBuilder;

@SideOnly(Side.CLIENT)
public class ModelDataDefault extends ModelData {

	public static final String DEFAULT = "default";

	public static void initModelData(ModelLocationBuilder location){
		ModelDataDefault model = new ModelDataDefault();
		model.addLocation(DEFAULT, location);
		location.getData().addModel(TileEntity.class, model);
	}
	
	@Override
	public IBakedModel getModel(Module module, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		return ModelLoader.getModel(get(DEFAULT), format);
	}
}
