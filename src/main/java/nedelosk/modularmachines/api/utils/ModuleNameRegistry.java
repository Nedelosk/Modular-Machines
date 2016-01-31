package nedelosk.modularmachines.api.utils;

import java.util.HashMap;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.Loader;
import nedelosk.modularmachines.api.modules.IModule;
import net.minecraft.util.ResourceLocation;

public class ModuleNameRegistry {

	private HashMap<ResourceLocation, IModule> modules = Maps.newHashMap();

	public IModule register(IModule module, String uidName) {
		if (module == null || uidName == null) {
			return null;
		}
		String modID = Loader.instance().activeModContainer().getModId();
		ResourceLocation uid = new ResourceLocation(uidName);
		if (modules.containsKey(uid)) {
			return null;
		}
		modules.put(uid, module);
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