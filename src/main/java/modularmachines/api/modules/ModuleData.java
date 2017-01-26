package modularmachines.api.modules;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import modularmachines.api.modules.assemblers.AssemblerError;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.SlotAssembler;
import modularmachines.api.modules.assemblers.SlotAssemblerStorage;
import modularmachines.api.modules.assemblers.StoragePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class ModuleData extends IForgeRegistryEntry.Impl<ModuleData> {

	
	private Map<Class<? extends Object>, IModelData> models = new HashMap<>();
	
	/**
	 * A description of this module. It woud be displayed in jei and the module crafter gui.
	 */
	public String getDescription(){
		return "";
	}
	
	public String getDisplayName(){
		return "";
	}
	
	public int getComplexity(){
		return 0;
	}
	
	public void canAssemble(IAssembler assembler, List<AssemblerError> errors){
	}
	
	public Module createModule(IModuleContainer container, ItemStack itemStack){
		Module module = createModule(container);
		module.onCreateModule(itemStack);
		return module;
	}
	
	protected Module createModule(IModuleContainer container){
		return null;
	}
	
	public int getAllowedComplexity(){
		return 0;
	}
	
	public boolean isValid(IAssembler assembler, IStoragePosition position, ItemStack stack, @Nullable SlotAssembler slot, SlotAssemblerStorage storageSlot){
		return false;
	}
	
	public IStorage createStorage(IModuleLogic moduleLogic, IStoragePosition position) {
		return null;
	}
	
	public boolean isStorageAt(IStoragePosition position, SlotAssemblerStorage slot){
		return false;
	}
	
	public boolean isStorage(IStoragePosition position){
		return false;
	}
	
	public StoragePage createStoragePage(IAssembler assembler, IStoragePosition position){
		return null;
	}
	
	public StoragePage createChildPage(IAssembler assembler, IStoragePosition position){
		return null;
	}
	
	public Collection<IStoragePosition> getChildPositions(IStoragePosition position){
		return Collections.emptyList();
	}
}
