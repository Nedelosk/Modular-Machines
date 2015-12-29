package nedelosk.forestcore.library.plugins;

public abstract class APlugin implements IPlugin {

	@Override
	public void preInit() {

	}

	@Override
	public void init() {

	}

	@Override
	public void postInit() {

	}

	@Override
	public void registerRecipes() {

	}

	@Override
	public String getRequiredMod() {
		return null;
	}

	@Override
	public boolean getConfigOption() {
		return true;
	}

}
