package modularmachines.api.modules.components;

public interface IActivatableComponent extends IModuleComponent {
	
	void setActive(boolean isActive);
	
	boolean isActive();
}
