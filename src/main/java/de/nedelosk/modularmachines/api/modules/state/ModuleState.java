package de.nedelosk.modularmachines.api.modules.state;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleState<M extends IModule> implements IModuleState<M> {

	protected static final PropertyInteger INDEX = new PropertyInteger("index");
	
	protected final Map<IProperty, Object> properties;
	protected final IModular modular;
	protected final M module;
	protected final IModuleContainer container;
	protected final List<IModuleContentHandler> contentHandlers;
	protected final IModulePage[] pages;
	
	public ModuleState(IModular modular, M module, IModuleContainer container) {
		this.properties = Maps.newHashMap();
		
		this.modular = modular;
		this.module = module;
		this.container = container;
		this.pages = module.createPages(this);
		this.contentHandlers = module.createContentHandlers(this);
	}
	
	@Override
	public <T> T get(IProperty<T, ? extends NBTBase> property) {
		if(!properties.containsKey(property)){
			return null;
		}
		return (T) properties.get(property);
	}

	@Override
	public <T, V extends T> IModuleState<M> add(IProperty<T, ? extends NBTBase> property, V value) {
		properties.put(property, value);
		return this;
	}
	
	@Override
	public List<IModuleContentHandler> getContentHandlers() {
		return contentHandlers;
	}
	
	@Override
	public <C> IModuleContentHandler<C, IModule> getContentHandler(Class<? extends C> contentClass) {
		for(IModuleContentHandler handler : contentHandlers){
			if(handler.getClass() == contentClass){
				return handler;
			}
		}
		return null;
	}

	@Override
	public Map<IProperty, Object> getProperties() {
		return properties;
	}

	@Override
	public int getIndex() {
		return get(INDEX);
	}

	@Override
	public void setIndex(int index) {
		add(INDEX, index);
	}

	@Override
	public IModulePage[] getPages() {
		return pages;
	}

	@Override
	public M getModule() {
		return module;
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
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		for(Entry<IProperty, Object> object : properties.entrySet()){
			nbt.setTag(object.getKey().getName(), object.getKey().writeToNBT(this, object));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		Set<IProperty> properties2 = properties.keySet();
		for(IProperty property : properties2){
			properties.put(property, property.readFromNBT(nbt.getTag(property.getName()), this));
		}
	}

}
