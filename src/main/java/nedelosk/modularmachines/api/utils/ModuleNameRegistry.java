package nedelosk.modularmachines.api.utils;

import java.util.HashMap;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.Loader;
import nedelosk.modularmachines.api.modules.IModule;
import net.minecraft.util.ResourceLocation;

public class ModuleNameRegistry {

	private HashMap<ResourceLocation, IModule> producers = Maps.newHashMap();

	public IModule register(IModule producer, String registryName) {
		if (producer == null || registryName == null) {
			return null;
		}
		String modID = Loader.instance().activeModContainer().getModId();
		ResourceLocation registry = new ResourceLocation(modID, registryName);
		if (producers.containsKey(registry)) {
			return null;
		}
		producer.setRegistry(registry);
		producers.put(registry, producer);
		return producer;
	}

	public IModule get(ResourceLocation registry) {
		if (producers.containsKey(registry)) {
			return producers.get(registry);
		}
		return null;
	}

	public HashMap<ResourceLocation, IModule> getProducers() {
		return producers;
	}
}