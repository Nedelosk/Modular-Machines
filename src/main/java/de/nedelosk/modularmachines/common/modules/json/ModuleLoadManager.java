package de.nedelosk.modularmachines.common.modules.json;

import java.lang.reflect.Type;
import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.json.IModuleLoader;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class ModuleLoadManager {

	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(IModule.class, new ModuleReader()).registerTypeAdapter(List.class, new ModuleContainerReader()).create();
	public static ModuleLoader DEFAULT = new ModuleLoader();
	public static BiMap<String, IModuleLoader> loaders = HashBiMap.create();
	
	public static void loadModules(){
	}

	public static void loadModuleContainers(){

	}
	
	private static class ModuleReader implements JsonDeserializer<IModule>{

		@Override
		public IModule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject object = json.getAsJsonObject();
			IModuleLoader loader = null;
			if(object.has("loader") && object.get("loader").isJsonPrimitive() && object.get("loader").getAsJsonPrimitive().isString()){
				loader = loaders.get(new ResourceLocation(object.get("loader").getAsString()));
			}

			if(loader == null){
				loader = DEFAULT;
			}
			return loader.loadModuleFromJson(object, typeOfT, context);
		}
	}
	
	private static class ModuleContainerReader implements JsonDeserializer<List>{

		@Override
		public List<IModuleContainer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject object = json.getAsJsonObject();
			IModuleLoader loader = null;
			if(object.has("loader") && object.get("loader").isJsonPrimitive() && object.get("loader").getAsJsonPrimitive().isString()){
				loader = loaders.get(new ResourceLocation(object.get("loader").getAsString()));
			}

			if(loader == null){
				loader = DEFAULT;
			}
			return loader.loadContainerFromJson(object, typeOfT, context);
		}
	}

}
