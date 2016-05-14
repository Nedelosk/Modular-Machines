package de.nedelosk.forestmods.library.modules.json;

import com.google.gson.JsonObject;

import de.nedelosk.forestmods.library.modules.IModule;

public interface IModuleLoader {
	
	String getName();
	
	IModule loadFromJson(JsonObject jsonObject);
}
