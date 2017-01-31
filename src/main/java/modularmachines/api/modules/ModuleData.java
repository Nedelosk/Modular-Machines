package modularmachines.api.modules;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import modularmachines.api.modules.assemblers.AssemblerError;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.assemblers.SlotAssembler;
import modularmachines.api.modules.assemblers.SlotAssemblerStorage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class ModuleData extends IForgeRegistryEntry.Impl<ModuleData> {

	private Map<Class<? extends Object>, IModelData> models = new HashMap<>();
	private int complexity = 0;
	private EnumModuleSizes size = EnumModuleSizes.MEDIUM;
	
	
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
		return complexity;
	}
	
	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}
	
	public EnumModuleSizes getSize(){
		return size;
	}
	
	public void setSize(EnumModuleSizes size) {
		this.size = size;
	}
	
	public void canAssemble(IAssembler assembler, List<AssemblerError> errors){
	}
	
	public Module createModule(IModuleContainer container, ItemStack itemStack){
		Module module = createModule();
		module.onCreateModule(container, itemStack);
		return module;
	}
	
	public boolean isPositionValid(IStoragePosition position){
		return true;
	}
	
	public Module createModule(){
		return null;
	}
	
	public int getAllowedComplexity(){
		return 0;
	}
	
	public boolean isItemValid(IAssembler assembler, IStoragePosition position, ItemStack stack, @Nullable SlotAssembler slot, SlotAssemblerStorage storageSlot){
		return false;
	}
	
	public IStorage createStorage(IModuleLogic moduleLogic, IStoragePosition position, @Nullable IStoragePage page) {
		return null;
	}
	
	public boolean isStorageAt(IStoragePosition position, SlotAssemblerStorage slot){
		return false;
	}
	
	public boolean isStorage(IStoragePosition position){
		return false;
	}
	
	public IStoragePage createStoragePage(IAssembler assembler, IStoragePosition position, @Nullable IStorage storage){
		return null;
	}
	
	@Nullable
	public IStoragePage createChildPage(IAssembler assembler, IStoragePosition position){
		return null;
	}
	
	public Collection<IStoragePosition> getChildPositions(IStoragePosition position){
		return Collections.emptyList();
	}

	public void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleContainer container) {
		
	}
}
