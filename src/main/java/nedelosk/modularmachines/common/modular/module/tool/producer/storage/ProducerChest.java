package nedelosk.modularmachines.common.modular.module.tool.producer.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.ProducerInventory;
import nedelosk.modularmachines.api.modular.module.tool.producer.storage.IProducerStorage;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class ProducerChest extends ProducerInventory implements IProducerStorage {

	public HashMap<Type, Integer> slots = Maps.newHashMap();
	
	public ProducerChest(String modifier) {
			super(modifier);
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		
		ArrayList<Slot> list = new ArrayList<Slot>();
		int i = slots.get(stack.getType()) / 9;
		
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
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.writeToNBT(nbt, modular, stack);
		
		NBTTagList list = new NBTTagList();
		for(Entry<Type, Integer> entry : slots.entrySet()){
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setString("Name", entry.getKey().getName());
			nbtTag.setInteger("Slot", entry.getValue());
			list.appendTag(nbtTag);
		}
		nbt.setTag("Slots", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.readFromNBT(nbt, modular, stack);
		
		NBTTagList list = nbt.getTagList("Slots", 10);
		for(int i = 0;i < list.tagCount();i++){
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			int slot = nbtTag.getInteger("Slot");
			String name = nbtTag.getString("Name");
			slots.put(Types.getType(name), slot);
		}
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return slots.get(stack.getType());
	}
	
	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}

}
