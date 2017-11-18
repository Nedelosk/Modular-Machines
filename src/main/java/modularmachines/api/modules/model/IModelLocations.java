package modularmachines.api.modules.model;

import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.util.ResourceLocation;

public interface IModelLocations extends Iterable<ResourceLocation> {
	
	void add(IModelKey key, ModelLocationBuilder location);
	
	@Nullable
	ResourceLocation get(IModelKey key);
	
	Collection<ResourceLocation> values();
}
