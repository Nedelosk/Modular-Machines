package nedelosk.modularmachines.common.modular.module.producer.producer.recipes;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.basic.inventory.ModuleInventory;
import nedelosk.modularmachines.api.modular.module.producer.producer.IModuleProducer;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleProducer extends ModuleInventory implements IModuleProducer {
	
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
	
	public abstract int getSpeed();
	
	public int getBurnTimeTotal(IModular modular)
	{
		ModuleStack<IModuleEngine> engine = ModularUtils.getModuleStackEngine(modular);
		int burnTimeTotal = engine.getModule().getSpeedModifier(engine.getTier()) * getSpeed() / 10;
		return burnTimeTotal + (burnTimeTotal * ModularUtils.getModuleBattery(modular).getSpeedModifier() / 100);
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
	
	@Override
	public IModule buildModule(ItemStack[] stacks) {
		return null;
	}
	
	@Override
	public int getColor() {
		return 16777215;
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

}