package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public class PositionedModuleStorage extends AdvancedModuleStorage implements IPositionedModuleStorage {

	protected EnumStoragePosition position;

	public PositionedModuleStorage(IModular modular, EnumStoragePosition position) {
		super(modular);

		this.position = position;
	}

	@Override
	public IModuleState addModule(ItemStack itemStack, IModuleState state) {
		if (state == null) {
			return null;
		}

		if (moduleStates.add(state)) {
			state.setIndex(modular.getNextIndex());
			return state;
		}
		return null;
	}

	@Override
	public EnumStoragePosition getPosition() {
		return position;
	}
}
