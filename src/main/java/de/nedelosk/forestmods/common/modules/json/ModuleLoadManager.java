package de.nedelosk.forestmods.common.modules.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.json.IModuleLoader;

public class ModuleLoadManager {
	
	private static final Map<String, IModuleLoader> loaders = new HashMap<>();
	
	public static void registerLoader(IModuleLoader loader){
		if(!loaders.containsKey(loader.getName())){
			loaders.put(loader.getName(), loader);
		}
	}
	
	public static IModuleLoader getLoader(String loaderName){
		return loaders.get(loaderName);
	}
	
	public static class ModuleCategory{
		
		private final List<ModuleEntry> entrys = new ArrayList<>();
		private final String name;
		
		public ModuleCategory(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
	}
	
	public static class ModuleEntry{
		
		public final Class<? extends IModule> moduleClass;
		public final Object[] parameters;
		
		public ModuleEntry(Class<? extends IModule> moduleClass, Object... parameters) {
			this.moduleClass = moduleClass;
			this.parameters = parameters;
		}
		
	}
	
}
