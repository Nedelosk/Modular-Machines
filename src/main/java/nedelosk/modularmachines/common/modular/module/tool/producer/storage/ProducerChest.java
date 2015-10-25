package nedelosk.modularmachines.common.modular.module.tool.producer.storage;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.ProducerInventory;
import nedelosk.modularmachines.api.modular.module.tool.producer.storage.IProducerStorage;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.inventory.Slot;
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
		if(ModularUtils.getModuleStackStorage(modular).getType().getTier() != 1)
			return 256;
		else
			return 166;
	}
	
	@Override
	public ResourceLocation getCustomGui(IModular modular, ModuleStack stack) {
		if(ModularUtils.getModuleStackStorage(modular).getType().getTier() == 3)
			return new ResourceLocation("modularmachines", "textures/gui/modular_machine_chest.png");
		else
			return null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.writeToNBT(nbt, modular, stack);
		
		nbt.setInteger("Slots", slots);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.readFromNBT(nbt, modular, stack);
		
		slots = nbt.getInteger("Slots");
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return slots;
	}
	
	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}

}
