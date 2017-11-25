package modularmachines.common.utils.components;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import modularmachines.api.components.IComponent;
import modularmachines.api.components.IComponentProvider;
import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;

public class ComponentProvider<C extends IComponent> implements IComponentProvider<C> {
	private final Map<Class<?>, List<C>> componentInterfaceMap;
	private final Map<Class<?>, C> componentMap;
	
	public ComponentProvider() {
		this.componentInterfaceMap = new LinkedHashMap<>();
		this.componentMap = new LinkedHashMap<>();
	}
	
	@Nullable
	public static IComponentProvider getProvider(@Nullable Object inventory) {
		if (inventory instanceof IComponentProvider) {
			return (IComponentProvider) inventory;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	public static <T extends IComponent> T getInterface(final Class<T> interfaceClass, @Nullable Object inventory) {
		IComponentProvider<T> provider = getProvider(inventory);
		if (provider != null) {
			return provider.getInterface(interfaceClass);
		}
		if (interfaceClass.isInstance(inventory)) {
			return interfaceClass.cast(inventory);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends C> T addComponent(T component) {
		Preconditions.checkNotNull(component, "Can't have a null component!");
		component.setProvider(this);
		this.componentMap.put(component.getClass(), component);
		for (Class<?> inter : component.getComponentInterfaces()) {
			this.componentInterfaceMap.computeIfAbsent(inter, k -> new ArrayList<>()).add(component);
		}
		return component;
	}
	
	@Override
	public void addComponent(C... components) {
		for (C component : components) {
			addComponent(component);
		}
	}
	
	public Collection<C> getComponents() {
		return this.componentMap.values();
	}
	
	public <T extends IComponent> T getComponent(Class<T> componentClass) {
		if (this.hasComponent(componentClass)) {
			return componentClass.cast(this.componentMap.get(componentClass));
		}
		throw new IllegalArgumentException("No component found for " + componentClass);
	}
	
	@Override
	@Nullable
	public <T> T getInterface(final Class<T> interfaceClass) {
		if (this.hasInterface(interfaceClass)) {
			return this.getInterfaces(interfaceClass).get(0);
		}
		for (C component : this.getComponents()) {
			if (interfaceClass.isInstance(component)) {
				return interfaceClass.cast(component);
			}
		}
		return null;
	}
	
	@Override
	public <T> List<T> getInterfaces(final Class<T> interfaceClass) {
		final List<T> interfaces = new ArrayList<>();
		if (!this.hasInterface(interfaceClass)) {
			return interfaces;
		}
		for (C component : this.componentInterfaceMap.get(interfaceClass)) {
			interfaces.add(interfaceClass.cast(component));
		}
		return interfaces;
	}
	
	@Override
	public Class<?>[] getComponentInterfaces(Class<? extends C> interfaceClass) {
		return ComponentManager.INSTANCE.getComponentInterfaces(interfaceClass);
	}
	
	public boolean hasInterface(final Class<?> interfaceClass) {
		return this.componentInterfaceMap.containsKey(interfaceClass);
	}
	
	public boolean hasComponent(Class<?> componentClass) {
		return this.componentMap.containsKey(componentClass);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		for (INBTReadable readable : getInterfaces(INBTReadable.class)) {
			readable.readFromNBT(compound);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for (INBTWritable writable : getInterfaces(INBTWritable.class)) {
			writable.writeToNBT(compound);
		}
		return compound;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
		for (INetworkComponent component : getInterfaces(INetworkComponent.class)) {
			component.writeData(data);
		}
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		for (INetworkComponent component : getInterfaces(INetworkComponent.class)) {
			component.readData(data);
		}
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		for (ICapabilityProvider component : getInterfaces(ICapabilityProvider.class)) {
			if (component.hasCapability(capability, facing)) {
				return true;
			}
		}
		return false;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		for (ICapabilityProvider component : getInterfaces(ICapabilityProvider.class)) {
			T value = component.getCapability(capability, facing);
			if (value != null) {
				return value;
			}
		}
		return null;
	}
}
