package modularmachines.client.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import modularmachines.common.core.Constants;

public class BuiltInModelLoader implements ICustomModelLoader {
	private final Map<String, IModel> builtInModels;
	
	public BuiltInModelLoader(Map<String, IModel> builtInModels) {
		this.builtInModels = ImmutableMap.copyOf(builtInModels);
	}
	
	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(Constants.MOD_ID) && this.builtInModels.containsKey(modelLocation.getResourcePath());
	}
	
	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return this.builtInModels.get(modelLocation.getResourcePath());
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		for (IModel model : this.builtInModels.values()) {
			if (model instanceof IResourceManagerReloadListener) {
				((IResourceManagerReloadListener) model).onResourceManagerReload(resourceManager);
			}
		}
	}
}
