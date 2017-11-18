package modularmachines.api.modules;

public class DefaultModuleFactory implements IModuleFactory {
	
	public static final DefaultModuleFactory INSTANCE = new DefaultModuleFactory();
	
	private DefaultModuleFactory() {
	}
	
	@Override
	public Module createModule() {
		return new Module();
	}
	
}
