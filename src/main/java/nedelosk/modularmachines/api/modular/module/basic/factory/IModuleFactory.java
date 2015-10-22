package nedelosk.modularmachines.api.modular.module.basic.factory;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleFactory {
	
	<P extends IProducer> P createProducer(String name, NBTTagCompound nbt, IModular modular, ModuleStack stack);
	
	<P extends IProducer> P createProducer(String name);
	
}
