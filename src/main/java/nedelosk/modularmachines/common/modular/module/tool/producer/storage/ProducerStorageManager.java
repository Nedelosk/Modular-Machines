package nedelosk.modularmachines.common.modular.module.tool.producer.storage;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.ProducerManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.storage.IProducerStorageManager;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerStorageManager extends ProducerManager implements IProducerStorageManager {
	
	public ProducerStorageManager(String modifier, Side side) {
		super(modifier, side);
	}
	
	public ProducerStorageManager(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		return null;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 0;
	}

}
