package modularmachines.api.modules.model;

import com.google.common.base.Function;

import java.util.Collection;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.model.IModelState;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.storages.IStorage;

@SideOnly(Side.CLIENT)
public interface IModelData<M> {

	default M getModel(Module module, IStorage storage, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter){
		return null;
	}
	
	default M getModel(Module module, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter){
		return null;
	}
	
	Collection<ResourceLocation> getValidLocations();
}
