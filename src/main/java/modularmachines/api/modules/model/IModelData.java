package modularmachines.api.modules.model;

import java.util.function.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.model.IModelState;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.client.model.EmptyModelState;

@SideOnly(Side.CLIENT)
public interface IModelData {
	default IBakedModel getModel(Module module, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return null;
	}
	
	default void addModel(IModelList modelList, Module module, IModuleModelState modelState) {
	}
	
	default IModuleModelState createState(Module module) {
		return EmptyModelState.INSTANCE;
	}
	
	IModelLocations locations();
}
