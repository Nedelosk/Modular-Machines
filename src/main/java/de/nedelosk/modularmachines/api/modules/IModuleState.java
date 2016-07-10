package de.nedelosk.modularmachines.api.modules;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.property.IPropertyRegistryBuilder;
import de.nedelosk.modularmachines.api.property.IPropertySetter;
import net.minecraft.nbt.NBTBase;

public interface IModuleState<M extends IModule> extends IPropertyProvider, IPropertySetter<IModuleState<M>>, IPropertyRegistryBuilder {

	@Override
	<T, V extends T> IModuleState<M> set(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value);

	@Override
	IModuleState<M> register(IProperty property);

	/**
	 * Finish the registration of the properties.
	 */
	@Override
	IModuleState<M> build();

	/**
	 * Add the content handlers to the list.
	 */
	List<IModuleContentHandler> getContentHandlers();

	<H extends IModuleContentHandler> H getContentHandler(Class<? extends H> contentClass);

	int getIndex();

	void setIndex(int index);

	List<IModulePage> getPages();

	IModulePage getPage(String pageID);

	M getModule();

	IModular getModular();

	IModuleContainer getContainer();

}
