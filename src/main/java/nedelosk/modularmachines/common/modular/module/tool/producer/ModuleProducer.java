package nedelosk.modularmachines.common.modular.module.tool.producer;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModuleProducer;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.recipes.NeiStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleProducer extends Module implements IModuleProducer {
	
	public int burnTime, burnTimeTotal;
	public int timer, timerTotal;
	
	public ModuleProducer(String modifier) {
		super(modifier);
	}
	
	
	public ModuleProducer(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public int getBurnTime() {
		return burnTime;
	}
	
	@Override
	public int getBurnTimeTotal() {
		return burnTimeTotal;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("burnTimeTotal", burnTimeTotal);
		nbt.setInteger("timer", timer);
		nbt.setInteger("timerTotal", timerTotal);
	}
	
	public abstract int getSpeedModifier();
	
	public int getBurnTimeTotal(IModular modular)
	{
		int burnTimeTotal = modular.getEngine().getSpeedModifier() * getSpeedModifier() / 10;
		return burnTimeTotal + (burnTimeTotal * modular.getEnergyManger().getSpeedModifier() / 100);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		burnTime = nbt.getInteger("burnTime");
		burnTimeTotal = nbt.getInteger("burnTimeTotal");
		timer = nbt.getInteger("timer");
		timerTotal = nbt.getInteger("timerTotal");
	}
	
	@Override
	public String getModuleName() {
		return "Producer";
	}
	
	@Override
	public ArrayList<NeiStack> addNEIStacks() {
		return null;
	}

}
