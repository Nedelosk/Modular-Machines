package de.nedelosk.modularmachines.api.modules.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.EnumStoragePositions;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleStorage;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	public void reload(IModuleState state, IStorage storage, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		List<IBakedModel> models = new ArrayList<>();
		if(state.getModular() != null){
			Set<IStoragePosition> positions = state.getModular().getStorages().keySet();
			if(!positions.contains(EnumStoragePositions.LEFT)){
				models.add(getBakedModel(casing_left, modelState, format, bakedTextureGetter));
			}
			if(!positions.contains(EnumStoragePositions.RIGHT)){
				models.add(getBakedModel(casing_right, modelState, format, bakedTextureGetter));
			}
		}
		models.add(getBakedModel(casing, modelState, format, bakedTextureGetter));
		for(IModuleState moduleState : ((IModuleStorage)storage).getModules()){
			IModule module = moduleState.getModule();
			if(((IModuleStateClient)moduleState).getModelHandler() != null){
				models.add(ModuleModelHelper.getModel(moduleState, storage, modelState, format));
			}
		}
		bakedModel = new BakedMultiModel(models);
	}
}
