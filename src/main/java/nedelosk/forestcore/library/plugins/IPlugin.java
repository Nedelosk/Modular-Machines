package nedelosk.forestcore.library.plugins;

public interface IPlugin {

	public void preInit();

	void init();

	void postInit();

	void registerRecipes();

	String getRequiredMod();

	boolean getConfigOption();

}
