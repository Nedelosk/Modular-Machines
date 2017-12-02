package modularmachines.api.modules.model;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IModelList {
	void add(@Nullable ResourceLocation location);
	
	void add(@Nullable ResourceLocation location, Function<IBakedModel, IBakedModel> modelWrapper);
	
	void add(@Nullable IBakedModel model);
	
	void add(@Nullable IBakedModel model, float yOffset);
	
	void add(@Nullable IBakedModel model, Function<IBakedModel, IBakedModel> modelWrapper);
	
	boolean empty();
	
	List<IBakedModel> models();
}
