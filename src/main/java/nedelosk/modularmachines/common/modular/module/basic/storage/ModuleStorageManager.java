package nedelosk.modularmachines.common.modular.module.basic.storage;

import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.gui.ModuleGui;
import nedelosk.modularmachines.api.modular.module.basic.storage.IModuleStorageManager;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.PartType;
import net.minecraft.item.ItemStack;

public class ModuleStorageManager extends ModuleGui implements IModuleStorageManager {

	public ModuleStorageManager() {
	}

	@Override
	public String getModuleName() {
		return "StorageManager";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public IModule buildModule(ItemStack[] stacks) {
		return null;
	}

	@Override
	public PartType[] getRequiredComponents() {
		return null;
	}

	@Override
	public ModuleStack creatModule(ItemStack stack) {
		return null;
	}

	@Override
	public int getColor() {
		return 0;
	}

}
