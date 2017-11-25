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
	
	boolean hasComponent(Class<?> componentClass);
	
	<T extends IComponent> T getComponent(Class<T> componentClass);
	
	/* INTERFACES*/
	@Nullable
	<T> T getInterface(Class<T> interfaceClass);
	
	<T> Collection<T> getInterfaces(Class<T> interfaceClass);
}
