package modularmachines.common.utils.components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modularmachines.api.components.IComponent;
import modularmachines.api.modules.components.IModuleComponent;

public enum ComponentManager {
	INSTANCE;
	private final Class<?>[] DEFAULT = new Class<?>[0];
	private final Map<Class<?>, Class<?>[]> componentInterfaceMap = new HashMap<>();
	
	private void registerComponentClass(Class<? extends IComponent> component) {
		if (componentInterfaceMap.containsKey(component)) {
			return;
		}
		Set<Class<?>> interfaces = new HashSet<>();
		addInterfaces(component, interfaces);
		interfaces.remove(IComponent.class);
		interfaces.remove(IModuleComponent.class);
		this.componentInterfaceMap.put(component, interfaces.toArray(new Class[0]));
	}
	
	private void addInterfaces(Class<?> interfaceClass, Set<Class<?>> interfaces) {
		for (Class<?> currentClass = interfaceClass; currentClass != null; currentClass = currentClass.getSuperclass()) {
			for (Class<?> interfaceCla : currentClass.getInterfaces()) {
				interfaces.add(interfaceCla);
				addInterfaces(interfaceCla, interfaces);
			}
		}
	}
	
	public Class<?>[] getComponentInterfaces(Class<? extends IComponent> componentClass) {
		if (!this.componentInterfaceMap.containsKey(componentClass)) {
			this.registerComponentClass(componentClass);
		}
		return this.componentInterfaceMap.getOrDefault(componentClass, DEFAULT);
	}
}
