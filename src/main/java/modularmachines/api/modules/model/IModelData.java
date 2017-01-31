package modularmachines.api.modules.model;

import java.util.List;

import com.google.common.base.Function;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.storages.IStorage;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IModelData<M> {

	void reload(Module module, IStorage storage, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter);

	void setNeedReload(boolean needReload);

	boolean needReload();

	IBakedModel getModel();
	
	List<ResourceLocation> getValidLocations();
}
