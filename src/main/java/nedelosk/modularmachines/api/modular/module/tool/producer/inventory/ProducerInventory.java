package nedelosk.modularmachines.api.modular.module.tool.producer.inventory;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.ProducerGui;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerInventory extends ProducerGui implements IProducerInventory {

	public ProducerInventory(String modifier) {
		super(modifier);
	}
	
	public ProducerInventory(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
}
