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
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.module.tool.producer.farm.IFarm;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModuleRegistry {
	
	//Module Factory
	public static IModuleFactory moduleFactory;
	
    //Module Registry
	private static HashMap<String, IModule> modules = Maps.newHashMap();
	
	public static void registerModule(IModule module){
		modules.put(module.getRegistryName(), module);
		MinecraftForge.EVENT_BUS.post(new Events.ModuleRegisterEvent(module));
	}
	
	public static IModule getModule(String name)
	{
		return modules.get(name);
	}
	
	public static HashMap<String, IModule> getModules() {
		return modules;
	}
    
    //Module Item Registry
	private static ArrayList<ModuleStack> moduleItems = Lists.newArrayList();
    
	public static ArrayList<ModuleStack> getModuleItems() {
		return moduleItems;
	}
	
	public static void addModuleItem(ModuleStack stack)
	{
		ArrayList<ModuleStack> it = moduleItems;
		if(!moduleItems.contains(stack)){
			moduleItems.add(stack);
			MinecraftForge.EVENT_BUS.post(new Events.ModuleItemRegisterEvent(stack));
		}
		if(!stack.getModule().getTypes().contains(stack.getType())){
			stack.getModule().addType(stack.getType());
		}
	}
    
	public static void addModuleItem(ItemStack item, IModule module, IProducer producer, Type type, boolean hasNbt)
	{
		addModuleItem(new ModuleStack(item, module, producer, type, hasNbt));
	}
	
	public static void addModuleItem(ItemStack item, IModule module, Type type, boolean hasNbt)
	{
		addModuleItem(item, module, null, type, hasNbt);
	}
	
	public static void addModuleItem(ItemStack item, IModule module, Type type)
	{
		addModuleItem(item, module, type, false);
	}
	
	public static ModuleStack getModuleItem(ItemStack stack){
		if(stack == null)
			return null;
		for(ModuleStack item : moduleItems)
		{
			if(stack.getItem() == item.getItem().getItem() && stack.getItemDamage() == item.getItem().getItemDamage())
				if(ItemStack.areItemStackTagsEqual(stack, item.getItem()) || !item.hasNbt())
					return item;
		}
		return null;
	}
	
    //Modular Registry
	private static HashMap<String, Class<? extends IModular>> modularClasses = Maps.newHashMap();
	
    public static void registerModular(Class<? extends IModular> modular, String name){
    	modularClasses.put(name, modular);
		MinecraftForge.EVENT_BUS.post(new Events.ModularRegisterEvent(modular, name));
    }
    
	public static HashMap<String, Class<? extends IModular>> getModular() {
		return modularClasses;
	}
	
	//Module Farm Registry
	private static LinkedHashMap<String, IFarm> farmRegistry = new LinkedHashMap<String, IFarm>(64);
	
	public static void registerFarm(IFarm farm)
	{
		farmRegistry.put(farm.getName(), farm);
		MinecraftForge.EVENT_BUS.post(new Events.ModuleFarmRegisterEvent(farm));
	}
	
	public static IFarm getFarm(String farm)
	{
		return farmRegistry.get(farm);
	}
	
	public static LinkedHashMap<String, IFarm> getFarmRegistry() {
		return farmRegistry;
	}
	
	//Module Producer
	private static HashMap<String, Class<? extends IProducer>> producerClasses = Maps.newHashMap();
	
	public static Class<? extends IProducer> getProducer(String name){
		for(Map.Entry<String, Class<? extends IProducer>> entry : producerClasses.entrySet())
		{
			if(entry.getKey().equals(name))
				return entry.getValue();
		}
		return null;
	}
	
    public static void registerProducer(Class<? extends IProducer> producer, String name){
    	producerClasses.put(name, producer);
		MinecraftForge.EVENT_BUS.post(new Events.ModuleProducerRegisterEvent(producer));
    }
    
    public static class Events{
    	
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
        
        public static class ModuleFarmRegisterEvent extends Event{
            public final IFarm farm;

            public ModuleFarmRegisterEvent(IFarm farm){
            	this.farm = farm;
            }
        }
        
        public static class ModuleProducerRegisterEvent extends Event{
            public final Class<? extends IProducer> producer;

            public ModuleProducerRegisterEvent(Class<? extends IProducer> producer){
            	this.producer = producer;
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
	
}
