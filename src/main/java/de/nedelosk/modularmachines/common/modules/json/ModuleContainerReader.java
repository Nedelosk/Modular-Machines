package de.nedelosk.modularmachines.common.modules.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleLoader;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import net.minecraft.util.ResourceLocation;

public class ModuleContainerReader implements JsonDeserializer<IModuleContainer>{

	@Override
	public IModuleContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		IModuleLoader loader = null;
		if(object.has("loader") && object.get("loader").isJsonPrimitive() && object.get("loader").getAsJsonPrimitive().isString()){
			loader = ModularMachines.iModuleLoaderRegistry.getValue(new ResourceLocation(object.get("loader").getAsString()));
		}
		
		if(loader == null){
			loader = ModularMachines.iModuleLoaderRegistry.getValue(new ResourceLocation("modularmachines:default"));
		}
		return loader.loadContainerFromJson(object, typeOfT, context);
	}
}
