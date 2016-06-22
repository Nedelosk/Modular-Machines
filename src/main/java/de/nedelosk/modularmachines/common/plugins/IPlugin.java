package de.nedelosk.modularmachines.common.plugins;

public interface IPlugin {

	public void preInit();

	void init();

	void postInit();

	void registerRecipes();

	String getRequiredMod();

	boolean getConfigOption();
}
