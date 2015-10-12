package nedelosk.modularmachines.common.modular.utils;

import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.materials.Tags;
import nedelosk.modularmachines.api.modular.machines.basic.AssemblerMachineInfo.BuildMode;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.parts.IMachinePart;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MachineBuilder {
	
	public static ItemStack buildMachineItem(ItemStack[] inputs, String moduleName, BuildMode mode, int tier, ItemStack part)
	{
		if(mode == BuildMode.MACHINE){
			IModular machine = buildMachine(inputs, moduleName);
			if(machine != null){
				if(tier >= machine.getTier()){
					ItemStack stack = MMBlockManager.Modular_Machine.getItemStack();
					stack.setTagCompound(new NBTTagCompound());
					NBTTagCompound nbtTag = new NBTTagCompound();
					machine.writeToNBT(nbtTag);
					stack.getTagCompound().setTag("Machine", nbtTag);
					stack.getTagCompound().setString("MachineName", machine.getName());
					return stack;
				}
			}
		}else if(mode == BuildMode.PART){
			if(part == null)
				return null;
			else{
				ItemStack output = ((IMachinePart)part.getItem()).buildItemFromStacks(inputs);
		    	if(output != null && output.hasTagCompound()){
			        if(tier >= output.getTagCompound().getCompoundTag(Tags.TAG_MACHINE).getInteger("Tier")) {
			        	return output;
			        }
		    	}
		      }
		}
		return null;
	}
	
	public static IModular buildMachine(ItemStack[] slots, String moduleName)
	{
		for(Entry<String, Class<? extends IModular>> entry : ModuleRegistry.getModularClasses().entrySet()){
			IModular modularType = createMachine(moduleName);
			IModular modular = modularType.buildItem(slots);
			if(modular != null){
				return modular;
			}
		}
		/*HashMap<String, Vector<ModuleStack>> moduleStacks = Maps.newHashMap();
		ArrayList<String> modules = new ArrayList();
		for(int i = 0;i < slots.length;i++)
		{
			ItemStack stack = slots[i];
			ModuleStack module = ModuleRegistry.getModuleStack(stack);
			if(module != null)
			{
				if(moduleStacks.get(module.getModuleName()) == null)
					moduleStacks.put(module.getModuleName(), new Vector<ModuleStack>());
				moduleStacks.get(module.getModuleName()).add(module.copy());
				modules.add(module.getModuleName());
			}
		}
		
		for(String module : ModuleRegistry.getRequiredModule())
		{
			if(!modules.contains(module))
				return null;
		}
		return createMachine(moduleStacks, moduleName);*/
		return null;
	}
	
	/*public static IModular createMachine(HashMap<String, Vector<ModuleStack>> modules, String moduleName)
	{
		IModular machine = createMachine(moduleName);
		if(machine != null)
			machine.setModules(modules);
		return machine;
	}*/
	
	public static <M extends IModular> M createMachine(String moduleName, Object... ctorArgs){
		IModular machine = null;
		try{
            if(ctorArgs == null || ctorArgs.length == 0)
            	if(ModuleRegistry.getModularClasses().get(moduleName) != null)
            		return (M) ModuleRegistry.getModularClasses().get(moduleName).getConstructor().newInstance();
            Class<?>[] ctorArgClasses = new Class<?>[ctorArgs.length];
            for (int idx = 0; idx < ctorArgClasses.length; idx++)
            {
                ctorArgClasses[idx] = ctorArgs[idx].getClass();
            }
            if(ModuleRegistry.getModularClasses().get(moduleName) != null)
            	machine = ModuleRegistry.getModularClasses().get(moduleName).getConstructor(ctorArgClasses).newInstance(ctorArgs);
            else
            	return null;
		}catch(Exception e){
            FMLLog.log(Level.ERROR, e, "Caught an exception during IModular registration in " + Loader.instance().activeModContainer().getModId() + ":" + moduleName);
            throw new LoaderException(e);
		}
		return (M) machine;
	}
	
}
