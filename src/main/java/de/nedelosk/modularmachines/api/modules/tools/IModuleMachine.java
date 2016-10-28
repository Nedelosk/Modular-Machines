package de.nedelosk.modularmachines.api.modules.tools;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModuleRecipe;
import de.nedelosk.modularmachines.api.modules.IModuleWorkerTime;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.properties.IModuleMachineProperties;
import de.nedelosk.modularmachines.api.recipes.IRecipe;

public interface IModuleMachine extends IModuleTool, IModuleWorkerTime, IModuleRecipe, IModuleMachineProperties {

	EnumToolType getType(IModuleState state);

	int getChance(IModuleState state);

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
