package de.nedelosk.forestmods.api.modules;

public interface IModuleAdvanced extends IModule {

	Object[] getRecipeModifiers();

	String getRecipeCategory();

	boolean removeInput();

	boolean addOutput();

	IRecipeManager getRecipeManager();

	void setRecipeManager(IRecipeManager manager);

	int getSpeed();
}
