package de.nedelosk.modularmachines.common.modular;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.IModularHelper;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class ModularHelper implements IModularHelper {

	@Override
	public IModularHandlerItem createItemHandler(ItemStack parent, List<IStoragePosition> positions) {
		return new ModularHandlerItem(parent, positions);
	}

	@Override
	public IModularHandlerTileEntity createTileEntityHandler(TileEntity parent, List<IStoragePosition> positions) {
		return new ModularHandlerTileEntity(parent, positions);
	}

	@Override
	public IModular createModular(IModularHandler modularHandler) {
		return new Modular(modularHandler);
	}

	@Override
	public IModular createModular(IModularHandler modularHandler, NBTTagCompound nbtTag) {
		return new Modular(modularHandler, nbtTag);
	}

	@Override
	public IModularAssembler createModularAssembler(IModularHandler modularHandler) {
		return new ModularAssembler(modularHandler);
	}

	@Override
	public IModularAssembler createModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag) {
		return new ModularAssembler(modularHandler, nbtTag);
	}
}
