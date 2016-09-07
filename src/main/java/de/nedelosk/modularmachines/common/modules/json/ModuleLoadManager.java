package de.nedelosk.modularmachines.common.modules.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.json.EnumLoaderType;
import de.nedelosk.modularmachines.api.modules.json.ModuleLoaderRegistry;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleLoadManager {

	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(IModule.class, new ModuleReader()).registerTypeAdapter(List.class, new ModuleContainerReader()).create();

	public ModuleLoadManager() {
		ModuleLoaderRegistry.registerLoader(EnumLoaderType.CONTAINER, ModuleLoaderRegistry.defaultContainerLoader = new DefaultLoaders.ContainerLoader());

		ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, new DefaultPropertiesLoader.BoilerPropertiesLoader());
		ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, new DefaultPropertiesLoader.CasingPropertiesLoader());
		ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, new DefaultPropertiesLoader.ControllerPropertiesLoader());
		ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, new DefaultPropertiesLoader.HeaterPropertiesLoader());
		ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, new DefaultPropertiesLoader.KineticPropertiesLoader());
		ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, new DefaultPropertiesLoader.MachinePropertiesLoader());
		ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, new DefaultPropertiesLoader.ModuleStoragePropertiesLoader());
	}

	public static void loadModules(){
	}

	public static void loadModuleContainers(){
		try{
			File containerFolder = new File(ModularMachines.configFolder, "containers");
			if(!containerFolder.exists()){
				containerFolder.mkdirs();
			}else{
				for(File file : containerFolder.listFiles()){
					if(file.getName().endsWith(".json")){
						Reader reader = new BufferedReader(new FileReader(file));
						List<IModuleContainer> containers = GSON.fromJson(reader, List.class);
						for(IModuleContainer container : containers){
							GameRegistry.register(container);
						}
						reader.close();
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private static class ModuleReader implements JsonDeserializer<IModule>{
		@Override
		public IModule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return ModuleLoaderRegistry.loadModuleFromJson(json.getAsJsonObject());
		}
	}

	private static class ModuleContainerReader implements JsonDeserializer<List>{
		@Override
		public List<IModuleContainer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return ModuleLoaderRegistry.loadContainersFromJson(json);
		}
	}

}
