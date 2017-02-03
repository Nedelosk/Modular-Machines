package modularmachines.common.modules.storages;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.assemblers.StoragePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.assembler.BasicStoragePage;
import modularmachines.common.modules.assembler.ModuleStorage;
import modularmachines.common.modules.assembler.Storage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

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
		List<Module> modules = new ArrayList<>();
		ModuleStorage moduleStorage = new ModuleStorage(moduleLogic, modules);
		Storage storage = new Storage(position, moduleStorage);
		if(page != null){
			IItemHandlerModifiable itemHandler = page.getItemHandler();
			for(int i = 0;i < itemHandler.getSlots();i++){
				Module module = ModuleHelper.createModule(moduleStorage, itemHandler.getStackInSlot(i));
				if(module != null){
					modules.add(module);
				}
			}
		}
		return storage;
	}
	
	@Override
	public IStoragePage createStoragePage(IAssembler assembler, IStoragePosition position, IStorage storage) {
		StoragePage page = new BasicStoragePage(assembler, position);
		if (storage != null) {
			IItemHandlerModifiable itemHandler = page.getItemHandler();
			List<Module> modules = storage.getStorage().getModules();
			for(int i = 0;i < modules.size();i++){
				Module module = modules.get(i);
				itemHandler.setStackInSlot(i, module.getParentItem());
			}
		}
		return page;
	}
	
}
