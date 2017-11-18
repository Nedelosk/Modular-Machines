package modularmachines.common.plugins;

import modularmachines.common.utils.Mod;

public abstract class Plugin {
	
	public void preInit() {
	}
	
	public void init() {
	}
	
	public void postInit() {
	}
	
	public void registerRecipes() {
	}
	
	public boolean isActive() {
		return true;
	}
	
	abstract Mod getMod();
}
