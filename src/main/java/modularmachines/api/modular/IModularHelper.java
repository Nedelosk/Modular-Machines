package modularmachines.api.modular;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerEntity;
import modularmachines.api.modular.handlers.IModularHandlerItem;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.position.StoragePositions;

public interface IModularHelper {

	/* HANDLERS */
	IModularHandlerItem createItemHandler(ItemStack parent, StoragePositions positions);

	/**
	 * You have to set the tileEntity after creating the handler.
	 */
	IModularHandlerTileEntity createTileEntityHandler(StoragePositions positions);

	IModularHandlerEntity createEntityHandler(Entity entity, StoragePositions positions);

	/* MODULARS */
	IModular createModular(IModularHandler modularHandler);

	IModular createModular(IModularHandler modularHandler, NBTTagCompound nbtTag);

	/* ASSEMBLERS */
	IModularAssembler createModularAssembler(IModularHandler modularHandler);

	IModularAssembler createModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag);
}
