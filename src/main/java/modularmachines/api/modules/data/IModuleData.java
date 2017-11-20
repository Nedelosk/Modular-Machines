package modularmachines.api.modules.data;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.IForgeRegistryEntry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.positions.IModulePosition;

public interface IModuleData extends IForgeRegistryEntry<IModuleData> {
	/**
	 * A description of this module. It would be displayed in jei and the item tooltip.
	 */
	String getDescription();
	
	/**
	 * @return The translation kay of a description that describes the module.
	 */
	String getUnlocalizedDescription();
	
	/**
	 * Sets the unlocalized name of this module.
	 */
	void setUnlocalizedName(String unlocalizedName);
	
	String getDisplayName();
	
	int getAllowedComplexity();
	
	IModuleData setAllowedComplexity(int allowedComplexity);
	
	/**
	 * @return The complexity that a module with this data has.
	 */
	int getComplexity();
	
	IModuleData setComplexity(int complexity);
	
	/**
	 * The module factory creates the module out of this data if a player adds it to a {@link IModuleHandler}.
	 */
	IModuleData setFactory(Supplier<Module> factory);
	
	/**
	 * @return The size of this module.
	 */
	@Deprecated
	EnumModuleSizes getSize();
	
	@Deprecated
	void setSize(EnumModuleSizes size);
	
	/**
	 * The chance that the module drops if a player breaks the block that contains this module.
	 */
	float getDropChance();
	
	void setDropChance(float dropChance);
	
	Module createModule(IModuleHandler handler, IModulePosition position, IModuleDataContainer container, ItemStack itemStack);
	
	/**
	 * Uses the module factory to create a new instance of a module.
	 */
	Module createModule();
	
	/**
	 * Checks if the position is a valid position for this module.
	 */
	boolean isValidPosition(IModulePosition position);
	
	IModuleData setPositions(IModulePosition... positions);
	
	@Nullable
	ResourceLocation getWindowLocation();
	
	IModuleData setWindowLocation(ResourceLocation windowLocation);
	
	/* ITEM INFO */
	void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleDataContainer container);
	
	/* MODEL */
	@Nullable
	@SideOnly(Side.CLIENT)
	IModelData getModel();
	
	@SideOnly(Side.CLIENT)
	IModuleData setModel(IModelData modelData);
}
