package nedelosk.modularmachines.common.machines.module.tool.generator;

import nedelosk.modularmachines.api.modular.module.producer.producer.energy.IModuleGenerator;
import nedelosk.modularmachines.common.machines.module.tool.producer.ModuleProducer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleGenerator extends ModuleProducer implements IModuleGenerator {

	public ModuleGenerator(String modifier) {
		super(modifier);
	}
	
	public ModuleGenerator(NBTTagCompound nbt) {
		super(nbt);
	}

	@Override
	public String getModuleName() {
		return "Generator";
	}
	
}
