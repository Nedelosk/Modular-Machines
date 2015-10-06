package nedelosk.modularmachines.common.modular.machines.modular;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.manager.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEnergyManager;
import nedelosk.modularmachines.api.modular.module.basic.fluids.IModuleFluidManager;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class Modular implements IModular {
	
	protected HashMap<String, Vector<ModuleStack>> modules = Maps.newHashMap();
	protected IModularTileEntity machine;
	protected IModularUtilsManager utils;
	
	public Modular() {
	}
	
	public Modular(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	public void update()
	{
		for(Vector<ModuleStack> moduleV : modules.values())
			for(ModuleStack module : moduleV)
				module.getModule().update(this);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		   if(modules == null)
			   modules = Maps.newHashMap();
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
	
	public void setMachine(IModularTileEntity machine) {
		this.machine = machine;
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
	public ModuleStack<IModuleFluidManager> getTankManeger() {
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
