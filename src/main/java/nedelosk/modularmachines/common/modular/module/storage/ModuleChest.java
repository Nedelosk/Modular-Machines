package nedelosk.modularmachines.common.modular.module.storage;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModuleStorage;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.common.inventory.slots.SlotModuleMachine;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ModuleChest extends Module implements IModuleStorage {

	public int slots;
	
	public ModuleChest() {
	}
	
	public ModuleChest(int slots) {
		this.slots = slots;
	}
	
	@Override
	public String getModuleName() {
		return "Storage";
	}

	@Override
	public void addSlots(IContainerBase container, IModular modular) {
		
		int i = slots / 9;
		
        for (int i1 = 0; i1 < i; i1++) {
            for (int l1 = 0; l1 < 9; l1++) {
               container.addSlot(new SlotModuleMachine(modular.getMachine(), l1 + i1 * 9 + 9, 8 + l1 * 18, 10 + i1 * 18, this.getName()));
            }
        }

	}
	
	@Override
	public int getGuiTop(IModular modular) {
		if(modular.getStorage().getTier() == 3)
			return 256;
		else
			return 166;
	}
	
	@Override
	public ResourceLocation getCustomGui(IModular modular) {
		if(modular.getStorage().getTier() == 3)
		return new ResourceLocation("modularmachines", "textures/gui/modular_machine_chest.png");
		else
			return super.getCustomGui(modular);
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@Override
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

}
