package modularmachines.client.model;

import java.util.Collection;
import java.util.Collections;

import modularmachines.api.modules.model.IModelProperty;
import modularmachines.api.modules.model.IModuleModelState;

public enum  EmptyModelState implements IModuleModelState {
	INSTANCE;
	
	@Override
	public void set(IModelProperty property, boolean value) {
	}
	
	@Override
	public boolean get(IModelProperty property) {
		return false;
	}
	
	@Override
	public Collection<IModelProperty> getProperties() {
		return Collections.emptySet();
	}
	
	@Override
	public boolean isEmpty() {
		return true;
	}
}
