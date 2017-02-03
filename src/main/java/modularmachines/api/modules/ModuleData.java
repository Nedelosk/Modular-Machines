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
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class ModuleData extends IForgeRegistryEntry.Impl<ModuleData> {
	
	private Map<Class<? extends Object>, IModelData> models = new HashMap<>();
	private int complexity = 0;
	private int allowedComplexity = 0;
	private EnumModuleSizes size = EnumModuleSizes.MEDIUM;
    private String unlocalizedName;
    private IModuleFactory factory = DefaultModuleFactory.INSTANCE;
    
	/**
	 * A description of this module. It woud be displayed in jei and the item tooltip.
	 */
	public String getDescription(){
		return I18n.translateToLocal(getDescriptionKey());
	}
	
	protected String getDescriptionKey(){
		return "module." + unlocalizedName + ".description";
	}
	
	public void setUnlocalizedName(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}
	
	public String getDisplayName(){
		return I18n.translateToLocal("module." + unlocalizedName + ".name");
	}
	
	public int getAllowedComplexity(){
		return allowedComplexity;
	}
	
	public void setAllowedComplexity(int allowedComplexity) {
		this.allowedComplexity = allowedComplexity;
	}
	
	public int getComplexity(){
		return complexity;
	}
	
	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}
	
	public void setFactory(IModuleFactory factory) {
		this.factory = factory;
	}
	
	public EnumModuleSizes getSize(){
		return size;
	}
	
	public void setSize(EnumModuleSizes size) {
		this.size = size;
	}
	
	public void canAssemble(IAssembler assembler, List<AssemblerError> errors){
	}
	
	public Module createModule(IModuleStorage storage, IModuleContainer container, ItemStack itemStack){
		Module module = createModule(storage);
		module.onCreateModule(container, this, itemStack);
		return module;
	}
	
	public boolean isPositionValid(IStoragePosition position){
		return true;
	}
	
	public Module createModule(IModuleStorage storage){
		return factory.createModule(storage);
	}
	
	public boolean isItemValid(IAssembler assembler, IStoragePosition position, ItemStack stack, @Nullable SlotAssembler slot, SlotAssemblerStorage storageSlot){
		return true;
	}
	
	public IStorage createStorage(IModuleLogic moduleLogic, IStoragePosition position, @Nullable IStoragePage page) {
		return null;
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
		if(I18n.canTranslate(getDescriptionKey())){
			tooltip.add(getDescription());
		}
	}
}
