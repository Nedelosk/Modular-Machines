package nedelosk.modularmachines.api.utils;

import java.util.HashMap;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.Loader;
import nedelosk.modularmachines.api.modules.IModule;
import net.minecraft.util.ResourceLocation;

public class ModuleNameRegistry {

	private HashMap<ResourceLocation, IModule> modules = Maps.newHashMap();

	public IModule register(IModule module, String registryName) {
		if (module == null || registryName == null) {
			return null;
		}
		String modID = Loader.instance().activeModContainer().getModId();
		ResourceLocation registry = new ResourceLocation(modID, registryName);
		if (modules.containsKey(registry)) {
			return null;
		}
		module.setRegistry(registry);
		modules.put(registry, module);
		return module;
	}

	public IModule get(ResourceLocation registry) {
		if (modules.containsKey(registry)) {
			return modules.get(registry);
		}
		return null;
	}

	public HashMap<ResourceLocation, IModule> getModules() {
		return modules;
	}
}