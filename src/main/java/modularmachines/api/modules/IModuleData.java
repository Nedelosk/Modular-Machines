package modularmachines.api.modules;

import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.registries.IForgeRegistryEntry;

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
	
	String getDisplayName();
	
	/**
	 * @return The complexity that a module with this data has.
	 */
	int getComplexity();
	
	/* POSITIONS */
	
	/**
	 * Checks if the position is a valid position of the module.
	 */
	boolean isValidPosition(IModulePosition position);
	
	/* ITEM INFO */
	void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleType container);
	
	IModuleDefinition getDefinition();
}
