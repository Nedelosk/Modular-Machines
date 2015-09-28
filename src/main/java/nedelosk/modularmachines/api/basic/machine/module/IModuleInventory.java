package nedelosk.modularmachines.api.basic.machine.module;

import java.util.ArrayList;

import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;

public interface IModuleInventory extends IModule {

	ArrayList<Slot> addSlots(IContainerBase container, IModular modular);
	
	int getSizeInventory();
	
}
