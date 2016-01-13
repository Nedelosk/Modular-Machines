package nedelosk.modularmachines.api.producers;

import java.util.List;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IProducer {

	void updateServer(IModular modular, ModuleStack stack);

	void updateClient(IModular modular, ModuleStack stack);

	void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception;

	void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception;

	String getName(ModuleStack stack);

	String getModifier(ModuleStack stack);

	List<String> getRequiredModules();

	boolean onBuildModular(IModular modular, ModuleStack stack, List<String> moduleNames);
}
