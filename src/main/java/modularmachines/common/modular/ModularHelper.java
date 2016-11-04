package modularmachines.common.modular;

import modularmachines.api.modular.IModular;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modular.IModularHelper;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerEntity;
import modularmachines.api.modular.handlers.IModularHandlerItem;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.position.StoragePositions;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModularHelper implements IModularHelper {

	@Override
	public IModularHandlerItem createItemHandler(ItemStack parent, StoragePositions positions) {
		return new ModularHandlerItem(parent, positions);
	}

	@Override
	public IModularHandlerTileEntity createTileEntityHandler(StoragePositions positions) {
		return new ModularHandlerTileEntity(positions);
	}

	@Override
	public IModularHandlerEntity createEntityHandler(Entity entity, StoragePositions positions) {
		return null;
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
