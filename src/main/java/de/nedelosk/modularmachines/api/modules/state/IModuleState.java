package de.nedelosk.modularmachines.api.modules.state;

import java.util.List;
import java.util.Map;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleState<M extends IModule> {

	<V> V get(IProperty<V, ? extends NBTBase> property);

	<T, V extends T> IModuleState<M> add(IProperty<T, ? extends NBTBase> property, V value);
	
	IModuleState<M> register(IProperty property);
	
	/**
	 * Finish the registration of the properties.
	 */
	IModuleState<M> createState();

	/**
	 * Add the content handlers to the list.
	 */
	List<IModuleContentHandler> getContentHandlers();

	<C> IModuleContentHandler<C, IModule> getContentHandler(Class<? extends C> contentClass);

	Map<IProperty, Object> getProperties();

	int getIndex();

	void setIndex(int index);

	IModulePage[] getPages();

	M getModule();

	IModular getModular();

	IModuleContainer getContainer();

	void writeToNBT(NBTTagCompound nbt, IModular modular);

	void readFromNBT(NBTTagCompound nbt, IModular modular);

}
