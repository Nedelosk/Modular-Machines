package nedelosk.modularmachines.common.modular.utils;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.factory.IModuleFactory;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleFactory implements IModuleFactory {

	private static IModuleFactory instance;
	
	public static void init(){
		instance = new ModuleFactory();
		if(ModuleRegistry.moduleFactory == null)
			ModuleRegistry.moduleFactory = instance;
	}
	
	@Override
	public <M> M createModule(String moduleName) {
        try
        {
            IModule i = null;
            if (moduleName != null)
            {
                i = ModuleRegistry.getModuleClass(moduleName).newInstance();
            }
            if (i != null)
            {
                return (M) i;
            }
            return null;
        }
        catch (Exception e)
        {
            FMLLog.log(Level.ERROR, e, "Caught an exception during IModule registration in " + Loader.instance().activeModContainer().getModId() + ":" + moduleName);
            throw new LoaderException(e);
        }
	}

	@Override
	public <M> M createModule(String name, NBTTagCompound nbt, IModular modular) {
        try
        {
            IModule i = null;
            if (name != null)
            {
            	if(ModuleRegistry.getModuleClass(name) == null)
            		return null;
                i = ModuleRegistry.getModuleClass(name).newInstance();
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
	
	public static IModuleFactory getInstance() {
		return instance;
	}

}
