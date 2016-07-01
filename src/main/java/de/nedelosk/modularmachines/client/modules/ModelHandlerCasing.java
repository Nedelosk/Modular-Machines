package de.nedelosk.modularmachines.client.modules;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
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

	public ModelHandlerCasing(ResourceLocation casingLocation, ResourceLocation rightStorage, ResourceLocation leftStorage) {
		this.casingLocation = casingLocation;
		this.rightStorage = rightStorage;
		this.leftStorage = leftStorage;
	}

	@Override
	public void initModels() {
		ModelLoaderRegistry.getModelOrMissing(casingLocation);
		ModelLoaderRegistry.getModelOrMissing(rightStorage);
		ModelLoaderRegistry.getModelOrMissing(leftStorage);
	}

	@Override
	public IBakedModel getModel(IModuleState<IModuleCasing> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		IBakedModel moduleCasing = ModelLoaderRegistry.getModelOrMissing(casingLocation).bake(modelState, format, bakedTextureGetter);
		IBakedModel moduleRightStorage = ModelLoaderRegistry.getModelOrMissing(rightStorage).bake(modelState, format, bakedTextureGetter);
		IBakedModel moduleLeftStorage = ModelLoaderRegistry.getModelOrMissing(leftStorage).bake(modelState, format, bakedTextureGetter);
		Map<Predicate<IBlockState>, IBakedModel> models = new LinkedHashMap<>();

		models.put(IModuleModelHandler.createTrue(), moduleCasing);
		models.put(IModuleModelHandler.createTrue(), moduleLeftStorage);
		models.put(IModuleModelHandler.createTrue(), moduleRightStorage);

		return new ModelModularMachine.ModelModularMachineBaked(models);
	}
}
