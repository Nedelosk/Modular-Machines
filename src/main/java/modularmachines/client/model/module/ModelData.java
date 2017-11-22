package modularmachines.client.model.module;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModelProperty;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.api.modules.model.ModelLocationBuilder;

@SideOnly(Side.CLIENT)
public class ModelData implements IModelData {
	
	protected final ModelLocations locations;
	
	public ModelData() {
		locations = new ModelLocations();
	}
	
	public void add(IModelProperty key, ModelLocationBuilder location) {
		locations.add(key, location.build());
	}
	
	public void add(IModelProperty key, ResourceLocation location) {
		locations.add(key, location);
	}
	
	@Nullable
	protected ResourceLocation get(IModelProperty key) {
		return locations.get(key);
	}
	
	@Override
	public ModelLocations locations() {
		return locations;
	}
	
	@Override
	public void addModel(IModelList modelList, IModule module, IModuleModelState modelState) {
		for (IModelProperty property : locations.getProperties()) {
			if (modelState.get(property) || !modelState.has(property)) {
				modelList.add(property);
			}
		}
	}
}
