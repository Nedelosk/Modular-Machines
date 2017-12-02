package modularmachines.api.modules;

import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.registries.IForgeRegistryEntry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.model.IModuleKeyGenerator;
import modularmachines.api.modules.model.IModuleModelBakery;
import modularmachines.api.modules.positions.IModulePosition;

/**
 * A module data contains all information about a module that is static like the name, the model or the positions at
 * that the module can be placed.
 *
 * You can append a {@link IModuleData}s to an item by using {@link IModuleType}s.
 */
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
	
	/* POSITIONS */
	
	/**
	 * Checks if the position is a valid position of the module.
	 */
	boolean isValidPosition(IModulePosition position);
	
	/**
	 * Sets the valid positions of the module.
	 */
	IModuleData setPositions(IModulePosition... positions);
	
	/* TYPES */
	
	/**
	 * Registers a {@link IModuleType} that represents this data and the given {@link ItemStack}.
	 */
	IModuleData registerType(ItemStack itemStack);
	
	/* ITEM INFO */
	void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleType container);
	
	IModuleData setDefinition(IModuleDefinition definition);
	
	IModuleDefinition getDefinition();
	
	/* MODEL */
	@SideOnly(Side.CLIENT)
	IModuleModelBakery getBakery();
	
	@SideOnly(Side.CLIENT)
	IModuleData setBakery(IModuleModelBakery bakery);
	
	IModuleKeyGenerator getGenerator();
	
	IModuleData setGenerator(IModuleKeyGenerator generator);
}
