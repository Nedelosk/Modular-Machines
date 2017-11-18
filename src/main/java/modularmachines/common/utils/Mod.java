package modularmachines.common.utils;

import net.minecraftforge.fml.common.Loader;

public enum Mod {
	FORESTRY("forestry"),
	JEI("jei");
	
	private final String modID;
	
	Mod(String modID) {
		this.modID = modID;
	}
	
	public boolean active() {
		return Loader.isModLoaded(this.modID);
	}
	
	@Override
	public String toString() {
		return modID;
	}
	
}
