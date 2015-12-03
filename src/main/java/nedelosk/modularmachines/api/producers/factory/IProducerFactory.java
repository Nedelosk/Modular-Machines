package nedelosk.modularmachines.api.producers.factory;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IProducerFactory {

	<P extends IProducer> P createProducer(String name, NBTTagCompound nbt, IModular modular, ModuleStack stack);

	<P extends IProducer> P createProducer(String name);

}
