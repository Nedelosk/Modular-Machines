package modularmachines.common.compat;

public interface ICompatPlugin {
	
	default void preInit() {
	}
	
	default void init() {
	}
	
	default void postInit() {
	}
}
