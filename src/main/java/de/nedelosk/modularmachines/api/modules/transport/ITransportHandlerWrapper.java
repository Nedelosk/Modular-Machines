package de.nedelosk.modularmachines.api.modules.transport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface ITransportHandlerWrapper<H> {

	@Nonnull
	H getHandler();

	/**
	 * @return Null if the handler is from a tile entity.
	 */
	@Nullable
	IModulePage getPage();

	/**
	 * @return Null if the handler is from a tile entity.
	 */
	@Nullable
	IModuleState getModuleState();

	/**
	 * @return Null if the handler is from a module.
	 */
	@Nullable
	EnumFacing getFacing();

	/**
	 * @return Null if the handler is from a module.
	 */
	@Nullable
	TileEntity getTileEntity();

	@Nonnull
	ItemStack getTabItem();

	@Nonnull
	String getTabTooltip();
}
