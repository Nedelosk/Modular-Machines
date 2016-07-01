package de.nedelosk.modularmachines.api.modules;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IModuleModelHandler<M extends IModule> {

	IBakedModel getModel(IModuleState<M> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter);

	/**
	 * To register the textures of the models and othe stuff.
	 */
	void initModels();

	public static Predicate createTrue(){
		return new Predicate<IBlockState>(){
			@Override
			public boolean apply(@Nullable IBlockState state){
				return true;
			}
		};
	}
}
