package nedelosk.modularmachines.common.modular.module.basic.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.module.basic.inventory.ModuleInventory;
import nedelosk.modularmachines.api.modular.module.basic.storage.IModuleStorage;
import nedelosk.modularmachines.api.modular.tier.Tiers;
import nedelosk.modularmachines.api.modular.tier.Tiers.Tier;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class ModuleChest extends ModuleInventory implements IModuleStorage {

	public HashMap<Tier, Integer> slots = Maps.newHashMap();
	
	public ModuleChest() {
	}
	
	public ModuleChest(String modifier) {
			super(modifier);
	}
	
	public void addTier(Tier tier, int slots, String modifier) {
		super.addTier(tier, modifier);
		this.slots.put(tier, slots);
	}
	
	@Override
	public String getModuleName() {
		return "Storage";
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		
		ArrayList<Slot> list = new ArrayList<Slot>();
		int i = slots.get(stack.getTier()) / 9;
		
        for (int i1 = 0; i1 < i; i1++) {
            for (int l1 = 0; l1 < 9; l1++) {
               list.add(new SlotModular(modular.getMachine(), l1 + i1 * 9 + 9, 8 + l1 * 18, 10 + i1 * 18, stack));
            }
        }
        return list;

	}
	
	@Override
	public int getGuiTop(IModular modular) {
		if(ModularUtils.getModuleStackStorage(modular).getTier().getStage() != 1)
			return 256;
		else
			return 166;
	}
	
	@Override
	public ResourceLocation getCustomGui(IModular modular) {
		if(ModularUtils.getModuleStackStorage(modular).getTier().getStage() == 3)
			return new ResourceLocation("modularmachines", "textures/gui/modular_machine_chest.png");
		else
			return null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList list = new NBTTagList();
		for(Entry<Tier, Integer> entry : slots.entrySet()){
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setString("Name", entry.getKey().getName());
			nbtTag.setInteger("Slot", entry.getValue());
			list.appendTag(nbtTag);
		}
		nbt.setTag("Slots", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		NBTTagList list = nbt.getTagList("Slots", 10);
		for(int i = 0;i < list.tagCount();i++){
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			int slot = nbtTag.getInteger("Slot");
			String name = nbtTag.getString("Name");
			slots.put(Tiers.getTier(name), slot);
		}
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return slots.get(stack.getTier());
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

}
