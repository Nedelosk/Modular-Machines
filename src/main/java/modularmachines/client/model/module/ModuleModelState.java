package modularmachines.client.model.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import modularmachines.api.modules.model.IModelProperty;
import modularmachines.api.modules.model.IModuleModelState;

public class ModuleModelState implements IModuleModelState {
	protected final Map<IModelProperty, Boolean> values = new HashMap<>();
	
	@Override
	public void set(IModelProperty property, boolean value) {
		values.put(property, value);
	}
	
	@Override
	public boolean get(IModelProperty property) {
		return values.get(property);
	}
	
	@Override
	public boolean has(IModelProperty property) {
		return values.containsKey(property);
	}
	
	@Override
	public Collection<IModelProperty> getProperties() {
		return values.keySet();
	}
	
	@Override
	public int hashCode() {
		return values.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ModuleModelState)) {
			return false;
		}
		ModuleModelState otherState = (ModuleModelState) obj;
		return values.equals(otherState.values);
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
}
