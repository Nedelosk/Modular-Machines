package modularmachines.api.modules;

import java.util.function.Supplier;

public class DefaultModuleFactory implements Supplier<Module> {
	
	public static final DefaultModuleFactory INSTANCE = new DefaultModuleFactory();
	
	private DefaultModuleFactory() {
	}
	
	@Override
	public Module get() {
		return new Module();
	}
	
}
