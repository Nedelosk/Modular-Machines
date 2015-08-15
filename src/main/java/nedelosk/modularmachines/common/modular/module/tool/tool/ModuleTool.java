package nedelosk.modularmachines.common.modular.module.tool.tool;

import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.modularmachines.api.basic.modular.module.IModuleTool;
import nedelosk.modularmachines.api.basic.modular.module.Module;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleTool extends Module implements IModuleTool {

	public ModuleTool(String modifier) {
		super(modifier);
	}
	
	public ModuleTool(NBTTagCompound nbt) {
		super(nbt);
	}
	
	public int tier;

	@Override
	public void update(IModular modular) {
	}
	
	@Override
	public String getModuleName() {
		return "tool";
	}

}
