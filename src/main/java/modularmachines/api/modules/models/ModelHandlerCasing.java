package modularmachines.api.modules.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;

import modularmachines.api.modular.ExpandedStoragePositions;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.state.IModuleStateClient;
import modularmachines.api.modules.storage.IStorage;
import modularmachines.api.modules.storage.module.IModuleStorage;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHandlerCasing extends ModelHandler implements IModelHandler {

	private final ResourceLocation casing;
	private final ResourceLocation casing_left;
	private final ResourceLocation casing_right;

	public ModelHandlerCasing(ResourceLocation... locations) {
		casing = locations[0];
		casing_left = locations[1];
		casing_right = locations[2];
	}

	@Override
	public void reload(IModuleState state, IStorage storage, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		List<IBakedModel> models = new ArrayList<>();
		if (state.getModular() != null) {
			Set<IStoragePosition> positions = state.getModular().getStorages().keySet();
			if (!positions.contains(ExpandedStoragePositions.LEFT)) {
				models.add(ModuleModelLoader.getModel(casing_left, format));
			}
			if (!positions.contains(ExpandedStoragePositions.RIGHT)) {
				models.add(ModuleModelLoader.getModel(casing_right, format));
			}
		}
		models.add(ModuleModelLoader.getModel(casing, format));
		for(IModuleState moduleState : ((IModuleStorage) storage).getModules()) {
			IModule module = moduleState.getModule();
			if (((IModuleStateClient) moduleState).getModelHandler() != null) {
				models.add(ModuleModelLoader.getModel(moduleState, storage, modelState, format));
			}
		}
		bakedModel = new BakedMultiModel(models);
	}
}
