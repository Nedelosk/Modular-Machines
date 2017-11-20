package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.IModulePosition;

/**
 * Implement this interface as a capability which should handle modules.
 * <p>
 * You can use "" to create an instance of this or you can use your own implementation.
 */
public interface IModuleHandler {
	
	/**
	 * @return The module that is currently at that position.
	 */
	@Nullable
	Module getModule(IModulePosition position);
	
	boolean hasModule(IModulePosition position);
	
	boolean canHandle(IModulePosition position);
	
	/**
	 * @return A collection filled with all module positions that are available for this handler.
	 */
	Collection<IModulePosition> getValidPositions();
	
	/**
	 * @return A collection filled with all modules of this handler.
	 */
	Collection<Module> getModules();
	
	/**
	 * Tries to inserts a module at the given position and return if it has inserted it.
	 *
	 * @param position  Position to insert to.
	 * @param container
	 * @param itemStack
	 * @return True if the modules was inserted at the position.
	 */
	boolean insertModule(IModulePosition position, IModuleDataContainer container, ItemStack itemStack, boolean simulate);
	
	List<ItemStack> extractModule(IModulePosition position, boolean simulate);
	
	int getPositionIndex(IModulePosition position);
	
	@Nullable
	IModulePosition getPosition(int index);
	
	IModuleProvider getProvider();
}
