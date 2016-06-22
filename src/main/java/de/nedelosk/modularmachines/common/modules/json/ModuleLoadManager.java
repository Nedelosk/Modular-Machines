package de.nedelosk.modularmachines.common.modules.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;

public class ModuleLoadManager {
	
	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(IModule.class, new ModuleReader()).registerTypeAdapter(IModuleContainer.class, new ModuleContainerReader()).create();
	
	public static void loadModules(){
	}
	
	public static void loadModuleContainers(){
		
	}
	
}
