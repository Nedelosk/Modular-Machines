package de.nedelosk.modularmachines.common.modules.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.json.IModuleLoader;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class ModuleLoader extends IForgeRegistryEntry.Impl<IModuleLoader> implements IModuleLoader {

	@Override
	public IModule loadModuleFromJson(JsonObject jsonObject, Type typeOfT, JsonDeserializationContext context) {
		return null;
	}

	@Override
	public IModuleContainer loadContainerFromJson(JsonObject jsonObject, Type typeOfT, JsonDeserializationContext context) {
		return null;
	}

}
