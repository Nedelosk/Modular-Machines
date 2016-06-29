package de.nedelosk.modularmachines.client.modules;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.MultipartBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
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
	public IBakedModel getModel(IModuleState<IModuleCasing> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		IBakedModel moduleCasing = ModelLoaderRegistry.getModelOrMissing(casingLocation).bake(modelState, format, bakedTextureGetter);
		IBakedModel moduleRightStorage = ModelLoaderRegistry.getModelOrMissing(rightStorage).bake(modelState, format, bakedTextureGetter);
		IBakedModel moduleLeftStorage = ModelLoaderRegistry.getModelOrMissing(leftStorage).bake(modelState, format, bakedTextureGetter);
		MultipartBakedModel.Builder builder = new MultipartBakedModel.Builder();
		
		builder.putModel(TRUE, moduleCasing);
		builder.putModel(TRUE, moduleLeftStorage);
		builder.putModel(TRUE, moduleRightStorage);
		
		return builder.makeMultipartModel();
	}

	@Override
	public Predicate<IBlockState> getPredicate(IModuleState<IModuleCasing> state) {
		return TRUE;
	}
}
