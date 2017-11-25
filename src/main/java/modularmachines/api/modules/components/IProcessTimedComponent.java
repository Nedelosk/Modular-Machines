package modularmachines.api.modules.components;

public interface IProcessTimedComponent extends IModuleComponent {
	int getProcessLength();
	
	float getProgress();
	
	float getProgressPerTick();
}
