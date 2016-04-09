package de.nedelosk.forestmods.api.modules;

import de.nedelosk.forestmods.api.modules.integration.IModuleNEI;

public interface IModuleAdvanced extends IModuleNEI {

	Object[] getRecipeModifiers();

	String getRecipeCategory();

	boolean removeInput();

	boolean addOutput();

	IRecipeManager getRecipeManager();

	void setRecipeManager(IRecipeManager manager);

	int getSpeed();
}
