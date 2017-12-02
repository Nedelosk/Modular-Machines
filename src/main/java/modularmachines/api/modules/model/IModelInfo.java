package modularmachines.api.modules.model;

import javax.annotation.Nullable;
import java.util.function.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.model.IModelState;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;

@SideOnly(Side.CLIENT)
public interface IModelInfo {
	@Nullable
	IBakedModel getModel(@Nullable IModule module);
	
	@Nullable
	IBakedModel getModel(@Nullable ResourceLocation location);
	
	@Nullable
	BlockRenderLayer getLayer();
	
	VertexFormat getFormat();
	
	IModelState getState();
	
	Function<ResourceLocation, TextureAtlasSprite> getTextureGetter();
	
	IBakedModel bakeMissingModel(IModule module);
}
