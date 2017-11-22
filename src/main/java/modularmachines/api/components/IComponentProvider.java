package modularmachines.api.components;

import javax.annotation.Nullable;
import java.util.Collection;

import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;

public interface IComponentProvider<C extends IComponent> extends INBTReadable, INBTWritable, INetworkComponent {
	Collection<C> getComponents();
	
	void addComponent(C component);
	
	/* CLASSES */
	Class<?>[] getComponentInterfaces(Class<? extends C> componentClass);
	
	boolean hasComponent(Class<?> componentClass);
	
	<T extends IComponent> T getComponent(Class<T> componentClass);
	
	/* INTERFACES*/
	@Nullable
	<T> T getInterface(Class<T> interfaceClass);
	
	<T> Collection<T> getInterfaces(Class<T> interfaceClass);
	
	/* TAGS */
	@Nullable
	<T> T getComponent(IComponentTag tag);
	
	<T> Collection<T> getComponents(IComponentTag tag);
	
	boolean hasComponent(IComponentTag tag);
}
