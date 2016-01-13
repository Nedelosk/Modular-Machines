package nedelosk.modularmachines.common.modular.utils;

import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.modules.ModuleModular;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MachineBuilder {

	public static ItemStack buildMachineItem(ItemStack[] inputs, String moduleName, int tier, ItemStack part) {
		IModular machine = buildMachine(inputs, moduleName);
		if (machine != null) {
			if (tier >= machine.getTier()) {
				try {
					ItemStack stack = new ItemStack(ModuleModular.BlockManager.Modular_Machine.item());
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
		for ( Entry<String, Class<? extends IModular>> entry : ModuleRegistry.getModular().entrySet() ) {
			IModular modularType = createMachine(moduleName);
			IModular modular = modularType.buildItem(slots);
			if (modular != null) {
				return modular;
			}
		}
		return null;
	}

	public static <M extends IModular> M createMachine(String modularName, Object... ctorArgs) {
		IModular machine = null;
		try {
			if (ctorArgs == null || ctorArgs.length == 0) {
				if (ModuleRegistry.getModular().get(modularName) != null) {
					return (M) ModuleRegistry.getModular().get(modularName).getConstructor().newInstance();
				}
			}
			Class<?>[] ctorArgClasses = new Class<?>[ctorArgs.length];
			for ( int idx = 0; idx < ctorArgClasses.length; idx++ ) {
				ctorArgClasses[idx] = ctorArgs[idx].getClass();
			}
			if (ModuleRegistry.getModular().get(modularName) != null) {
				machine = ModuleRegistry.getModular().get(modularName).getConstructor(ctorArgClasses).newInstance(ctorArgs);
			} else {
				return null;
			}
		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, "Caught an exception during IModular registration in :" + modularName);
			throw new LoaderException(e);
		}
		return (M) machine;
	}
}
