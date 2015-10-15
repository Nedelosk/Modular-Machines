package nedelosk.modularmachines.api.modular.module.basic.inventory;

import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.module.basic.gui.ModuleGui;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleInventory extends ModuleGui implements IModuleInventory {

	public ModuleInventory() {
		super();
	}
	
	public ModuleInventory(String modifier) {
		super(modifier);
	}
	
	public ModuleInventory(NBTTagCompound nbt) {
		super(nbt);
	}
	
}
