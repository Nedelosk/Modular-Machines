package modularmachines.common.utils.components;

import modularmachines.api.components.IComponent;
import modularmachines.api.components.IComponentProvider;

public class Component<P extends IComponentProvider> implements IComponent<P> {
	protected P provider;
	
	public Component() {
	}
	
	@Override
	public void setProvider(P provider) {
		this.provider = provider;
	}
	
	@Override
	public P getProvider() {
		return provider;
	}
}
