package de.nedelosk.modularmachines.api.modules.json;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;

public interface IModuleLoader{

	IModule loadModuleFromJson(JsonObject jsonObject, Type typeOfT, JsonDeserializationContext context);

	List<IModuleContainer> loadContainerFromJson(JsonObject jsonObject, Type typeOfT, JsonDeserializationContext context);
}
