package modularmachines.api.components;

public interface IComponent<P extends IComponentProvider> {
	default Class<?>[] getComponentInterfaces() {
		return getProvider().getComponentInterfaces(this.getClass());
	}
	
	/**
	 * @return The provider that provides this component.
	 */
	P getProvider();
	
	void setProvider(P provider);
}
