package nedelosk.modularmachines.common.modular.module.tool.producer.farm;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.farm.IFarm;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachine;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerFarm extends ProducerMachine {

	public IFarm farm;
	
	public ProducerFarm(String modifier, IFarm farm) {
		super(modifier);
		this.farm = farm;
	}
	
	public ProducerFarm(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		farm.updateFarm(stack, modular);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.writeToNBT(nbt, modular, stack);
		if(farm != null)
			nbt.setString("Farm", farm.getName());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception{
		super.readFromNBT(nbt, modular, stack);
		if(nbt.hasKey("Farm"))
			farm = ModuleRegistry.getFarm(nbt.getString("Farm"));
		
	}

}
