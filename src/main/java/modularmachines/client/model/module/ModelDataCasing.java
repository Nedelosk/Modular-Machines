package modularmachines.client.model.module;

import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Function;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStorage;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDataCasing extends ModelData {

	public static final String CASING = "casing";
	public static final String CASING_LEFT = "casingLeft";
	public static final String CASING_RIGHT = "casingRight";

	@Override
	public IBakedModel getModel(Module module, IStorage storage, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		List<IBakedModel> models = new ArrayList<>();
		IModuleLogic logic = module.getLogic();
		if (logic.getStorage(EnumStoragePosition.LEFT)	== null) {
			models.add(ModelLoader.getModel(get(CASING_LEFT), format));
		}
		if (logic.getStorage(EnumStoragePosition.RIGHT)	== null) {
			models.add(ModelLoader.getModel(get(CASING_RIGHT), format));
		}
		models.add(ModelLoader.getModel(get(CASING), format));
		for (Module otherModule : storage.getStorage().getModules()) {
			models.add(ModelLoader.getModel(otherModule, storage, modelState, format));
		}
		return new BakedMultiModel(models);
	}
}
