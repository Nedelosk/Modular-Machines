package nedelosk.modularmachines.common.modular.module.basic.storage;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.basic.Module;
import nedelosk.modularmachines.api.modular.module.basic.storage.IModuleStorage;
import nedelosk.modularmachines.common.inventory.slots.SlotModuleMachine;
import nedelosk.modularmachines.common.modular.utils.ModularUtils;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ModuleChest extends Module implements IModuleStorage {

	public int slots;
	
	public ModuleChest() {
	}
	
	public ModuleChest(String modifier, int slots) {
			super(modifier);
		this.slots = slots;
	}
	
	@Override
	public String getModuleName() {
		return "Storage";
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		
		ArrayList<Slot> list = new ArrayList<Slot>();
		int i = slots / 9;
		
        for (int i1 = 0; i1 < i; i1++) {
            for (int l1 = 0; l1 < 9; l1++) {
               list.add(new SlotModuleMachine(modular.getMachine(), l1 + i1 * 9 + 9, 8 + l1 * 18, 10 + i1 * 18, this.getName()));
            }
        }
        return list;

	}
	
	@Override
	public int getGuiTop(IModular modular) {
		if(ModularUtils.getModuleStackStorage(modular).getTier() != 1)
			return 256;
		else
			return 166;
	}
	
	@Override
	public ResourceLocation getCustomGui(IModular modular) {
		if(ModularUtils.getModuleStackStorage(modular).getTier() == 3)
		return new ResourceLocation("modularmachines", "textures/gui/modular_machine_chest.png");
		else
			return null;
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("Slots", slots);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		slots = nbt.getInteger("Slots");
	}

	@Override
	public int getSizeInventory() {
		return slots;
	}

}
