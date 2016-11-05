package modularmachines.api.modules.state;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import modularmachines.api.modular.IModular;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.handlers.IMultiModuleContentHandlerProvider;
import modularmachines.api.modules.storage.module.IModuleHandler;
import modularmachines.api.property.IProperty;
import modularmachines.api.property.IPropertyProvider;
import modularmachines.api.property.IPropertyRegistryBuilder;
import modularmachines.api.property.IPropertySetter;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IModuleState<M extends IModule> extends IPropertyProvider, ICapabilityProvider, IPropertySetter<IModuleState<M>>, IPropertyRegistryBuilder, IMultiModuleContentHandlerProvider {

	@Override
	@Nonnull
	<T, V extends T> IModuleState<M> set(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value);

	@Override
	@Nonnull
	IModuleState<M> register(IProperty property);

	/**
	 * Finish the registration of the properties.
	 */
	@Override
	@Nonnull
	IModuleState<M> build();

	int getIndex();

	void setIndex(int index);

	void addPage(@Nonnull IModulePage page);

	@Nonnull
	List<IModulePage> getPages();

	@Nullable
	<P extends IModulePage> P getPage(Class<? extends P> pageClass);

	@Nullable
	IModulePage getPage(String pageID);

	@Nonnull
	M getModule();

	@Nonnull
	IModuleProperties getModuleProperties();

	@Nullable
	IModuleHandler getModuleHandler();

	@Nullable
	IModular getModular();

	@Nonnull
	IModuleContainer getContainer();

	void setProvider(@Nullable IModuleProvider provider);

	@Nullable
	IModuleProvider getProvider();

	@Override
	@Nonnull
	NBTTagCompound serializeNBT();

	@Override
	void deserializeNBT(@Nonnull NBTTagCompound nbt);

	@Nonnull
	String getDisplayName();
}
