package modularmachines.api.modules.tools;

import java.util.List;

import modularmachines.api.modules.IModuleRecipe;
import modularmachines.api.modules.IModuleWorkerTime;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.properties.IModuleMachineProperties;
import modularmachines.api.recipes.IRecipe;

public interface IModuleMachine extends IModuleTool, IModuleWorkerTime, IModuleRecipe, IModuleMachineProperties {

	EnumToolType getType(IModuleState state);

	float getChance(IModuleState state);

	/**
	 * @return All valid recipe for that module state.
	 */
	List<IRecipe> getRecipes(IModuleState state);

	/**
	 * @return The first valid recipe that are matching with the inputs of the
	 *         machine.
	 */
	IRecipe getValidRecipe(IModuleState state);
}
