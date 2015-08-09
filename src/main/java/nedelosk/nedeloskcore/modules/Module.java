package nedelosk.nedeloskcore.modules;

public abstract class Module {
	
	public void preInit(){}
	public void init(){}
	public void postInit(){}
	
	public void registerRecipes(){}
	public abstract boolean getRequiredBoolean();
	
	public abstract String getModuleName();
	
	public abstract String getModuleVersion();
	
}
