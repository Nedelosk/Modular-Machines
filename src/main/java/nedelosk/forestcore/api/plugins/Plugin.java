package nedelosk.forestcore.api.plugins;

public abstract class Plugin implements IPlugin {

	public void preInit() {

	}

	public void init() {

	}

	public void postInit() {

	}

	public void registerRecipes() {

	}

	public String getRequiredMod() {
		return null;
	}

	public boolean getConfigOption() {
		return true;
	}

}
