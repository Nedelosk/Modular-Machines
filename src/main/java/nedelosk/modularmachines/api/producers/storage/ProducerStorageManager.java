package nedelosk.modularmachines.api.producers.storage;

import java.util.ArrayList;

import nedelosk.forestcore.api.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.Modules;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.inventory.IProducerInventory;
import nedelosk.modularmachines.api.producers.managers.ProducerManager;
import nedelosk.modularmachines.api.producers.managers.storage.IProducerStorageManager;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
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
