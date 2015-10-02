package nedelosk.modularmachines.common.machines.utils;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.factory.IModuleFactory;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleFactory implements IModuleFactory {

	@Override
	public IModule createModule(ItemStack stack, String moduleName) {
		return null;
	}

	@Override
	public <M> M createModule(String name, NBTTagCompound nbt, IModular modular) {
        try
        {
            IModule i = null;
            if (name != null)
            {
                i = ModularManager.getModuleClass(name).newInstance();
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

}
