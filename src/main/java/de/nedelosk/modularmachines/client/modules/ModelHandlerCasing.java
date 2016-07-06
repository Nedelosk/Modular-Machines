package de.nedelosk.modularmachines.client.modules;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.client.model.ModelModularMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class ModelHandlerCasing implements IModuleModelHandler<IModuleCasing> {

	private final ResourceLocation casingLocation;
	private final ResourceLocation rightStorage;
	private final ResourceLocation leftStorage;
	private final ResourceLocation smallWall;
	private final ResourceLocation middleWall;
	private final ResourceLocation largeWall;

	public ModelHandlerCasing(ResourceLocation casingLocation, ResourceLocation rightStorage, ResourceLocation leftStorage, ResourceLocation smallWall, ResourceLocation middleWall, ResourceLocation largeWall) {
		this.casingLocation = casingLocation;
		this.rightStorage = rightStorage;
		this.leftStorage = leftStorage;
		this.smallWall = smallWall;
		this.middleWall = middleWall;
		this.largeWall = largeWall;
	}

	@Override
	public void initModels() {
		ModelLoaderRegistry.getModelOrMissing(casingLocation);
		ModelLoaderRegistry.getModelOrMissing(rightStorage);
		ModelLoaderRegistry.getModelOrMissing(leftStorage);
		ModelLoaderRegistry.getModelOrMissing(smallWall);
		ModelLoaderRegistry.getModelOrMissing(middleWall);
		ModelLoaderRegistry.getModelOrMissing(largeWall);
	}

	@Override
	public IBakedModel getModel(IModuleState<IModuleCasing> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, List<IModuleModelHandler> otherHandlers) {
		IBakedModel moduleCasing = ModelLoaderRegistry.getModelOrMissing(casingLocation).bake(modelState, format, bakedTextureGetter);
		IBakedModel moduleRightStorage = ModelLoaderRegistry.getModelOrMissing(rightStorage).bake(modelState, format, bakedTextureGetter);
		IBakedModel moduleLeftStorage = ModelLoaderRegistry.getModelOrMissing(leftStorage).bake(modelState, format, bakedTextureGetter);
		Map<Predicate<IBlockState>, IBakedModel> models = new LinkedHashMap<>();

		models.put(IModuleModelHandler.createTrue(), moduleCasing);
		models.put(IModuleModelHandler.createTrue(), moduleLeftStorage);
		models.put(IModuleModelHandler.createTrue(), moduleRightStorage);

		int toolSize = 0;
		IModuleState<IModuleTool> lastToolState = null;
		for(IModuleState<IModuleTool> toolState : state.getModular().getModules(IModuleTool.class)){
			if(toolState != null){
				toolSize+=toolState.getModule().getSize();
				lastToolState = toolState;
			}
		}
		IBakedModel wallModel = null;
		if(toolSize == 1){
			wallModel = ModelLoaderRegistry.getModelOrMissing(middleWall).bake(modelState, format, bakedTextureGetter);	
		}
		else if(toolSize == 2){
			wallModel = ModelLoaderRegistry.getModelOrMissing(smallWall).bake(modelState, format, bakedTextureGetter);
		}else if(toolSize == 3){
			if(lastToolState != null && lastToolState.getModule().renderWall()){
				wallModel = ModelLoaderRegistry.getModelOrMissing(largeWall).bake(modelState, format, bakedTextureGetter);
			}
		}
		if(wallModel != null){
			models.put(IModuleModelHandler.createTrue(), wallModel);
		}

		return new ModelModularMachine.ModelModularMachineBaked(models);
	}

	protected IBakedModel getWallModel(int size){
		return null;
	}
}
