package modularmachines.common.modules.pages;

import modularmachines.api.modules.handlers.filters.DefaultFilter;
import modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.IModuleMachine;

public class ChestPage extends MainPage<IModuleMachine> {

	public ChestPage(IModuleState<IModuleMachine> module) {
		super("chest", module);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				invBuilder.addInventorySlot(true, 8 + k * 18, 18 + j * 18, DefaultFilter.INSTANCE);
			}
		}
	}
}
