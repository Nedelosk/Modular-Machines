package modularmachines.api.modules.data;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.IForgeRegistryEntry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.positions.IModulePosition;

/**
 * A module data contains all information about a module that is static like the name, the model or the positions at
 * that the module can be placed.
 *
 * You can append a {@link IModuleData}s to an item by using {@link IModuleDataContainer}s.
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
	
	/**
	 * The chance that the module drops if a player breaks the block that contains this module.
	 */
	float getDropChance();
	
	void setDropChance(float dropChance);
	
	/**
	 * Checks if the position is a valid position for this module.
	 */
	boolean isValidPosition(IModulePosition position);
	
	IModuleData setPositions(IModulePosition... positions);
	
	@Nullable
	ResourceLocation getWallModelLocation();
	
	IModuleData setWallModelLocation(ResourceLocation WallModelLocation);
	
	/* ITEM INFO */
	void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleDataContainer container);
	
	IModuleData setDefinition(IModuleDefinition definition);
	
	IModuleDefinition getDefinition();
	
	/* MODEL */
	@Nullable
	@SideOnly(Side.CLIENT)
	IModelData getModel();
	
	@SideOnly(Side.CLIENT)
	IModuleData setModel(IModelData modelData);
}
