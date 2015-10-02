package nedelosk.modularmachines.common.machines.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import nedelosk.modularmachines.api.modular.module.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.parts.IMachinePart;
import nedelosk.modularmachines.common.core.MMBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MachineBuilder {

	public static enum BuildMode{
		MACHINE, PART
	}
	
	public static ItemStack buildMachineItem(ItemStack[] inputs, String moduleName, BuildMode mode)
	{
		if(mode == BuildMode.MACHINE){
			IModular machine = buildMachine(inputs, moduleName);
			if(machine != null){
				ItemStack stack = MMBlocks.Modular_Machine.getItemStack();
				stack.setTagCompound(new NBTTagCompound());
				NBTTagCompound nbtTag = new NBTTagCompound();
				machine.writeToNBT(nbtTag);
				stack.getTagCompound().setTag("Machine", nbtTag);
				stack.getTagCompound().setString("MachineName", machine.getName());
				return stack;
			}
		}else if(mode == BuildMode.PART){
		    for(IMachinePart item : ModularManager.getMachineParts()) {
		    	ItemStack output = item.buildItemFromStacks(inputs);
		        if(output != null) {
		        	return output;
		        }
		      }
		}
		return null;
	}
	
	public static IModular buildMachine(ItemStack[] slots, String moduleName)
	{
		HashMap<String, Vector<ModuleStack>> moduleStacks = Maps.newHashMap();
		ArrayList<String> modules = new ArrayList();
		for(int i = 0;i < slots.length;i++)
		{
			ItemStack stack = slots[i];
			ModuleStack module = ModularManager.getModuleStack(stack);
			if(module != null)
			{
				if(moduleStacks.get(module.getModuleName()) == null)
					moduleStacks.put(module.getModuleName(), new Vector<ModuleStack>());
				moduleStacks.get(module.getModuleName()).add(module.copy());
				modules.add(module.getModuleName());
			}
		}
		
		for(String module : ModularManager.getRequiredModule())
		{
			if(!modules.contains(module))
				return null;
		}
		return createMachine(moduleStacks, moduleName);
	}
	
	public static IModular createMachine(HashMap<String, Vector<ModuleStack>> modules, String moduleName)
	{
		IModular machine = createMachine(moduleName);
		if(machine != null)
			machine.setModules(modules);
		return machine;
	}
	
	public static <M extends IModular> M createMachine(String moduleName, Object... ctorArgs){
		IModular machine = null;
		try{
            Class<?>[] ctorArgClasses = new Class<?>[ctorArgs.length];
            for (int idx = 0; idx < ctorArgClasses.length; idx++)
            {
                ctorArgClasses[idx] = ctorArgs[idx].getClass();
            }
			machine = ModularManager.modularClasses.get(moduleName).getConstructor(ctorArgClasses).newInstance(ctorArgs);
		}catch(Exception e){
            FMLLog.log(Level.ERROR, e, "Caught an exception during IModular registration in " + Loader.instance().activeModContainer().getModId() + ":" + moduleName);
            throw new LoaderException(e);
		}
		return (M) machine;
	}
	
}
