package de.nedelosk.modularmachines.common.modules.storage;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;

public class PositionedModuleStorage extends ModuleStorage implements IPositionedModuleStorage {

	protected EnumPosition position;

	public PositionedModuleStorage(IModular modular, EnumPosition position) {
		super(modular);

		this.position = position;
	}

	@Override
	public EnumPosition getPosition() {
		return position;
	}
}
