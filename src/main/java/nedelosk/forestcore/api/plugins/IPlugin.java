package nedelosk.forestcore.api.plugins;

public interface IPlugin {

	public void preInit();

	void init();

	void postInit();

	void registerRecipes();

	String getRequiredMod();

	boolean getConfigOption();
	
}
