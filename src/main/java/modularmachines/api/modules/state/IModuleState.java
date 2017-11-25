package modularmachines.api.modules.state;

import com.google.common.collect.ImmutableMap;

import java.util.Collection;

import net.minecraft.block.state.IBlockState;

import modularmachines.api.modules.IModule;

public interface IModuleState {
	Collection<IModuleProperty<?>> getPropertyKeys();
	
	/**
	 * Get the value of the given Property for this BlockState
	 */
	<T> T getValue(IModuleProperty<T> property);
	
	/**
	 * Get a version of this ModuleState with the given Property now set to the given value
	 */
	<T, V extends T> IBlockState withProperty(IModuleProperty<T> property, V value);
	
	ImmutableMap<IModuleProperty<?>, ?> getProperties();
	
	boolean isEmpty();
	
	IModule getModule();
}
