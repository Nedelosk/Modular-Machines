package nedelosk.modularmachines.common.modular.module.tool.producer.storage;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.Modules;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.ProducerManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.module.tool.producer.storage.IProducerStorage;
import nedelosk.modularmachines.api.modular.module.tool.producer.storage.IProducerStorageManager;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerStorageManager extends ProducerManager implements IProducerStorageManager {

	private int storageSlots;

	public ProducerStorageManager() {
		super("StorageManager");
		this.storageSlots = 3;
	}

	public ProducerStorageManager(int storageSlots) {
		super("StorageManager");
		this.storageSlots = storageSlots;
	}

	public ProducerStorageManager(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);

		storageSlots = nbt.getInteger("StorageSlots");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);

		nbt.setInteger("StorageSlots", storageSlots);
	}
	
	@Override
	public boolean transferInput(ModuleStack<IModule, IProducerInventory> stackModule, IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem){
		ModuleStack<IModule, IProducer> stack = ModuleRegistry.getModuleItem(tile.getStackInSlot(slotID - 36));
		if(stack != null && stack.getModule() == Modules.CHEST && stack.getProducer() != null && stack.getProducer() instanceof IProducerStorage){
			if(mergeItemStack(stackItem, slotID, slotID + 1, false, container))
				return true;
		}
		return false;
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		return null;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 3;
	}

	@Override
	public int getColor() {
		return 0xD1CA3D;
	}

}
