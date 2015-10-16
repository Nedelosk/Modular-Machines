package nedelosk.modularmachines.common.modular.module.tool.producer.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerBattery;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.ProducerInventory;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachine;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerMachine extends ProducerInventory implements IProducerMachine {
	
	public int burnTime, burnTimeTotal;
	public int timer, timerTotal;
	
	public ProducerMachine(String modifier) {
		super(modifier);
	}
	
	public ProducerMachine(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@Override
	public int getBurnTime(ModuleStack stack) {
		return burnTime;
	}
	
	@Override
	public int getBurnTimeTotal(ModuleStack stack) {
		return burnTimeTotal;
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
	public int getBurnTimeTotal(IModular modular, ModuleStack stack)
	{
		ModuleStack<IModule, IProducerEngine> engine = ModularUtils.getModuleStackEngine(modular);
		int burnTimeTotal = engine.getProducer().getSpeedModifier(engine.getTier().getStage()) * getSpeed(stack) / 10;
		ModuleStack<IModule, IProducerBattery> battery = ModularUtils.getModuleStackBattery(modular);
		return burnTimeTotal + (burnTimeTotal * battery.getProducer().getSpeedModifier() / 100);
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
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		
	}

}