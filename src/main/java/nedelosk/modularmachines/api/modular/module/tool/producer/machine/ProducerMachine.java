package nedelosk.modularmachines.api.modular.module.tool.producer.machine;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.Producer;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerBattery;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerMachine extends Producer implements IProducerMachine {
	
	public ProducerMachine(String modifier) {
		super(modifier);
	}
	
	public ProducerMachine(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	private int burnTime, burnTimeTotal, timer, timerTotal = 0;

	@Override
	public int getBurnTimeTotal(IModular modular, ModuleStack stack)
	{
		ModuleStack<IModule, IProducerEngine> engine = ModularUtils.getModuleStackEngine(modular);
		int burnTimeTotal = engine.getProducer().getSpeedModifier(engine.getTier().getStage()) * getSpeed(stack) / 10;
		ModuleStack<IModule, IProducerBattery> battery = ModularUtils.getModuleStackBattery(modular);
		return burnTimeTotal + (burnTimeTotal * battery.getProducer().getSpeedModifier() / 100);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.writeToNBT(nbt, modular, stack);
		
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("burnTimeTotal", burnTimeTotal);
		nbt.setInteger("timer", timer);
		nbt.setInteger("timerTotal", timerTotal);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.readFromNBT(nbt, modular, stack);
		
		burnTime = nbt.getInteger("burnTime");
		burnTimeTotal = nbt.getInteger("burnTimeTotal");
		timer = nbt.getInteger("timer");
		timerTotal = nbt.getInteger("timerTotal");
	}

	@Override
	public int getBurnTime(ModuleStack stack) {
		return burnTime;
	}

	@Override
	public int getBurnTimeTotal(ModuleStack stack) {
		return burnTimeTotal;
	}

}