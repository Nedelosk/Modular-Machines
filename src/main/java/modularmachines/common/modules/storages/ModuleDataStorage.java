package modularmachines.common.modules.storages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.assembler.BasicStoragePage;
import modularmachines.common.modules.assembler.ModuleStorage;
import modularmachines.common.modules.assembler.Storage;
import net.minecraft.item.ItemStack;

public class ModuleDataStorage extends ModuleData {

	@Override
	public void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleContainer container) {
		if (showPosition(container)) {
			//tooltip.add(I18n.translateToLocal("mm.module.tooltip.storage.position") + Arrays.toString(getPositions(container)).replace("[", "").replace("]", ""));
		}
		super.addTooltip(tooltip, itemStack, container);
	}
	
	@Override
	public boolean isPositionValid(IStoragePosition position) {
		EnumModuleSizes size = getSize();
		if (size == EnumModuleSizes.LARGE) {
			return position == EnumStoragePosition.LEFT || position == EnumStoragePosition.RIGHT;
		} else if (size == EnumModuleSizes.SMALL) {
			return position == EnumStoragePosition.TOP || position == EnumStoragePosition.BACK;
		}
		return position != EnumStoragePosition.NONE;
	}

	protected boolean showPosition(IModuleContainer container) {
		return true;
	}
	
	@Override
	public IStorage createStorage(IModuleLogic moduleLogic, IStoragePosition position, IStoragePage page) {
		if(page != null){
			ItemStack storageItem = page.getStorageStack();
			if(storageItem != null){
				Module module = ModuleHelper.createModule(storageItem);
				if(module != null){
					return new Storage(position, new ModuleStorage(moduleLogic, Collections.singletonList(module)));
				}
			}
		}
		return new Storage(position, new ModuleStorage(moduleLogic, new ArrayList<>()));
	}
	
	@Override
	public IStoragePage createStoragePage(IAssembler assembler, IStoragePosition position, IStorage storage) {
		if (storage != null) {
			ItemStack parentItem = storage.getModule().getParentItem();
			IStoragePage page = new BasicStoragePage(assembler, position);
			page.getItemHandler().setStackInSlot(0, parentItem);
			return page;
		}
		return new BasicStoragePage(assembler, position);
	}
	
}
