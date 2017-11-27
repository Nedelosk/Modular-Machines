package modularmachines.api.modules.components.process;

import modularmachines.api.modules.components.IModuleComponent;

public interface IProcessTimedComponent extends IModuleComponent {
	int getProcessLength();
	
	float getProgress();
	
	float getProgressPerTick();
}
