package nedelosk.modularmachines.common.machines.module;

import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.basic.Module;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleCasing extends Module implements IModuleCasing {

	public ModuleCasing() {
	}
	
	public ModuleCasing(NBTTagCompound nbt) {
		super(nbt);
	}
	
	public ModuleCasing(String modifier) {
		super(modifier);
	}
	
	@Override
	public String getModuleName() {
		return "Casing";
	}

}
