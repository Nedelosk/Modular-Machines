package de.nedelosk.modularmachines.common.plugins;

public abstract class APlugin {

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

	public boolean isActive() {
		return true;
	}
}
