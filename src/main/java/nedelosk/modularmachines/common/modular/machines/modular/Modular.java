package nedelosk.modularmachines.common.modular.machines.modular;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.manager.IModularGuiManager;
import nedelosk.modularmachines.api.modular.machines.manager.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerBattery;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.IProducerFluidManager;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.machines.modular.managers.ModularGuiManager;
import nedelosk.modularmachines.common.modular.machines.modular.managers.ModularUtilsManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class Modular implements IModular {
	
	protected HashMap<String, Vector<ModuleStack>> modules = Maps.newHashMap();
	protected IModularTileEntity machine;
	protected IModularUtilsManager utils;
	protected IModularGuiManager gui;
	protected String page;
	
	public Modular() {
		utils = new ModularUtilsManager();
		gui = new ModularGuiManager();
	}
	
	public Modular(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	@Override
	public void update(){
		for(Vector<ModuleStack> moduleV : modules.values())
			for(ModuleStack module : moduleV)
				if(module != null && module.getModule() != null && module.getProducer() != null)
					module.getProducer().update(this, module);
		
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
		   utils = new ModularUtilsManager();
		   utils.readFromNBT(nbt);
		   gui = new ModularGuiManager();
		   gui.readFromNBT(nbt);
		   gui.setModular(this);
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
		if(utils == null)
			utils = new ModularUtilsManager();
		utils.writeToNBT(nbt);
		if(gui == null)
			gui = new ModularGuiManager();
		gui.writeToNBT(nbt);
	}

	@Override
	public int getTier() {
		if(getCasing() != null){
			int tiers = 0;
			int size = 0;
			for(Vector<ModuleStack> modules : modules.values()){
				if(modules != null){
					for(ModuleStack module : modules){
						if(module != null){
							tiers += module.getTier().getStage();
							size++;
						}
					}
				}
			}
			return tiers/size;
		}
		return 1;
	}
	
	@Override
	public void initModular(){
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
	public void setMachine(IModularTileEntity machine) {
		this.machine = machine;
	}

	@Override
	public IModularTileEntity getMachine() {
		return machine;
	}
	
	@Override
	public ModuleStack<IModule, IProducerBattery> getBattery() {
		return getModule("Battery", 0);
	}
	
	@Override
	public ModuleStack<IModuleCasing, IProducer> getCasing() {
		return getModule("Casing", 0);
	}

	@Override
	public ModuleStack<IModule, IProducerFluidManager> getTankManeger() {
		return getModule("TankManager", 0);
	}
	
	@Override
	public boolean addModule(ModuleStack stack) {
		if(stack == null || stack.getModule() == null)
			return false;
		if(modules.get(stack.getModule().getModuleName()) == null)
			modules.put(stack.getModule().getModuleName(), new Vector<ModuleStack>());
		modules.get(stack.getModule().getModuleName()).add(stack);
		return true;
	}
	
	@Override
	public ModuleStack getModule(String moduleName, int id) {
		if(getModule(moduleName) == null)
			return null;
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
	
	@Override
	public IModularGuiManager getGuiManager() {
		return gui;
	}

}
