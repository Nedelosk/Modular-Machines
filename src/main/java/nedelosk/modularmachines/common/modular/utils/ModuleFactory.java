package nedelosk.modularmachines.common.modular.utils;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.factory.IModuleFactory;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleFactory implements IModuleFactory {

	private static IModuleFactory instance;
	
	public static void init(){
		instance = new ModuleFactory();
		if(ModuleRegistry.moduleFactory == null)
			ModuleRegistry.moduleFactory = instance;
	}
	
	@Override
	public <P extends IProducer> P createProducer(String name, NBTTagCompound nbt, IModular modular, ModuleStack stack) {
        try
        {
            IProducer i = null;
            if (name != null)
            {
            	if(ModuleRegistry.getProducer(name) == null)
            		return null;
                i = ModuleRegistry.getProducer(name).getConstructor(new Class[]{ NBTTagCompound.class, IModular.class, ModuleStack.class }).newInstance(nbt, modular, stack);
            }
            if (i != null)
            {
                return (P) i;
            }
            return null;
        }
        catch (Exception e)
        {
            FMLLog.log(Level.ERROR, e, "Caught an exception during IProducer creation in " + Loader.instance().activeModContainer().getModId() + ":" + name);
            throw new LoaderException(e);
        }
	}
	
	@Override
	public <P extends IProducer> P createProducer(String name) {
        try
        {
            IProducer i = null;
            if (name != null)
            {
            	if(ModuleRegistry.getProducer(name) == null)
            		return null;
                i = ModuleRegistry.getProducer(name).newInstance();
            }
            if (i != null)
            {
                return (P) i;
            }
            return null;
        }
        catch (Exception e)
        {
            FMLLog.log(Level.ERROR, e, "Caught an exception during IProducer creation in " + Loader.instance().activeModContainer().getModId() + ":" + name);
            throw new LoaderException(e);
        }
	}
	
	public static IModuleFactory getInstance() {
		return instance;
	}

}
