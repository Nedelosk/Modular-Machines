package modularmachines.api.modules.components.process;

import javax.annotation.Nullable;

import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.errors.IErrorState;

public interface IProcessCriterion<R> extends INetworkComponent {
	
	R getRequirement();
	
	void setRequirement(R requirement);
	
	boolean getState();
	
	void updateState();
	
	default void work() {
	}
	
	@Nullable
	IErrorState getError();
	
	IProcessComponent getParent();
	
	void markDirty();
	
	void setDirty(boolean dirty);
	
	boolean isDirty();
}
