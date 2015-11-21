package nedelosk.modularmachines.common.modular.module.tool.producer.machine;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.basic.IMachineMode;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerMachineRecipeMode extends ProducerMachineRecipe {
	
	public ProducerMachineRecipeMode(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	public ProducerMachineRecipeMode(String modifier, int inputs, int outputs, int speed) {
		super(modifier, inputs, outputs, speed);
	}
	
	abstract Class<IMachineMode> getModeClass();
	
}
