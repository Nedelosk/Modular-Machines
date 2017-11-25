package modularmachines.common.modules.components;

import modularmachines.common.modules.components.energy.ProcessComponent;

public class BoilerComponent extends ProcessComponent {
	
	
	@Override
	protected void onFinishTask() {
		super.onFinishTask();
	}
	
	@Override
	public boolean canWork() {
		return super.canWork();
	}
	
	@Override
	public boolean canProgress() {
		return super.canProgress();
	}
	
	@Override
	public int getProcessLength() {
		return 40;
	}
}
