package de.nedelosk.forestmods.api.utils;

import java.util.HashMap;

import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.modular.material.Materials.Material;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleType;
import net.minecraft.util.ResourceLocation;

public class ModuleMaps {

	private HashMap<ResourceLocation, IModule> modules = Maps.newHashMap();
	private HashMap<IModule, HashMap<Material, IModuleType>> moduleTypes = Maps.newHashMap();

	public <M extends IModule> M registerModule(M module, String uidName) {
		if (module == null || uidName == null) {
			return null;
		}
		ResourceLocation uid = new ResourceLocation(uidName);
		if (modules.containsKey(uid)) {
			return null;
		}
		moduleTypes.put(module, Maps.newHashMap());
		modules.put(uid, module);
		return module;
	}

	public IModuleType registerModuleType(IModule module, Material material, IModuleType type) {
		if (module == null || material == null) {
			return null;
		}
		if (!moduleTypes.containsKey(module) || moduleTypes.get(module).containsKey(type)) {
			return null;
		}
		moduleTypes.get(module).put(material, type);
		return type;
	}

	public IModule getModule(ResourceLocation registry) {
		if (modules.containsKey(registry)) {
			return modules.get(registry);
		}
		return null;
	}

	public IModuleType getModuleType(IModule module, Material material) {
		if (moduleTypes.containsKey(module) && moduleTypes.get(module).containsKey(material)) {
			return moduleTypes.get(module).get(material);
		}
		return null;
	}

	public HashMap<Material, IModuleType> getTypes(IModule module) {
		if (moduleTypes.containsKey(module)) {
			return moduleTypes.get(module);
		}
		return null;
	}

	public HashMap<ResourceLocation, IModule> getModules() {
		return modules;
	}

	public HashMap<IModule, HashMap<Material, IModuleType>> getModuleTypes() {
		return moduleTypes;
	}
}