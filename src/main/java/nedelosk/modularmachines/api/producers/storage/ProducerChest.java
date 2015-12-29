package nedelosk.modularmachines.api.producers.storage;

import java.util.ArrayList;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.inventory.IProducerInventory;
import nedelosk.modularmachines.api.producers.inventory.ProducerInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.api.utils.ModuleUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ProducerChest extends ProducerInventory implements IProducerStorage {

	protected int slots;

	public ProducerChest(String modifier, int slots) {
		super(modifier);
		this.slots = slots;
	}

	public ProducerChest(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {

		ArrayList<Slot> list = new ArrayList<Slot>();
		int i = slots / 9;

		for (int i1 = 0; i1 < i; i1++) {
			for (int l1 = 0; l1 < 9; l1++) {
				list.add(new SlotModular(modular.getMachine(), l1 + i1 * 9 + 9, 8 + l1 * 18, 10 + i1 * 18, stack));
			}
		}
		return list;

	}

	@Override
	public int getGuiTop(IModular modular, ModuleStack stack) {
		if (ModuleUtils.getModuleStackStorage(modular).getType().getTier() != 1)
			return 256;
		else
			return 166;
	}

	@Override
	public ResourceLocation getCustomGui(IModular modular, ModuleStack stack) {
		if (ModuleUtils.getModuleStackStorage(modular).getType().getTier() == 3)
			return new ResourceLocation("modularmachines", "textures/gui/modular_machine_chest.png");
		else
			return null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);

		nbt.setInteger("Slots", slots);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);

		slots = nbt.getInteger("Slots");
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return slots;
	}
	
	@Override
	public boolean transferInput(ModuleStack<IModule, IProducerInventory> stack, IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		return false;
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}

}
