package nedelosk.modularmachines.common.modular.module.tool.tool;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.farm.IFarm;
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
			farm = ModularMachinesApi.getFarm(nbt.getString("Farm"));
		
	}
	
	@Override
	public String getModuleName() {
		return "farm";
	}

}
