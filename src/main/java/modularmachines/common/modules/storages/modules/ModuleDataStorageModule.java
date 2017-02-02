package modularmachines.common.modules.storages.modules;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.assemblers.StoragePage;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.assembler.ModuleStorage;
import modularmachines.common.modules.assembler.ModuleStoragePage;
import modularmachines.common.modules.assembler.Storage;
import modularmachines.common.modules.assembler.StorageModule;
import net.minecraftforge.items.IItemHandlerModifiable;

public class ModuleDataStorageModule extends ModuleData {

	private final EnumModuleSizes storageSize;
	
	public ModuleDataStorageModule(EnumModuleSizes storageSize) {
		this.storageSize = storageSize;
	}
	
	@Override
	public IStorage createStorage(IModuleLogic moduleLogic, IStoragePosition position, IStoragePage page) {
		List<Module> modules = new ArrayList<>();
		ModuleStorage moduleStorage = new ModuleStorage(moduleLogic, modules);
		Storage storage = new StorageModule(position, moduleStorage, storageSize);
		if(page != null){
			IItemHandlerModifiable itemHandler = page.getItemHandler();
			for(int i = 0;i < itemHandler.getSlots();i++){
				Module module = ModuleHelper.createModule(moduleStorage, itemHandler.getStackInSlot(i));
				if(module != null){
					modules.set(i, module);
				}
			}
		}
		return storage;
	}
	
	@Override
	public IStoragePage createStoragePage(IAssembler assembler, IStoragePosition position, IStorage storage) {
		StoragePage page = new ModuleStoragePage(assembler, position, storageSize);
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
