package modularmachines.common.utils;

import net.minecraftforge.fml.common.Loader;

public enum Mod {
	FORESTRY("forestry"),
	THERMAL_EXPANSION("thermalexpansion"),
	JEI("jei");
	
	private final String modID;
	private boolean isLoaded;
	
	Mod(String modID) {
		this.modID = modID;
		this.isLoaded = Loader.isModLoaded(this.modID);
	}
	
	public boolean active() {
		return isLoaded;
	}
	
	
	public String modID() {
		return modID;
	}
	
	@Override
	public String toString() {
		return modID;
	}
	
}
