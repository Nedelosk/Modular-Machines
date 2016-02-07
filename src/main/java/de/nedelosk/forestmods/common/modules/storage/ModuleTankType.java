package de.nedelosk.forestmods.common.modules.storage;

import de.nedelosk.forestmods.api.modules.storage.tanks.IModuleTankType;

public class ModuleTankType implements IModuleTankType {

	private final int capacity;

	public ModuleTankType(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}
}
