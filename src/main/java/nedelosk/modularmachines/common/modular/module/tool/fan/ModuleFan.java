package nedelosk.modularmachines.common.modular.module.tool.fan;

import nedelosk.modularmachines.api.basic.modular.module.IModuleFan;
import nedelosk.modularmachines.api.basic.modular.module.Module;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleFan extends Module implements IModuleFan {

	public ModuleFan() {
	}
	
	public ModuleFan(NBTTagCompound nbt) {
		super(nbt);
	}

	@Override
	public String getModuleName() {
		return "fan";
	}

}
