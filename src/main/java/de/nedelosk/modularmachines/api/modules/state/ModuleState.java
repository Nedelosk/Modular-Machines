package de.nedelosk.modularmachines.api.modules.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleHandler;
import de.nedelosk.modularmachines.api.modules.storage.module.ModuleHandler;
import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class ModuleState<M extends IModule> implements IModuleState<M> {

	protected static final PropertyInteger INDEX = new PropertyInteger("index", -1);
	protected Map<IProperty, Object> properties;
	protected final Map<String, IProperty> registeredProperties;
	protected final IModuleHandler moduleHandler;
	protected final IModuleContainer<M, IModuleProperties> container;
	protected final List<IModuleContentHandler> contentHandlers;
	protected final List<IModulePage> pages;
	protected IModuleProvider provider;

	public ModuleState(IModuleProvider provider, IModuleContainer container) {
		this.registeredProperties = Maps.newHashMap();
		register(INDEX);
		this.provider = provider;
		this.container = container;
		this.pages = container.getModule().createPages(this);
		this.contentHandlers = container.getModule().createHandlers(this);
		if (provider != null && provider.getModular() != null) {
			this.moduleHandler = new ModuleHandler(provider.getModular(), this);
		} else {
			this.moduleHandler = null;
		}
	}

	@Override
	public IModuleState<M> build() {
		this.properties = Maps.newHashMap();
		for(IProperty property : registeredProperties.values()) {
			properties.put(property, property.getDefaultValue());
		}
		return this;
	}

	@Override
	public IModuleState<M> register(IProperty property) {
		if (properties == null) {
			if (!registeredProperties.containsKey(property.getName()) && !registeredProperties.containsValue(property)) {
				registeredProperties.put(property.getName(), property);
			}
		}
		return this;
	}

	@Override
	public <T> T get(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property) {
		if (!registeredProperties.containsValue(property)) {
			throw new IllegalArgumentException("Cannot get property " + property + " as it is not registred in the module state from the model " + container.getDisplayName());
		}
		if (!properties.containsKey(property)) {
			return null;
		}
		return (T) properties.get(property);
	}

	@Override
	public <T, V extends T> IModuleState<M> set(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value) {
		if (!registeredProperties.containsValue(property)) {
			throw new IllegalArgumentException("Cannot set property " + property + " as it is not registred in the module state from the model " + container.getDisplayName());
		}
		properties.put(property, value);
		return this;
	}

	@Override
	public <P extends IModulePage> P getPage(Class<? extends P> pageClass) {
		for(IModulePage page : pages) {
			if (pageClass.isAssignableFrom(page.getClass())) {
				return (P) page;
			}
		}
		return null;
	}

	@Override
	public List<IModuleContentHandler> getContentHandlers() {
		return contentHandlers;
	}

	@Override
	public <H> H getContentHandler(Class<? extends H> handlerClass) {
		for(IModuleContentHandler handler : contentHandlers) {
			if (handlerClass.isAssignableFrom(handler.getClass())) {
				return (H) handler;
			}
		}
		return null;
	}

	@Override
	public List<IModuleContentHandler> getAllContentHandlers() {
		List<IModuleContentHandler> contentHandlers = new ArrayList<>();
		contentHandlers.addAll(getContentHandlers());
		for(IModulePage page : pages) {
			contentHandlers.addAll(page.getContentHandlers());
		}
		return contentHandlers;
	}
	
	@Override
	public <H> H getContentHandlerFromAll(Class<? extends H> handlerClass) {
		for(IModuleContentHandler handler : getAllContentHandlers()) {
			if (handlerClass.isAssignableFrom(handler.getClass())) {
				return (H) handler;
			}
		}
		return null;
	}

	@Override
	public Map<IProperty, Object> getProperties() {
		return properties;
	}

	@Override
	public Map<String, IProperty> getRegisteredProperties() {
		return registeredProperties;
	}

	@Override
	public int getIndex() {
		return get(INDEX);
	}

	@Override
	public void setIndex(int index) {
		set(INDEX, index);
	}

	@Override
	public List<IModulePage> getPages() {
		return pages;
	}

	@Override
	public void addPage(IModulePage page) {
		if (page == null || provider == null) {
			return;
		}
		if (getPage(page.getPageID()) != null) {
			return;
		}
		pages.add(page);
	}

	@Override
	public IModulePage getPage(String pageID) {
		if (pageID == null) {
			return null;
		}
		for(IModulePage page : pages) {
			if (page.getPageID().equals(pageID)) {
				return page;
			}
		}
		return null;
	}

	@Override
	public M getModule() {
		return container.getModule();
	}

	@Override
	public IModuleProperties getModuleProperties() {
		return container.getProperties();
	}

	@Override
	public IModuleHandler getModuleHandler() {
		return moduleHandler;
	}

	@Override
	public void setProvider(IModuleProvider provider) {
		this.provider = provider;
	}

	@Override
	public IModuleProvider getProvider() {
		return provider;
	}

	@Override
	public IModular getModular() {
		if (provider == null) {
			return null;
		}
		return provider.getModular();
	}

	@Override
	public IModuleContainer getContainer() {
		return container;
	}

	@Override
	public String toString() {
		return container.getDisplayName() + ": " + properties.toString();
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		for(Entry<IProperty, Object> object : properties.entrySet()) {
			try {
				if (object.getValue() != null) {
					nbt.setTag(object.getKey().getName(), object.getKey().writeToNBT(this, object.getValue()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(IModuleContentHandler handler : contentHandlers) {
			if (handler instanceof INBTSerializable) {
				nbt.setTag(handler.getUID(), ((INBTSerializable) handler).serializeNBT());
			}
		}
		for(IModulePage page : pages) {
			nbt.setTag(page.getPageID(), page.serializeNBT());
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		for(IProperty property : registeredProperties.values()) {
			try {
				if (nbt.hasKey(property.getName())) {
					properties.put(property, property.readFromNBT(nbt.getTag(property.getName()), this));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(IModuleContentHandler handler : contentHandlers) {
			if (handler instanceof INBTSerializable && nbt.hasKey(handler.getUID())) {
				((INBTSerializable) handler).deserializeNBT(nbt.getCompoundTag(handler.getUID()));
			}
		}
		for(IModulePage page : pages) {
			page.deserializeNBT(nbt.getCompoundTag(page.getPageID()));
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		for(IModuleContentHandler handler : contentHandlers) {
			if (handler instanceof ICapabilityProvider) {
				if (((ICapabilityProvider) handler).hasCapability(capability, facing)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		for(IModuleContentHandler handler : contentHandlers) {
			if (handler instanceof ICapabilityProvider) {
				if (((ICapabilityProvider) handler).hasCapability(capability, facing)) {
					return ((ICapabilityProvider) handler).getCapability(capability, facing);
				}
			}
		}
		return null;
	}

}
