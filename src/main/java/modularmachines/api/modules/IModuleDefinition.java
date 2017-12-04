package modularmachines.api.modules;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.components.IModuleComponentFactory;

/**
 * Can be implemented to add components to a module.
 * <p>
 * Must be added to a module data with {@link IModuleDataBuilder#setDefinition(IModuleDefinition)}.
 */
public interface IModuleDefinition {
	
	/**
	 * Called at the creation of a module. Can be used to add components to it.
	 *
	 * @param module  The module that is currently created.
	 * @param factory A factory that can be used to create components.
	 */
	void addComponents(IModule module, IModuleComponentFactory factory);
	
	@SideOnly(Side.CLIENT)
	default void registerModels(IModuleModelRegistry registry) {
	}
	
	default void registerTypes(IModuleRegistry registry) {
	}
}
