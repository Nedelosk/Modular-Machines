package modularmachines.api.modules;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import modularmachines.api.components.IComponentProvider;
import modularmachines.api.modules.components.IModuleComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.pages.PageComponent;
import modularmachines.api.modules.positions.IModulePosition;

public interface IModule extends INBTReadable, INBTWritable, IComponentProvider<IModuleComponent>, ICapabilityProvider {
	
	IModuleHandler getParent();
	
	/**
	 * @return The module container that contains every module.
	 */
	IModuleContainer getContainer();
	
	IModuleProvider getProvider();
	
	/**
	 * @return The position of this module at the parent.
	 */
	IModulePosition getPosition();
	
	/**
	 * @return The item that was used to create this module.
	 */
	ItemStack getItemStack();
	
	/**
	 * @return The index of this module.
	 */
	int getIndex();
	
	IModuleData getData();
	
	/* FACING */
	EnumFacing getFacing();
	
	EnumFacing getSide(EnumFacing side);
	
	/**
	 * Returns true if the given side is the facing of the module.
	 */
	boolean isFacing(@Nullable EnumFacing side);
	
	default PageComponent getComponent(int index) {
		return null;
	}
	
	void sendToClient();
}
