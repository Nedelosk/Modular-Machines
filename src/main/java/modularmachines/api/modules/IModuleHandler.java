package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.positions.IModulePosition;

/**
 * Used by {@link IModuleProvider}s to handle modules.
 */
public interface IModuleHandler {
	
	/**
	 * @return The module that is currently at that position.
	 */
	IModule getModule(IModulePosition position);
	
	/**
	 * @return true if this handler has a module at the given position.
	 */
	boolean hasModule(IModulePosition position);
	
	/**
	 * @return true if this handler can handle the given position.
	 */
	boolean canHandle(IModulePosition position);
	
	/**
	 * @return A collection filled with all module positions that are available for this handler.
	 */
	Collection<IModulePosition> getValidPositions();
	
	/**
	 * @return A collection filled with all modules of this handler that are not empty.
	 */
	Collection<IModule> getModules();
	
	/**
	 * @return A collection filled with all modules of this handler.
	 */
	Collection<IModule> getAllModules();
	
	/**
	 * @return true if this handler contains no modules.
	 */
	boolean isEmpty();
	
	/**
	 * Tries to inserts a module at the given position and return if it has inserted it.
	 *
	 * @param position  Position to insert to.
	 * @param type The data container of the item
	 * @param itemStack The item stack of the module
	 * @param simulate  If true, the insertion is only simulated
	 * @return True if the modules was inserted at the position.
	 */
	boolean insertModule(IModulePosition position, IModuleType type, ItemStack itemStack, boolean simulate);
	
	/**
	 * Tries to extract a module from the given position.
	 *
	 * @param position Position to extract.
	 * @param simulate If true, the extraction is only simulated
	 * @return A list with all items that the player should get. The list is empty if the extraction fails.
	 */
	List<ItemStack> extractModule(IModulePosition position, boolean simulate);
	
	/**
	 * @return The internal index of the given position.
	 */
	int getPositionIndex(IModulePosition position);
	
	/**
	 * @return The position that is handled under the given internal index.
	 */
	@Nullable
	IModulePosition getPosition(int index);
	
	/**
	 * @return The module provider that contains this module handler.
	 */
	IModuleProvider getProvider();
}
