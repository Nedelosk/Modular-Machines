package modularmachines.api.components;

import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;

public interface IComponentProvider<C extends IComponent> extends INBTReadable, INBTWritable, INetworkComponent, ICapabilityProvider {
	Collection<C> getComponents();
	
	<T extends C> T addComponent(T component);
	
	void addComponent(C... components);
	
	/* CLASSES */
	Class<?>[] getComponentInterfaces(Class<? extends C> componentClass);
	
	/* INTERFACES*/
	@Nullable
	<T> T getComponent(Class<T> interfaceClass);
	
	<T> Collection<T> getComponents(Class<T> interfaceClass);
	
	boolean hasComponent(final Class<?> interfaceClass);
}
