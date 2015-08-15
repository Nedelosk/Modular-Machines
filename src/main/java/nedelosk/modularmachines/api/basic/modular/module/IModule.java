package nedelosk.modularmachines.api.basic.modular.module;

import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public interface IModule extends INBTTagable {
	
	String getName();
	
	String[] getTechTreeKeys(int tier);
	
	String getTechTreeKeys();
	
	void setTechTreeKeys(int tier, String... keys);
	
	String getModuleName();
	
	void update(IModular modular);
	
	void readFromNBT(NBTTagCompound nbt, IModular modular);
	
	String getModifier();
	
}
