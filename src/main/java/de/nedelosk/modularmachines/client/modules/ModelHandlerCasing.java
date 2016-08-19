package de.nedelosk.modularmachines.client.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.client.model.ModelModular;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class ModelHandlerCasing extends ModelHandler implements IModelHandler, IModelInitHandler {

	private final ResourceLocation casing;
	private final ResourceLocation casing_left;
	private final ResourceLocation casing_right;

	public ModelHandlerCasing(IModuleContainer container) {
		super("casings", container);
		casing = getModelLocation("casing");
		casing_left = getModelLocation("side_left");
		casing_right= getModelLocation("side_right");
	}

	@Override
	public void initModels(IModuleContainer container) {
		getModelOrDefault(casing);
		getModelOrDefault(casing_left);
		getModelOrDefault(casing_right);
	}

	@Override
	public void reload(IModuleState state, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		List<IBakedModel> models = new ArrayList<>();
		if(state.getModular() != null){
			List<EnumStoragePosition> positions = new ArrayList<>();
			for(IModuleState<IModuleModuleStorage> storage : state.getModular().getModules(IModuleModuleStorage.class)){
				positions.add(storage.getModule().getCurrentPosition(storage));
			}
			if(!positions.contains(EnumStoragePosition.LEFT)){
				models.add(getBakedModel(casing_left, modelState, format, bakedTextureGetter));
			}
			if(!positions.contains(EnumStoragePosition.RIGHT)){
				models.add(getBakedModel(casing_right, modelState, format, bakedTextureGetter));
			}
		}
		models.add(getBakedModel(casing, modelState, format, bakedTextureGetter));
		bakedModel = new ModelModular.ModularBaked(models);
	}
}
