package modularmachines.client.model.module;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

import modularmachines.api.modules.model.IModelKey;
import modularmachines.api.modules.model.IModelLocations;
import modularmachines.api.modules.model.ModelLocationBuilder;

public class ModelLocations implements IModelLocations {
	private Map<IModelKey, ResourceLocation> locations = new HashMap<>();
	
	public void add(IModelKey key, ModelLocationBuilder location) {
		locations.put(key, location.build());
	}
	
	@Nullable
	public ResourceLocation get(IModelKey key) {
		return locations.get(key);
	}
	
	public Collection<ResourceLocation> values() {
		return locations.values();
	}
	
	@Override
	public Iterator<ResourceLocation> iterator() {
		return locations.values().iterator();
	}
}
