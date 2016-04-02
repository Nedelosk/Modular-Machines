package de.nedelosk.forestmods.common.modules;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.utils.ModuleEvents;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import net.minecraftforge.common.MinecraftForge;

public class ModuleMaps {

	private Map<IMaterial, Map<String, List<IModule>>> modules = Maps.newHashMap();

	public <M extends IModule> M registerModule(IMaterial material, String category, M module) {
		if (material == null || category == null || module == null) {
			return null;
		}
		if (!modules.containsKey(material)) {
			modules.put(material, Maps.newHashMap());
		}
		if (!modules.get(material).containsKey(material)) {
			modules.get(material).put(category, Lists.newArrayList());
		}
		if (modules.get(material).get(category).contains(module)) {
			return module;
		}
		if (modules.get(material).get(category).add(module)) {
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleRegisterEvent(module));
			return module;
		}
		return null;
	}

	public IModule getModule(IMaterial material, ModuleUID UID) {
		if (UID == null || material == null) {
			return null;
		}
		if (modules.containsKey(material)) {
			if (modules.get(material).containsKey(UID.getCategoryUID())) {
				for ( IModule module : modules.get(material).get(UID.getCategoryUID()) ) {
					if (module.getName().equals(UID.getModuleUID())) {
						return module;
					}
				}
			}
		}
		return null;
	}
}