package nedelosk.modularmachines.api.modular.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.eventhandler.Event;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.factory.IModuleFactory;
import nedelosk.modularmachines.api.modular.module.producer.farm.IFarm;
import nedelosk.modularmachines.api.parts.IMachinePart;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModuleRegistry {

	private static HashMap<String, IModule> modules = Maps.newHashMap();
	
	private static HashMap<String, Class<? extends IModule>> moduleClasses = Maps.newHashMap();
	
	public static HashMap<String, Class<? extends IModular>> modularClasses = Maps.newHashMap();
	
	private static LinkedHashMap<String, IFarm> farmRegistry = new LinkedHashMap<String, IFarm>(64);
	
	private static ArrayList<ModuleStack> moduleStacks = Lists.newArrayList();
	
	private static ArrayList<IMachinePart> machineParts = Lists.newArrayList();
	
	private static ArrayList<String> requiredModule = Lists.newArrayList();
	
	public static IModuleFactory moduleFactory;
	
	public static void addModule(IModule module)
	{
		if(modules.containsKey(module.getName())){
			return;
		}
		modules.put(module.getName(), module);
		if(!moduleClasses.containsKey(module.getName()))
			registerModuleClass(module.getClass(), module.getName());
		MinecraftForge.EVENT_BUS.post(new ModuleRegisterEvent(module));
	}
	
	public static void registerModuleClass(Class< ? extends IModule> module, String name)
	{
		moduleClasses.put(name, module);
	}
	
	public static void addRequiredModule(String module)
	{
		if(requiredModule.contains(module))
			return;
		requiredModule.add(module);
	}
	
	public static Class<? extends IModule> getModuleClass(String name)
	{
		for(Map.Entry<String, Class<? extends IModule>> entry : moduleClasses.entrySet())
		{
			if(entry.getKey().equals(name))
				return entry.getValue();
		}
		return null;
	}
	
	public static void addModuleStack(ItemStack item, IModule module, int tier, boolean hasNbt)
	{
		ModuleStack itemM = new ModuleStack(item, module, tier, hasNbt);
		ArrayList<ModuleStack> it = moduleStacks;
		if(!moduleStacks.contains(itemM))
			moduleStacks.add(itemM);
		else
		{
			getModuleStacks();
		}
		if(modules.get(module.getName()) == null)
			addModule(module);
	}
	
	public static ItemStack[] getModuleItemStacks(IModule module, int tier)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for(ModuleStack item : moduleStacks)
		{
			if(module.getName().equals(item.getModule().getName()) && tier == item.getTier())
			{
				stacks.add(item.getItem());
			}
				
		}
		return stacks.toArray(new ItemStack[stacks.size()]);
	}
	
	public static void addModuleStack(ItemStack item, IModule module, int tier)
	{
		moduleStacks.add(new ModuleStack(item, module, tier, false));
		if(modules.get(module.getName()) == null)
			addModule(module);
	}
	
	public static ModuleStack getModuleStack(String moduleName, ItemStack stack){
		if(stack == null || moduleName == null)
			return null;
		for(ModuleStack item : moduleStacks)
		{
			if(item.getModuleName().equals(moduleName) && stack.getItem() == item.getItem().getItem() && stack.getItemDamage() == item.getItem().getItemDamage())
				if(ItemStack.areItemStackTagsEqual(stack, item.getItem()) || !item.hasNbt())
					return item;
		}
		return null;
	}
	
	public static ModuleStack getModuleStack(ItemStack stack){
		if(stack == null)
			return null;
		ArrayList<ModuleStack> ite = moduleStacks;
		for(ModuleStack item : moduleStacks)
		{
			if(stack.getItem() == item.getItem().getItem() && stack.getItemDamage() == item.getItem().getItemDamage())
				if(ItemStack.areItemStackTagsEqual(stack, item.getItem()) || !item.hasNbt())
					return item;
		}
		return null;
	}
	
	public static void registerFarm(IFarm farm)
	{
		farmRegistry.put(farm.getName(), farm);
	}
	
	public static LinkedHashMap<String, IFarm> getFarmRegistry() {
		return farmRegistry;
	}
	
	public static IModule getModule(String name)
	{
		return modules.get(name);
	}
	
	public static HashMap<String, IModule> getModules() {
		return modules;
	}
	
	public static HashMap<String, Class< ? extends IModule>> getModuleClasses() {
		return moduleClasses;
	}
	
	public static ArrayList<String> getRequiredModule() {
		return requiredModule;
	}
	
	public static IFarm getFarm(String farm)
	{
		return farmRegistry.get(farm);
	}
	
	public static ArrayList<ModuleStack> getModuleStacks() {
		return moduleStacks;
	}
    
    public static <M extends IMachinePart> M registerMachinePart(M part){
    	machineParts.add(part);
    	return part;
    }
    
    public static ArrayList<IMachinePart> getMachineParts() {
		return machineParts;
	}
    
    public static class ModuleRegisterEvent extends Event{
        public final IModule module;

        public ModuleRegisterEvent(IModule module){
        	this.module = module;
        }
    }
	
}
