package nedelosk.modularmachines.api.basic.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.IModule;
import nedelosk.modularmachines.api.basic.machine.module.ModuleStack;
import nedelosk.modularmachines.api.basic.machine.module.farm.IFarm;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModularManager {

	private static HashMap<String, IModule> modules = new HashMap<String, IModule>();
	
	private static HashMap<String, Class<? extends IModule>> moduleClasses = new HashMap<String, Class<? extends IModule>>();
	
	public static HashMap<String, Class<? extends IModular>> modularClasses = new HashMap<String, Class<? extends IModular>>();
	
	private static LinkedHashMap<String, IFarm> farmRegistry = new LinkedHashMap<String, IFarm>(64);
	
	private static ArrayList<ModuleStack> items = new ArrayList<ModuleStack>();
	
	private static ArrayList<String> requiredModule = new ArrayList<String>();
	
	private static IModule registerFailed;
	
	public final static HashMap<String, ArrayList<ItemStack>> bookmark = new HashMap<String, ArrayList<ItemStack>>();
	
	public static void addModule(IModule module)
	{
		if(modules.containsKey(module.getName()))
			registerFailed = module;
		modules.put(module.getName(), module);
		if(!moduleClasses.containsKey(module.getName()))
			addModuleClass(module.getClass(), module.getName());
	}
	
	public static void addModuleClass(Class< ? extends IModule> module, String name)
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
	
	public static <M> M buildModule(String name, NBTTagCompound nbt, IModular modular)
	{
        try
        {
            IModule i = null;
            if (name != null)
            {
                i = getModuleClass(name).newInstance();
                i.readFromNBT(nbt, modular);
            }
            if (i != null)
            {
                return (M) i;
            }
            return null;
        }
        catch (Exception e)
        {
            FMLLog.log(Level.ERROR, e, "Caught an exception during IModule registration in " + Loader.instance().activeModContainer().getModId() + ":" + name);
            throw new LoaderException(e);
        }
	}
	
	public static void addModuleItem(ItemStack item, IModule module, int tier, boolean hasNbt)
	{
		ModuleStack itemM = new ModuleStack(item, module, tier, hasNbt);
		ArrayList<ModuleStack> it = items;
		if(!items.contains(itemM))
			items.add(itemM);
		else
		{
			getItems();
		}
		if(modules.get(module.getName()) == null)
			addModule(module);
	}
	
	public static ItemStack[] addModuleItemStacks(IModule module, int tier)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for(ModuleStack item : items)
		{
			if(module.getName().equals(item.getModule().getName()) && tier == item.getTier())
			{
				stacks.add(item.getItem());
			}
				
		}
		return stacks.toArray(new ItemStack[stacks.size()]);
	}
	
	public static void addModuleItem(ItemStack item, IModule module, int tier)
	{
		items.add(new ModuleStack(item, module, tier, false));
		if(modules.get(module.getName()) == null)
			addModule(module);
	}
	
	public static ModuleStack getModuleItem(String moduleName, ItemStack stack){
		if(stack == null || moduleName == null)
			return null;
		for(ModuleStack item : items)
		{
			if(item.getModuleName().equals(moduleName) && stack.getItem() == item.getItem().getItem() && stack.getItemDamage() == item.getItem().getItemDamage())
				if(ItemStack.areItemStackTagsEqual(stack, item.getItem()) || !item.hasNbt())
					return item;
		}
		return null;
	}
	
	public static ModuleStack getModuleItem(ItemStack stack){
		if(stack == null)
			return null;
		ArrayList<ModuleStack> ite = items;
		for(ModuleStack item : items)
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
	
	public static IModule getRegisterFailed() {
		return registerFailed;
	}
	
	public static IFarm getFarm(String farm)
	{
		return farmRegistry.get(farm);
	}
	
	public static ArrayList<ModuleStack> getItems() {
		return items;
	}
    
    public static void addBookmarkItem(String markName, ItemStack stack)
    {
    	if(bookmark.get(markName) == null)
    		bookmark.put(markName, new ArrayList<ItemStack>());
    	bookmark.get(markName).add(stack);
    }
	
}
