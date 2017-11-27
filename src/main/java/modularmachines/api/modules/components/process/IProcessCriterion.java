package modularmachines.api.modules.components.process;

import javax.annotation.Nullable;

import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.errors.IErrorState;

public interface IProcessCriterion extends INetworkComponent {
	
	boolean getState();
	
	void updateState();
	
	@Nullable
	IErrorState getError();
	
	IProcessComponent getParent();
	
	void markDirty();
	
	void setDirty(boolean dirty);
	
	boolean isDirty();
}
