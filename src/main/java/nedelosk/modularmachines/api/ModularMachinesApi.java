package nedelosk.modularmachines.api;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.api.modular.module.ModuleItem;
import nedelosk.modularmachines.api.modular.module.farm.IFarm;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModularMachinesApi {
	
	private static HashMap<String, IModule> modules = new HashMap<String, IModule>();
	private static HashMap<String, Class< ? extends IModule>> moduleClasses = new HashMap<String, Class< ? extends IModule>>();
	private static LinkedHashMap<String, IFarm> farmRegistry = new LinkedHashMap<String, IFarm>(64);
	private static ArrayList<ModuleItem> items = new ArrayList<ModuleItem>();
	private static IModule registerFailed;
	private static ArrayList<String> requiredModule = new ArrayList<String>();
	public final static LinkedHashMap<String, ArrayList<ModuleEntry>> moduleEntrys = new LinkedHashMap<String, ArrayList<ModuleEntry>>();
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
	
	public static IModule buildModule(String name, NBTTagCompound nbt, IModular modular)
	{
        try
        {
            IModule i = null;
            if (name != null)
            {
                Constructor<? extends IModule> itemCtor = getModuleClass(name).getConstructor();
                i = itemCtor.newInstance();
                i.readFromNBT(nbt, modular);
            }
            if (i != null)
            {
                return i;
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
		ModuleItem itemM = new ModuleItem(item, module, tier, hasNbt);
		ArrayList<ModuleItem> it = items;
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
		for(ModuleItem item : items)
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
		items.add(new ModuleItem(item, module, tier, false));
		if(modules.get(module.getName()) == null)
			addModule(module);
	}
	
	public static ModuleItem getModuleItem(String moduleName, ItemStack stack){
		if(stack == null || moduleName == null)
			return null;
		for(ModuleItem item : items)
		{
			if(item.moduleName.equals(moduleName) && stack.getItem() == item.item.getItem() && stack.getItemDamage() == item.item.getItemDamage())
				if(ItemStack.areItemStackTagsEqual(stack, item.item) || !item.hasNbt)
					return item;
		}
		return null;
	}
	
	public static ModuleItem getModuleItem(ItemStack stack){
		if(stack == null)
			return null;
		ArrayList<ModuleItem> ite = items;
		for(ModuleItem item : items)
		{
			if(stack.getItem() == item.item.getItem() && stack.getItemDamage() == item.item.getItemDamage())
				if(ItemStack.areItemStackTagsEqual(stack, item.item) || !item.hasNbt)
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
	
	public static ArrayList<ModuleItem> getItems() {
		return items;
	}
	
    
    public static void registerBookmark(String markName)
    {
    	moduleEntrys.put(markName, new ArrayList<ModuleEntry>());
    }
    
    public static ModuleEntry getModuleEntry(String page, int ID)
    {
    	for(ModuleEntry entry : moduleEntrys.get(page))
    	{
    		if(entry.ID == ID && entry.page.equals(page))
    			return entry;
    	}
    	return null;
    }
    
    public static void addBookmarkItem(String markName, ItemStack stack)
    {
    	if(bookmark.get(markName) == null)
    		bookmark.put(markName, new ArrayList<ItemStack>());
    	bookmark.get(markName).add(stack);
    }
    
    public static void addModuleEntry(String page, int x, int y, String moduleName, RendererSides... rendererSides)
    {
    	moduleEntrys.get(page).add(new ModuleEntry(x - 9, y - 9, moduleName));
    }
    
    public static void addModuleEntry(ModuleEntry entry)
    {
    	moduleEntrys.get(entry.page).add(entry);
    	moduleEntrys.get(entry.page).get(0);
    }
	
}
