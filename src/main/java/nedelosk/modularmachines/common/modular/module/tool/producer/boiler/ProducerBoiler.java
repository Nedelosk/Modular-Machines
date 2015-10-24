package nedelosk.modularmachines.common.modular.module.tool.producer.boiler;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachineRecipe;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerBoiler extends ProducerMachineRecipe {

	public ProducerBoiler(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	public ProducerBoiler(String modifier, int inputs, int outputs) {
		super(modifier, inputs, outputs);
	}
	
	public ProducerBoiler(String modifier, int inputs, int outputs, int speed) {
		super(modifier, inputs, outputs, speed);
	}

}
