package nedelosk.modularmachines.common.modular.utils;

import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MachineBuilder {

	public static ItemStack buildMachineItem(ItemStack[] inputs, String moduleName, int tier, ItemStack part) {
		IModular machine = buildMachine(inputs, moduleName);
		if (machine != null) {
			if (tier >= machine.getTier()) {
				try {
					ItemStack stack = MMBlockManager.Modular_Machine.getItemStack();
					stack.setTagCompound(new NBTTagCompound());
					NBTTagCompound nbtTag = new NBTTagCompound();
					machine.writeToNBT(nbtTag);
					stack.getTagCompound().setTag("Machine", nbtTag);
					stack.getTagCompound().setString("MachineName", machine.getName());
					return stack;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	public static IModular buildMachine(ItemStack[] slots, String moduleName) {
		for (Entry<String, Class<? extends IModular>> entry : ModuleRegistry.getModular().entrySet()) {
			IModular modularType = createMachine(moduleName);
			IModular modular = modularType.buildItem(slots);
			if (modular != null) {
				return modular;
			}
		}
		/*
		 * HashMap<String, Vector<ModuleStack>> moduleStacks =
		 * Maps.newHashMap(); ArrayList<String> modules = new ArrayList();
		 * for(int i = 0;i < slots.length;i++) { ItemStack stack = slots[i];
		 * ModuleStack module = ModuleRegistry.getModuleStack(stack); if(module
		 * != null) { if(moduleStacks.get(module.getModuleName()) == null)
		 * moduleStacks.put(module.getModuleName(), new Vector<ModuleStack>());
		 * moduleStacks.get(module.getModuleName()).add(module.copy());
		 * modules.add(module.getModuleName()); } }
		 * 
		 * for(String module : ModuleRegistry.getRequiredModule()) {
		 * if(!modules.contains(module)) return null; } return
		 * createMachine(moduleStacks, moduleName);
		 */
		return null;
	}

	/*
	 * public static IModular createMachine(HashMap<String, Vector<ModuleStack>>
	 * modules, String moduleName) { IModular machine =
	 * createMachine(moduleName); if(machine != null)
	 * machine.setModules(modules); return machine; }
	 */

	public static <M extends IModular> M createMachine(String modularName, Object... ctorArgs) {
		IModular machine = null;
		try {
			if (ctorArgs == null || ctorArgs.length == 0)
				if (ModuleRegistry.getModular().get(modularName) != null)
					return (M) ModuleRegistry.getModular().get(modularName).getConstructor().newInstance();
			Class<?>[] ctorArgClasses = new Class<?>[ctorArgs.length];
			for (int idx = 0; idx < ctorArgClasses.length; idx++) {
				ctorArgClasses[idx] = ctorArgs[idx].getClass();
			}
			if (ModuleRegistry.getModular().get(modularName) != null)
				machine = ModuleRegistry.getModular().get(modularName).getConstructor(ctorArgClasses)
						.newInstance(ctorArgs);
			else
				return null;
		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, "Caught an exception during IModular registration in :" + modularName);
			throw new LoaderException(e);
		}
		return (M) machine;
	}

}
