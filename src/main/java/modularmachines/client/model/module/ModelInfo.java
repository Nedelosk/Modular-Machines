package modularmachines.client.model.module;

import javax.annotation.Nullable;
import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.block.IBoundingBoxComponent;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.client.model.AABBModelBaker;

public class ModelInfo implements IModelInfo {
	private final VertexFormat format;
	private final IModelState modelState;
	private final Function<ResourceLocation, TextureAtlasSprite> textureGetter;
	private final BlockRenderLayer layer;
	
	public ModelInfo(VertexFormat format, IModelState modelState, Function<ResourceLocation, TextureAtlasSprite> textureGetter, BlockRenderLayer layer) {
		this.format = format;
		this.modelState = modelState;
		this.textureGetter = textureGetter;
		this.layer = layer;
	}
	
	@Nullable
	@Override
	public IBakedModel getModel(@Nullable IModule module) {
		if (module == null) {
			return null;
		}
		return ModuleModelLoader.getModel(module, this);
	}
	
	@Nullable
	@Override
	public IBakedModel getModel(@Nullable ResourceLocation location) {
		if (location == null) {
			return null;
		}
		return ModuleModelLoader.getModel(location, format);
	}
	
	@Override
	public VertexFormat getFormat() {
		return format;
	}
	
	@Override
	public IModelState getState() {
		return modelState;
	}
	
	@Override
	public BlockRenderLayer getLayer() {
		return layer;
	}
	
	@Override
	public Function<ResourceLocation, TextureAtlasSprite> getTextureGetter() {
		return textureGetter;
	}
	
	@Override
	public IBakedModel bakeMissingModel(IModule module) {
		IBoundingBoxComponent component = module.getComponent(IBoundingBoxComponent.class);
		if (component != null) {
			AxisAlignedBB boundingBox = component.getBoundingBox();
			AABBModelBaker modelBaker = new AABBModelBaker();
			modelBaker.setModelBounds(boundingBox);
			modelBaker.addModel(Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite(), 0);
			return modelBaker.bakeModel(true);
		}
		return ModelLoaderRegistry.getMissingModel().bake(modelState, format, textureGetter);
	}
}
