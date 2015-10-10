package nedelosk.modularmachines.api.modular.module.basic.inventory;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGui;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;

public interface IModuleInventory extends IModuleGui {

	ArrayList<Slot> addSlots(IContainerBase container, IModular modular);
	
	int getSizeInventory();
	
	void onSlotChanced(IModularInventory modular);
	
}
