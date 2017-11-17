package modularmachines.api.modules.storages;

import javax.annotation.Nullable;
import java.util.Collection;

public interface IStorageHandler {
	@Nullable
	IStorage getStorage(IStoragePosition position);
	
	Collection<IStoragePosition> getValidPositions();
}
