package nedelosk.modularmachines.api.modular.module.basic;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public interface IModule extends INBTTagable {
	
	String getName();
	
	String getModuleName();
	
	void update(IModular modular);
	
	void readFromNBT(NBTTagCompound nbt, IModular modular);
	
	String getModifier();
	
}
