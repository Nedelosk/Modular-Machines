package modularmachines.api.modules;

/**
 * Describes a possible position of a module in a {@link IModuleHandler}.
 */
public interface IModulePosition {
	
	/**
	 * Checks if the module of the data can be placed at this position.
	 */
	boolean isValidModule(ModuleData moduleData);
}
