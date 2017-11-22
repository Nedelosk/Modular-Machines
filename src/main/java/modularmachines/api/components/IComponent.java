package modularmachines.api.components;

public interface IComponent<P extends IComponentProvider> {
	default Class<?>[] getComponentInterfaces() {
		return getProvider().getComponentInterfaces(this.getClass());
	}
	
	void setProvider(P provider);
	
	P getProvider();
	
	default IComponentTag[] getTags() {
		return new IComponentTag[0];
	}
}
