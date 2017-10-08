package modularmachines.common.modules.storages;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IModuleSlot;
import modularmachines.api.modules.assemblers.IModuleSlots;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.assembler.page.BasicStoragePage;
import modularmachines.common.modules.assembler.ModuleStorage;
import modularmachines.common.modules.assembler.Storage;
import modularmachines.common.modules.assembler.page.StoragePage;

public class ModuleDataStorage extends ModuleData {
	
	@Override
	public boolean isPositionValid(IStoragePosition position) {
		return false;
	}
	
	@Override
	public IStorage createStorage(IModuleLogic moduleLogic, IStoragePosition position, IStoragePage page) {
		List<Module> modules = new ArrayList<>();
		ModuleStorage moduleStorage = new ModuleStorage(moduleLogic, modules);
		Storage storage = new Storage(position, moduleStorage);
		if(page != null){
			IModuleSlots slots = page.getSlots();
			for(IModuleSlot slot : slots){
				Module module = ModuleHelper.createModule(moduleStorage, slot.getItem());
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
			IModuleSlots slots = page.getSlots();
			List<Module> modules = storage.getModules().getModules();
			for(int i = 0;i < modules.size();i++){
				Module module = modules.get(i);
				slots.setItem(i, module.getParentItem());
			}
		}
		return page;
	}
	
}
