package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.registries.IForgeRegistryEntry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.containers.IModuleDataContainer;
import modularmachines.api.modules.model.IModelData;

public class ModuleData extends IForgeRegistryEntry.Impl<ModuleData> {
	
	@SideOnly(Side.CLIENT)
	private Map<Class<? extends Object>, IModelData> models = new HashMap<>();
	private int complexity = 0;
	private int allowedComplexity = 0;
	private float dropChance = 1.0F;
	private EnumModuleSizes size = EnumModuleSizes.MEDIUM;
    private String unlocalizedName;
    private IModuleFactory factory = DefaultModuleFactory.INSTANCE;
    private final IModulePosition[] positions;
    
	public ModuleData(IModulePosition... positions) {
		this.positions = positions;
	}
	
	/**
	 * A description of this module. It would be displayed in jei and the item tooltip.
	 */
	public String getDescription(){
		return I18n.translateToLocal(getUnlocalizedDescription());
	}
	
	/**
	 * @return The translation kay of a description that describes the module.
	 */
	public String getUnlocalizedDescription(){
		return "module." + unlocalizedName + ".description";
	}
	
	/**
	 * Sets the unlocalized name of this module.
	 */
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
	
	/**
	 * @return The complexity that a module with this data has.
	 */
	public int getComplexity(){
		return complexity;
	}
	
	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}
	
	/**
	 * The module factory creates the module out of this data if a player adds it to a {@link IModuleHandler}.
	 */
	public void setFactory(IModuleFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * @return The size of this module.
	 */
	@Deprecated
	public EnumModuleSizes getSize(){
		return size;
	}
	
	@Deprecated
	public void setSize(EnumModuleSizes size) {
		this.size = size;
	}
	
	/**
	 * The chance that the module drops if a player breaks the block that contains this module.
	 */
	public float getDropChance(){
		return dropChance;
	}
	
	public void setDropChance(float dropChance) {
		this.dropChance = dropChance;
	}
	
	public Module createModule(IModuleHandler handler, IModulePosition position, IModuleDataContainer container, ItemStack itemStack){
		Module module = createModule();
		module.onCreateModule(handler, position, container, itemStack);
		return module;
	}
	
	
	/**
	 * Uses the module factory to create a new instance of a module.
	 */
	public Module createModule(){
		return factory.createModule();
	}
	
	public IModuleType[] getTypes(ItemStack itemStack){
		return new IModuleType[0];
	}
	
	/**
	 * Checks if the position is a valid position for this module.
	 */
	public boolean isValidPosition(IModulePosition position){
		for(IModulePosition otherPosition : positions){
			if(position == otherPosition){
				return true;
			}
		}
		return false;
	}

	/* ITEM INFO */
	public void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleDataContainer container) {
		if(I18n.canTranslate(getUnlocalizedDescription())){
			tooltip.add(getDescription());
		}
	}
	
	/* MODEL */
	public void addModel(Class<? extends Object> clazz, IModelData modelData){
		models.put(clazz, modelData);
	}
	
	@Nullable
	public IModelData getModel(Class<? extends Object> clazz){
		return models.get(clazz);
	}
}
