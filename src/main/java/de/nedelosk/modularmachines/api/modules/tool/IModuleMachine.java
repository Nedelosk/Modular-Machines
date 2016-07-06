package de.nedelosk.modularmachines.api.modules.tool;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;

public interface IModuleMachine extends IModuleTool {

	boolean removeInput(IModuleState state);

	boolean addOutput(IModuleState state);

	int getWorkTime(IModuleState state);

	void addWorkTime(IModuleState state, int workTime);

	void setWorkTime(IModuleState state, int workTime);

	int getWorkTimeTotal(IModuleState state);

	void setBurnTimeTotal(IModuleState state, int workTimeTotal);

	int createBurnTimeTotal(IModuleState state, int speedModifier);

	int getChance(IModuleState state);

	int getSpeed(IModuleState state);

	/* RECIPSE */
	IRecipe getCurrentRecipe(IModuleState state);

	void setCurrentRecipe(IModuleState state, IRecipe recipe);

	List<IRecipe> getRecipes(IModuleState state);

	IRecipe getValidRecipe(IModuleState state);

	/**
	 * @return True if the stack is a input, of a recipe, at the matching position.
	 */
	boolean isRecipeInput(IModuleState state, RecipeItem item);
}
