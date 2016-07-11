package de.nedelosk.modularmachines.api.modules.models;

import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IModelHandler<M extends IModule> {

	void reload(IModuleState<M> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, List<IModelHandler> otherHandlers);

	void setNeedReload(boolean needReload);

	boolean needReload();

	IBakedModel getModel();
}
