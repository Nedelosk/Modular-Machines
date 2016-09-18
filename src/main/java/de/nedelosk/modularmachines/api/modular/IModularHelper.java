package de.nedelosk.modularmachines.api.modular;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface IModularHelper {

	/* HANDLERS */
	IModularHandlerItem createItemHandler(ItemStack parent, List<IStoragePosition> positions);

	IModularHandlerTileEntity createTileEntityHandler(TileEntity parent, List<IStoragePosition> positions);

	/* MODULARS */
	IModular createModular(IModularHandler modularHandler);

	IModular createModular(IModularHandler modularHandler, NBTTagCompound nbtTag);

	/* ASSEMBLERS */
	IModularAssembler createModularAssembler(IModularHandler modularHandler);

	IModularAssembler createModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag);

}
