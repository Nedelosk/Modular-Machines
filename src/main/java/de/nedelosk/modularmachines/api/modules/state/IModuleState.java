package de.nedelosk.modularmachines.api.modules.state;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.property.IPropertyRegistryBuilder;
import de.nedelosk.modularmachines.api.property.IPropertySetter;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface IModuleState<M extends IModule> extends IPropertyProvider, ICapabilitySerializable<NBTTagCompound>, IPropertySetter<IModuleState<M>>, IPropertyRegistryBuilder {

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

	@Override
	NBTTagCompound serializeNBT();

	@Override
	void deserializeNBT(NBTTagCompound nbt);

}
