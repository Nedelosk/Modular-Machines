package modularmachines.api.modules.storages;

import java.util.HashMap;
import java.util.Map;

public class StorageRegistry {

	private Map<String, Class<? extends IStorage>> storageClasses = new HashMap<>();
	
}
