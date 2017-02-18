package modularmachines.client.model.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.model.ModelLocation;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ModelData<M extends Module> implements IModelData<IBakedModel> {

	protected final Map<String, ResourceLocation> locations;
	
	public ModelData() {
		locations = new HashMap<>();
	}
	
	public void addLocation(String key, ModelLocation location){
		locations.put(key, location.toLocation());
	}
	
	protected ResourceLocation get(String key){
		return locations.get(key);
	}
	
	@Override
	public Collection<ResourceLocation> getValidLocations() {
		return locations.values();
	}
}
