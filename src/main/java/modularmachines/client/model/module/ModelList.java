package modularmachines.client.model.module;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModelList;
import modularmachines.client.model.TRSRBakedModel;

@SideOnly(Side.CLIENT)
public class ModelList implements IModelList {
	private final List<IBakedModel> models = new LinkedList<>();
	private final IModelInfo info;
	
	public ModelList(IModelInfo info) {
		this.info = info;
	}
	
	@Override
	public void add(@Nullable ResourceLocation location) {
		if (location == null) {
			return;
		}
		add(info.getModel(location));
	}
	
	@Override
	public void add(@Nullable IBakedModel model) {
		if (model != null) {
			models.add(model);
		}
	}
	
	@Override
	public void add(@Nullable IBakedModel model, float y) {
		if (model == null) {
			return;
		}
		add(model, m -> new TRSRBakedModel(m, 0F, y, 0F));
	}
	
	@Override
	public void add(@Nullable ResourceLocation location, Function<IBakedModel, IBakedModel> modelWrapper) {
		if (location == null) {
			return;
		}
		IBakedModel bakedModel = info.getModel(location);
		if (bakedModel != null) {
			add(modelWrapper.apply(bakedModel));
		}
	}
	
	@Override
	public void add(@Nullable IBakedModel model, Function<IBakedModel, IBakedModel> modelWrapper) {
		add(model == null ? null : modelWrapper.apply(model));
	}
	
	@Override
	public boolean empty() {
		return models.isEmpty();
	}
	
	@Override
	public List<IBakedModel> models() {
		return models;
	}
}
