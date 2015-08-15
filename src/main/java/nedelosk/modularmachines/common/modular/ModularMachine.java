package nedelosk.modularmachines.common.modular;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.modularmachines.api.basic.modular.IModularTileEntity;
import nedelosk.modularmachines.api.basic.modular.module.IModuleCasing;
import nedelosk.modularmachines.api.basic.modular.module.IModuleEngine;
import nedelosk.modularmachines.api.basic.modular.module.IModuleGenerator;
import nedelosk.modularmachines.api.basic.modular.module.IModuleProducer;
import nedelosk.modularmachines.api.basic.modular.module.IModuleStorage;
import nedelosk.modularmachines.api.basic.modular.module.IModuleTank;
import nedelosk.modularmachines.api.basic.modular.module.ModuleStack;
import nedelosk.modularmachines.api.basic.modular.module.energy.IModuleBattery;
import nedelosk.modularmachines.api.basic.modular.module.energy.IModuleCapacitor;
import nedelosk.modularmachines.api.basic.modular.module.manager.IModuleEnergyManager;
import nedelosk.modularmachines.api.basic.modular.module.manager.IModuleTankManager;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.modular.handler.EnergyHandler;
import nedelosk.modularmachines.common.modular.handler.FluidHandler;
import nedelosk.modularmachines.common.modular.module.ModuleTank;
import nedelosk.modularmachines.common.modular.module.energy.ModuleBattery;
import nedelosk.modularmachines.common.modular.module.energy.ModuleCapacitor;
import nedelosk.modularmachines.common.modular.module.energy.ModuleEngine;
import nedelosk.modularmachines.common.modular.module.manager.ModuleEnergyManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleTankManager;
import nedelosk.modularmachines.common.modular.module.tool.tool.ModuleGenerator;
import nedelosk.nedeloskcore.api.INBTTagable;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class ModularMachine implements INBTTagable, IModular, IEnergyHandler {
	
	public ArrayList<ModuleStack> modules = new ArrayList<ModuleStack>();
	public EnergyHandler energyHandler;
	public FluidHandler fluidHandler;
	public TileModularMachine machine;
	
	public ModularMachine() {
	}
	
	public ModularMachine(NBTTagCompound nbt, TileModularMachine tile) {
		readFromNBT(nbt);
		machine = tile;
	}
	
	public void update()
	{
		for(ModuleStack module : modules)
			module.getModule().update(this);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		   if(nbt.hasKey("FluidHandler"))
			   fluidHandler = new FluidHandler(nbt, this);
		   
		   energyHandler = new EnergyHandler(nbt);
		   
		   NBTTagList listTag = nbt.getTagList("Modules", 10);
		   for(int i = 0;i < listTag.tagCount();i++)
		   {
			   if(listTag != null)
				   modules.add(ModuleStack.loadStackFromNBT(listTag.getCompoundTagAt(i), this));
		   }
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {		
		NBTTagList listTag = new NBTTagList();
		for(ModuleStack stack : modules)
		{
			if(stack != null)
			{
			NBTTagCompound nbtTag = new NBTTagCompound();
			stack.writeToNBT(nbtTag);
			listTag.appendTag(nbtTag);
			}
		}
		nbt.setTag("Modules", listTag);
		
		if(energyHandler != null)
			energyHandler.writeToNBT(nbt);
		
		if(fluidHandler != null)
			fluidHandler.writeToNBT(nbt);
	}
	
	public static ModularMachine crateModularMachine(ArrayList<ModuleStack> modules)
	{
		ModularMachine machine = new ModularMachine();
		machine.modules = modules;
		for(ModuleStack module : modules)
		{
		if(module.getModule() instanceof IModuleBattery)
		{
		machine.energyHandler = new EnergyHandler(new EnergyStorage(machine.getBattery().getMaxEnergyStored()));
		}
		else if(module.getModule() instanceof IModuleTankManager)
		{
			if(machine.getTanks() != null)
			{
				int i = 0;
				FluidTankNedelosk tanks[] = new FluidTankNedelosk[machine.getTanks().length];
				for(ModuleTank tank : machine.getTanks())
				{
					tanks[i] = new FluidTankNedelosk(tank.getCapacity());
					i++;
				}
				machine.fluidHandler = new FluidHandler(machine, tanks);
			}
		}
		}
		return machine;
	}
	

	@Override
	public ModuleCapacitor[] getCapacitors() {
		List<IModuleCapacitor> capacitors = new ArrayList<IModuleCapacitor>();
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleCapacitor)
				capacitors.add((IModuleCapacitor) stack.getModule());
		}
		
		if(capacitors.size() != 0)
		{
		return capacitors.toArray(new ModuleCapacitor[capacitors.size()]);
		}
		return null;
	}

	@Override
	public ModuleTank[] getTanks() {
		List<IModuleTank> tanks = new ArrayList<IModuleTank>();
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleTank)
				tanks.add((IModuleTank) stack.getModule());
		}
		
		if(tanks.size() != 0)
		{
		return tanks.toArray(new ModuleTank[tanks.size()]);
		}
		return null;
	}
	
	@Override
	public ModuleGenerator getGenerator()
	{
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleGenerator)
				return (ModuleGenerator) stack.getModule();
		}
		return null;
	}
	
	@Override
	public ModuleBattery getBattery()
	{
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleBattery)
				return (ModuleBattery) stack.getModule();
		}
		return null;
	}
	

	@Override
	public ModuleStack getProducer() {
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleProducer)
				return stack;
		}
		return null;
	}
	
	@Override
	public ModuleEngine getEngine()
	{
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleEngine)
				return (ModuleEngine) stack.getModule();
		}
		return null;
	}
	

	@Override
	public ModuleStack getStorage() {
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleStorage)
				return stack;
		}
		return null;
	}

	public IEnergyHandler getEnergyHandler() {
		return energyHandler;
	}

	@Override
	public IFluidHandler getFluidHandler() {
		return fluidHandler;
	}

	@Override
	public int getTier() {
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleCasing)
				return stack.getTier();
		}
		return 0;
	}

	@Override
	public ArrayList<ModuleStack> getModules() {
		return modules;
	}

	@Override
	public IModularTileEntity getMachine() {
		return machine;
	}

	@Override
	public IModuleEnergyManager getEnergyManger() {
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleEnergyManager)
				return (ModuleEnergyManager) stack.getModule();
		}
		return null;
	}

	@Override
	public IModuleTankManager getTankManeger() {
		for(ModuleStack stack : modules)
		{
			if(stack.getModule() instanceof IModuleTankManager)
				return (ModuleTankManager) stack.getModule();
		}
		return null;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return energyHandler.canConnectEnergy(from);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return energyHandler.receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return energyHandler.extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energyHandler.getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energyHandler.getMaxEnergyStored(from);
	}

}
