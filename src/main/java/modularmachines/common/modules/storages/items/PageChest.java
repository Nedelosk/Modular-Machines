package modularmachines.common.modules.storages.items;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.ModulePage;

public class PageChest extends ModulePage {

	public PageChest(Module parent) {
		super(parent);
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
