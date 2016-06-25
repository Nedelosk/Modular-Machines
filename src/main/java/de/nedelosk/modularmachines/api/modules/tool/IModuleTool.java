package de.nedelosk.modularmachines.api.modules.tool;

import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.IRecipeManager;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modules.IModuleJEI;

public interface IModuleTool extends IModuleController {

	Object[] getRecipeModifiers(IModuleState state);

	String getRecipeCategory(IModuleState state);

	boolean removeInput(IModuleState state);

	boolean addOutput(IModuleState state);

	IRecipeManager getRecipeManager(IModuleState state);

	void setRecipeManager(IModuleState state, IRecipeManager manager);

	int getWorkTime(IModuleState state);

	void addWorkTime(IModuleState state, int workTime);

	void setWorkTime(IModuleState state, int workTime);

	int getWorkTimeTotal(IModuleState state);

	void setBurnTimeTotal(IModuleState state, int workTimeTotal);

	int createBurnTimeTotal(IModuleState state, int speedModifier);

	int getChance(IModuleState state);
	
	int getSpeedModifier(IModuleState state);
	
	/**
	 * The size of the tool. A number between 1 and 3.
	 */
	int getSize();
}
