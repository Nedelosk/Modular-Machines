package nedelosk.modularmachines.common.modular.module.producer.producer.energy;

import nedelosk.modularmachines.api.modular.module.producer.producer.energy.IModuleGenerator;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.ModuleProducer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleGenerator extends ModuleProducer implements IModuleGenerator {

	public ModuleGenerator(String modifier) {
		super(modifier);
	}
	
	public ModuleGenerator(NBTTagCompound nbt) {
		super(nbt);
	}
	
}
