package nedelosk.modularmachines.common.machines.module.tool.tool.farm;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.producer.farm.IFarm;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import nedelosk.modularmachines.common.machines.module.tool.tool.ModuleTool;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleFarm extends ModuleTool {

	public IFarm farm;
	
	public ModuleFarm(String modifier, IFarm farm) {
		super(modifier);
		this.farm = farm;
	}
	
	public ModuleFarm(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void update(IModular modular) {
		farm.updateFarm(this, modular);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(farm != null)
			nbt.setString("Farm", farm.getName());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("Farm"))
			farm = ModularManager.getFarm(nbt.getString("Farm"));
		
	}
	
	@Override
	public String getModuleName() {
		return "farm";
	}

}
