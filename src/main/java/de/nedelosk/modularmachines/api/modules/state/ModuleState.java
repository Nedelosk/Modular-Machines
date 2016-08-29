package de.nedelosk.modularmachines.api.modules.state;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class ModuleState<M extends IModule> implements IModuleState<M> {

	protected static final PropertyInteger INDEX = new PropertyInteger("index", -1);

	protected Map<IProperty, Object> properties;
	protected final Map<String, IProperty> registeredProperties;
	protected final IModular modular;
	protected final IModuleContainer container;
	protected final List<IModuleContentHandler> contentHandlers;
	protected final List<IModulePage> pages;
	protected ItemStack stack;

	public ModuleState(IModular modular, IModuleContainer container) {
		this.registeredProperties = Maps.newHashMap();
		register(INDEX);

		this.modular = modular;
		this.container = container;
		List<IModulePage> createdPages = container.getModule().createPages(this);
		MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModulePageCreateEvent(this, createdPages));
		this.pages = createdPages;
		this.contentHandlers = container.getModule().createHandlers(this);
	}

	@Override
	public IModuleState<M> build() {
		this.properties = Maps.newHashMap();
		for(IProperty property : registeredProperties.values()){
			properties.put(property, property.getDefaultValue());
		}
		return this;
	}

	@Override
	public IModuleState<M> register(IProperty property) {
		if(properties == null){
			if(!registeredProperties.containsKey(property.getName()) && !registeredProperties.containsValue(property)){
				registeredProperties.put(property.getName(), property);
			}
		}
		return this;
	}

	@Override
	public <T> T get(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property) {
		if(!registeredProperties.containsValue(property)){
			throw new IllegalArgumentException("Cannot get property " + property + " as it is not registred in the module state from the model " + container.getDisplayName());
		}
		if(!properties.containsKey(property)){
			return null;
		}
		return (T) properties.get(property);
	}

	@Override
	public <T, V extends T> IModuleState<M> set(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value) {
		if(!registeredProperties.containsValue(property)){
			throw new IllegalArgumentException("Cannot set property " + property + " as it is not registred in the module state from the model " + container.getDisplayName());
		}
		properties.put(property, value);
		return this;
	}

	@Override
	public List<IModuleContentHandler> getContentHandlers() {
		return contentHandlers;
	}

	@Override
	public <H> H getContentHandler(Class<? extends H> handlerClass) {
		for(IModuleContentHandler handler : contentHandlers){
			if(handlerClass.isAssignableFrom(handler.getClass())){
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
	public IModulePage getPage(String pageID) {
		for(IModulePage page : pages){
			if(page.getPageID().equals(pageID)){
				return page;
			}
		}
		return null;
	}
	
	@Override
	public ItemStack getStack() {
		return stack;
	}
	
	@Override
	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public M getModule() {
		return (M) container.getModule();
	}

	@Override
	public IModuleProperties getModuleProperties() {
		return container.getProperties();
	}

	@Override
	public IModular getModular() {
		return modular;
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
		if(stack != null){
			nbt.setTag("Stack", stack.serializeNBT());
		}
		for(Entry<IProperty, Object> object : properties.entrySet()){
			try{
				if(object.getValue() != null){
					nbt.setTag(object.getKey().getName(), object.getKey().writeToNBT(this, object.getValue()));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		for(IModuleContentHandler handler : contentHandlers){
			if(handler instanceof INBTSerializable){
				nbt.setTag(handler.getUID(), ((INBTSerializable)handler).serializeNBT());
			}
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Stack")){
			NBTTagCompound nbtTag = nbt.getCompoundTag("Stack");
			stack = ItemStack.loadItemStackFromNBT(nbtTag);
		}
		for(IProperty property : registeredProperties.values()){
			try{
				if(nbt.hasKey(property.getName())){
					properties.put(property, property.readFromNBT(nbt.getTag(property.getName()), this));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		for(IModuleContentHandler handler : contentHandlers){
			if(handler instanceof INBTSerializable && nbt.hasKey(handler.getUID())){
				((INBTSerializable)handler).deserializeNBT(nbt.getCompoundTag(handler.getUID()));
			}
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		for(IModuleContentHandler handler : contentHandlers){
			if(handler instanceof ICapabilityProvider){
				if(((ICapabilityProvider)handler).hasCapability(capability, facing)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		for(IModuleContentHandler handler : contentHandlers){
			if(handler instanceof ICapabilityProvider){
				if(((ICapabilityProvider)handler).hasCapability(capability, facing)){
					return ((ICapabilityProvider)handler).getCapability(capability, facing);
				}
			}
		}
		return null;
	}

}
