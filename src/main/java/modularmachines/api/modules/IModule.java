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

/**
 * It contains the components, the position and module handler of the module.
 */
public interface IModule extends INBTReadable, INBTWritable, IComponentProvider<IModuleComponent>, ICapabilityProvider {
	
	/**
	 * @return The module handler that contains and handles this module.
	 */
	IModuleHandler getHandler();
	
	/**
	 * @return The module container that contains every module.
	 */
	IModuleContainer getContainer();
	
	/**
	 * @return The provider that provides this module.
	 */
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
	 * @return Creates a item that drops if the module was extracted or the block that contains the module container was
	 * destroyed by a player.
	 */
	ItemStack createItem();
	
	/**
	 * @return The internal index of this module. It is generated out of the position of the module and the positions
	 * of the parents.
	 */
	int getIndex();
	
	/**
	 * @return The module data that was used to create this module.
	 */
	IModuleData getData();
	
	/* FACING */
	
	/**
	 * @return The facing of this module.
	 */
	EnumFacing getFacing();
	
	/**
	 * @return The given side relative to the facing of this module.
	 */
	EnumFacing getSide(EnumFacing side);
	
	/**
	 * Returns true if the given side is the facing of the module.
	 */
	boolean isFacing(@Nullable EnumFacing side);
	
	@Deprecated
	default PageComponent getComponent(int index) {
		return null;
	}
	
	/**
	 * Sends the NBT-Data of this module to the client.
	 */
	void sendToClient();
	
	/**
	 * @return If the module is empty and contains the default {@link IModuleData}.
	 */
	boolean isEmpty();
}
