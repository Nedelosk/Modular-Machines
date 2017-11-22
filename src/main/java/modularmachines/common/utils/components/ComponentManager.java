package modularmachines.common.utils.components;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modularmachines.api.components.IComponent;

public enum ComponentManager {
	INSTANCE;
	private final Class<?>[] DEFAULT = new Class<?>[0];
	private final Map<Class<?>, Class<?>[]> componentInterfaceMap = new HashMap<>();
	
	private void registerComponentClass(Class<? extends IComponent> component) {
		if (componentInterfaceMap.containsKey(component)) {
			return;
		}
		Set<Class<?>> interfaces = new HashSet<>();
		for (Class<?> currentClass = component; currentClass != null; currentClass = currentClass.getSuperclass()) {
			Collections.addAll(interfaces, currentClass.getInterfaces());
		}
		interfaces.remove(IComponent.class);
		this.componentInterfaceMap.put(component, interfaces.toArray(new Class[0]));
	}
	
	public Class<?>[] getComponentInterfaces(Class<? extends IComponent> componentClass) {
		if (!this.componentInterfaceMap.containsKey(componentClass)) {
			this.registerComponentClass(componentClass);
		}
		return this.componentInterfaceMap.getOrDefault(componentClass, DEFAULT);
	}
}
