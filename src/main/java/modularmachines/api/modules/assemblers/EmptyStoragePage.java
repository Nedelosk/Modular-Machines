package modularmachines.api.modules.assemblers;

import modularmachines.api.modules.storages.IStoragePosition;

public class EmptyStoragePage extends StoragePage {
	
	public EmptyStoragePage(IAssembler assembler, IStoragePosition position) {
		super(assembler, position);
		init();
	}
	
	@Override
	public boolean isEmpty() {
		return true;
	}

}
