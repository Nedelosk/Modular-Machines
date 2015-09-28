package nedelosk.modularmachines.common.machines.modular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.basic.machine.IModularTileEntity;
import nedelosk.modularmachines.api.basic.machine.manager.IModularUtilsManager;
import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.modular.IModularMachine;
import nedelosk.modularmachines.api.basic.machine.module.IModule;
import nedelosk.modularmachines.api.basic.machine.module.IModuleCasing;
import nedelosk.modularmachines.api.basic.machine.module.IModuleEngine;
import nedelosk.modularmachines.api.basic.machine.module.IModuleGenerator;
import nedelosk.modularmachines.api.basic.machine.module.IModuleProducer;
import nedelosk.modularmachines.api.basic.machine.module.IModuleStorage;
import nedelosk.modularmachines.api.basic.machine.module.IModuleTank;
import nedelosk.modularmachines.api.basic.machine.module.ModuleStack;
import nedelosk.modularmachines.api.basic.machine.module.energy.IModuleBattery;
import nedelosk.modularmachines.api.basic.machine.module.energy.IModuleCapacitor;
import nedelosk.modularmachines.api.basic.machine.module.manager.IModuleEnergyManager;
import nedelosk.modularmachines.api.basic.machine.module.manager.IModuleTankManager;
import nedelosk.modularmachines.common.machines.handler.EnergyHandler;
import nedelosk.modularmachines.common.machines.handler.FluidHandler;
import nedelosk.modularmachines.common.machines.manager.ModularUtilsManager;
import nedelosk.modularmachines.common.machines.module.ModuleTank;
import nedelosk.modularmachines.common.machines.module.energy.ModuleBattery;
import nedelosk.modularmachines.common.machines.module.energy.ModuleCapacitor;
import nedelosk.modularmachines.common.machines.module.energy.ModuleEngine;
import nedelosk.modularmachines.common.machines.module.manager.ModuleEnergyManager;
import nedelosk.modularmachines.common.machines.module.manager.ModuleTankManager;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.IEnergyHandler;

public abstract class Modular implements IModular {
	
	protected HashMap<String, Vector<ModuleStack>> modules = Maps.newHashMap();
	protected IModularTileEntity machine;
	protected IModularUtilsManager utils;
	
	public Modular() {
	}
	
	public Modular(NBTTagCompound nbt, IModularTileEntity machine) {
		readFromNBT(nbt);
		this.machine = machine;
	}
	
	public void update()
	{
		for(Vector<ModuleStack> moduleV : modules.values())
			for(ModuleStack module : moduleV)
				module.getModule().update(this);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		   
		   NBTTagList listTag = nbt.getTagList("Modules", 10);
		   for(int i = 0;i < listTag.tagCount();i++)
		   {
			   NBTTagCompound modulesTag = listTag.getCompoundTagAt(i);
			   NBTTagList modulesTagList = modulesTag.getTagList("modules", 10);
			   String key = modulesTag.getString("key");
			   Vector<ModuleStack> moduleV = new Vector<ModuleStack>();
			   for(int r = 0;r < modulesTagList.tagCount();r++){
				   ModuleStack stack = ModuleStack.loadStackFromNBT(modulesTagList.getCompoundTagAt(r), this);
				   moduleV.add(stack);
			   }
			   modules.put(key, moduleV);
		   }
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {		
		NBTTagList listTag = new NBTTagList();
		for(Entry<String, Vector<ModuleStack>> moduleV : modules.entrySet()){
			NBTTagCompound modulesTag = new NBTTagCompound();
			NBTTagList modulesTagList = new NBTTagList();
			for(ModuleStack stack : moduleV.getValue())
			{
				if(stack != null)
				{
					NBTTagCompound nbtTag = new NBTTagCompound();
					stack.writeToNBT(nbtTag, this);
					modulesTagList.appendTag(nbtTag);
				}
			}
			modulesTag.setTag("modules", modulesTagList);
			modulesTag.setString("key", moduleV.getKey());
			listTag.appendTag(modulesTag);
		}
		nbt.setTag("Modules", listTag);
	}

	@Override
	public int getTier() {
		if(getCasing() != null){
			return getCasing().getTier();
		}
		return 0;
	}

	@Override
	public HashMap<String,Vector<ModuleStack>> getModules() {
		return modules;
	}
	
	@Override
	public void setModules(HashMap<String, Vector<ModuleStack>> modules) {
		this.modules = modules;
	}

	@Override
	public IModularTileEntity getMachine() {
		return machine;
	}
	
	@Override
	public ModuleStack<IModuleEnergyManager> getEnergyManger() {
		return getModule("EnergyManager", 0);
	}
	
	@Override
	public ModuleStack<IModuleCasing> getCasing() {
		return getModule("Casing", 0);
	}

	@Override
	public ModuleStack<IModuleTankManager> getTankManeger() {
		return getModule("TankManager", 0);
	}
	
	@Override
	public void addModule(ModuleStack stack) {
		if(modules.get(stack.getModule().getModuleName()) == null)
			modules.put(stack.getModule().getModuleName(), new Vector<ModuleStack>());
		modules.get(stack.getModule().getModuleName()).add(stack);
	}
	
	@Override
	public ModuleStack getModule(String moduleName, int id) {
		return getModule(moduleName).get(id);
	}
	
	@Override
	public Vector<ModuleStack> getModule(String moduleName) {
		if(modules.get(moduleName) == null)
			return null;
		return modules.get(moduleName);
	}

	@Override
	public IModularUtilsManager getManager() {
		return utils;
	}

}
