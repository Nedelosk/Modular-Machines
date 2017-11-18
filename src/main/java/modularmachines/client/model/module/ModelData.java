package modularmachines.client.model.module;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.model.IModelProperty;
import modularmachines.api.modules.model.ModelLocationBuilder;

@SideOnly(Side.CLIENT)
public abstract class ModelData<M extends Module> implements IModelData {
	
	protected final ModelLocations locations;
	
	public ModelData() {
		locations = new ModelLocations();
	}
	
	public void add(IModelProperty key, ModelLocationBuilder location) {
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
}
