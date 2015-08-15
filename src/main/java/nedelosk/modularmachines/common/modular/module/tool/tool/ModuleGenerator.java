package nedelosk.modularmachines.common.modular.module.tool.tool;

import nedelosk.modularmachines.api.basic.modular.module.IModuleGenerator;
import nedelosk.modularmachines.common.modular.module.tool.producer.ModuleProducer;
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
		return "generator";
	}
	
}
