package nedelosk.modularmachines.common.modular.utils;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.factory.IProducerFactory;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerFactory implements IProducerFactory {

	private static IProducerFactory instance;

	public static void init() {
		instance = new ProducerFactory();
		if (ModuleRegistry.producerFactory == null)
			ModuleRegistry.producerFactory = instance;
	}

	@Override
	public <P extends IProducer> P createProducer(String name, NBTTagCompound nbt, IModular modular,
			ModuleStack stack) {
		try {
			IProducer i = null;
			if (name != null) {
				if (ModuleRegistry.getProducerClass(name) == null)
					return null;
				i = ModuleRegistry.getProducerClass(name)
						.getConstructor(new Class[] { NBTTagCompound.class, IModular.class, ModuleStack.class })
						.newInstance(nbt, modular, stack);
			}
			if (i != null) {
				return (P) i;
			}
			return null;
		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, "Caught an exception during IProducer creation in "
					+ Loader.instance().activeModContainer().getModId() + ":" + name);
			throw new LoaderException(e);
		}
	}

	@Override
	public <P extends IProducer> P createProducer(String name) {
		try {
			IProducer i = null;
			if (name != null) {
				if (ModuleRegistry.getProducerClass(name) == null)
					return null;
				i = ModuleRegistry.getProducerClass(name).newInstance();
			}
			if (i != null) {
				return (P) i;
			}
			return null;
		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, "Caught an exception during IProducer creation in :" + name);
			throw new LoaderException(e);
		}
	}

	public static IProducerFactory getInstance() {
		return instance;
	}

}
