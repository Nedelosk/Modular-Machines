package modularmachines.common.utils.components;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private final Set<C> components;
	
	public ComponentProvider() {
		this.componentInterfaceMap = new LinkedHashMap<>();
		this.components = new HashSet<>();
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
			return provider.getComponent(interfaceClass);
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
		this.components.add(component);
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
		return components;
	}
	
	@Override
	@Nullable
	public <T> T getComponent(final Class<T> interfaceClass) {
		if (this.hasComponent(interfaceClass)) {
			return this.getComponents(interfaceClass).get(0);
		}
		for (C component : this.getComponents()) {
			if (interfaceClass.isInstance(component)) {
				return interfaceClass.cast(component);
			}
		}
		return null;
	}
	
	@Override
	public <T> List<T> getComponents(final Class<T> interfaceClass) {
		final List<T> interfaces = new ArrayList<>();
		if (!this.hasComponent(interfaceClass)) {
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
	
	public boolean hasComponent(final Class<?> interfaceClass) {
		return this.componentInterfaceMap.containsKey(interfaceClass);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		for (INBTReadable readable : getComponents(INBTReadable.class)) {
			readable.readFromNBT(compound);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for (INBTWritable writable : getComponents(INBTWritable.class)) {
			writable.writeToNBT(compound);
		}
		return compound;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
		for (INetworkComponent component : getComponents(INetworkComponent.class)) {
			component.writeData(data);
		}
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		for (INetworkComponent component : getComponents(INetworkComponent.class)) {
			component.readData(data);
		}
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		for (ICapabilityProvider component : getComponents(ICapabilityProvider.class)) {
			if (component.hasCapability(capability, facing)) {
				return true;
			}
		}
		return false;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		for (ICapabilityProvider component : getComponents(ICapabilityProvider.class)) {
			T value = component.getCapability(capability, facing);
			if (value != null) {
				return value;
			}
		}
		return null;
	}
}
