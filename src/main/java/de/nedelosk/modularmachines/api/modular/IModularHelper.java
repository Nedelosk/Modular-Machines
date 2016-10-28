package de.nedelosk.modularmachines.api.modular;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerEntity;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.position.StoragePositions;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
