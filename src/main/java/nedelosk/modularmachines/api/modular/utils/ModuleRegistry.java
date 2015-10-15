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
import nedelosk.modularmachines.api.modular.tier.Tiers.Tier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModuleRegistry {

	private static HashMap<String, IModule> modules = Maps.newHashMap();
	
	private static HashMap<String, Class<? extends IModule>> moduleClasses = Maps.newHashMap();
	
	private static HashMap<String, Class<? extends IModular>> modularClasses = Maps.newHashMap();
	
	private static LinkedHashMap<String, IFarm> farmRegistry = new LinkedHashMap<String, IFarm>(64);
	
	private static ArrayList<ModuleStack> moduleStacks = Lists.newArrayList();
	
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
	
	public static Class<? extends IModule> getModuleClass(String name)
	{
		for(Map.Entry<String, Class<? extends IModule>> entry : moduleClasses.entrySet())
		{
			if(entry.getKey().equals(name))
				return entry.getValue();
		}
		return null;
	}
	
	public static void addModuleStack(ItemStack item, IModule module, Tier tier, boolean hasNbt)
	{
		ModuleStack stack = new ModuleStack(item, module, tier, hasNbt);
		ArrayList<ModuleStack> it = moduleStacks;
		if(!moduleStacks.contains(stack)){
			moduleStacks.add(stack);
			MinecraftForge.EVENT_BUS.post(new ModuleItemRegisterEvent(stack));
		}
		if(modules.get(module.getName(stack)) == null)
			addModule(module);
	}
	
	public static void addModuleStack(ItemStack item, IModule module, Tier tier)
	{
		ModuleStack stack = new ModuleStack(item, module, tier, false);
		if(!moduleStacks.contains(stack)){
			moduleStacks.add(stack);
			MinecraftForge.EVENT_BUS.post(new ModuleItemRegisterEvent(stack));
		}
		if(modules.get(module.getName(stack)) == null)
			addModule(module);
	}
	
	public static ItemStack[] getModuleItemStacks(IModule module, Tier tier)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for(ModuleStack item : moduleStacks)
		{
			if(module.getName(item).equals(item.getModule().getName(item)) && tier == item.getTier())
			{
				stacks.add(item.getItem());
			}
				
		}
		return stacks.toArray(new ItemStack[stacks.size()]);
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
	
	public static HashMap<String, Class<? extends IModular>> getModularClasses() {
		return modularClasses;
	}
	
	public static IFarm getFarm(String farm)
	{
		return farmRegistry.get(farm);
	}
    
    public static void registerModular(Class<? extends IModular> modular, String name){
    	modularClasses.put(name, modular);
		MinecraftForge.EVENT_BUS.post(new ModularRegisterEvent(modular, name));
    }
    
    public static class ModuleRegisterEvent extends Event{
        public final IModule module;

        public ModuleRegisterEvent(IModule module){
        	this.module = module;
        }
    }
    
    public static class ModuleItemRegisterEvent extends Event{
        public final ModuleStack module;

        public ModuleItemRegisterEvent(ModuleStack module){
        	this.module = module;
        }
    }
    
    public static class ModularRegisterEvent extends Event{
        public final Class<? extends IModular> modular;
        public final String name;

        public ModularRegisterEvent(Class<? extends IModular> modular, String name){
        	this.modular = modular;
        	this.name = name;
        }
    }
	
}
