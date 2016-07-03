package de.nedelosk.modularmachines.api.modules.state;

import java.util.List;
import java.util.Map;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleState<M extends IModule> extends IPropertyProvider {
	
	<T, V extends T> IModuleState<M> add(IProperty<T, ? extends NBTBase> property, V value);

	IModuleState<M> register(IProperty property);
	
	/**
	 * Finish the registration of the properties.
	 */
	IModuleState<M> createProvider();

	/**
	 * Add the content handlers to the list.
	 */
	List<IModuleContentHandler> getContentHandlers();

	<C> IModuleContentHandler<C, IModule> getContentHandler(Class<? extends C> contentClass);

	int getIndex();

	void setIndex(int index);

	List<IModulePage> getPages();

	IModulePage getPage(String pageID);

	M getModule();

	IModular getModular();

	IModuleContainer getContainer();
	
	void writeToNBT(NBTTagCompound nbt);

	void readFromNBT(NBTTagCompound nbt);

}
