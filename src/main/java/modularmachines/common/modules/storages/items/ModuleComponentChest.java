package modularmachines.common.modules.storages.items;

import java.util.List;

import modularmachines.api.modules.pages.ModuleComponent;
import modularmachines.common.containers.SlotModule;
import modularmachines.common.inventory.ItemHandlerModule;

public class ModuleComponentChest extends ModuleComponent<ModuleChest> {

	public ModuleComponentChest(ModuleChest parent) {
		super(parent);
	}
	
	@Override
	public void createSlots(List slots) {
		ItemHandlerModule itemHandler = parent.getItemHandler();
		for (int j = 0; j < 3; ++j) {
			for (int i = 0; i < 9; ++i) {
				slots.add(new SlotModule(itemHandler, i + j * 9, 8 + i * 18, 18 + j * 18));
			}
		}
	}
}
