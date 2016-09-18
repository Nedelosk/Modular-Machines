package de.nedelosk.modularmachines.common.modules.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.material.MaterialRegistry;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.items.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.json.ICustomLoader;
import de.nedelosk.modularmachines.api.modules.json.ModuleLoaderRegistry;
import de.nedelosk.modularmachines.api.property.JsonUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DefaultLoaders {

	public static class ContainerLoader implements ICustomLoader{

		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourcePath().contains("default");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			IModule module = null;
			IMaterial material = null;
			IModuleProperties properties = null;
			ItemStack stack = null;
			boolean ignorNBT = false;
			List<String> tooltip = new ArrayList<>();

			String moduleName = JsonUtils.getString(jsonObject.get("module"));
			if(moduleName != null){
				module = ModuleManager.MODULES.getValue(new ResourceLocation(moduleName));
			}
			material = MaterialRegistry.getMaterial(JsonUtils.getString(jsonObject.get("material")));
			if(JsonUtils.getString(jsonObject.get("item")) != null){
				stack = JsonUtils.parseItemStack(jsonObject, "item");
			}
			if(jsonObject.has("properties") && jsonObject.get("properties").isJsonObject()){
				properties = ModuleLoaderRegistry.loadPropertiesFromJson(jsonObject.get("properties").getAsJsonObject());
			}
			if(jsonObject.has("tooltip")){
				if(jsonObject.get("tooltip").isJsonArray()){
					JsonArray array = jsonObject.get("tooltip").getAsJsonArray();
					for(JsonElement entry : array){
						if(entry.isJsonPrimitive() && entry.getAsJsonPrimitive().isString()){
							tooltip.add(entry.getAsString());
						}
					}
				}else if(jsonObject.get("tooltip").isJsonPrimitive() && jsonObject.get("tooltip").getAsJsonPrimitive().isString()){
					tooltip.add(jsonObject.get("tooltip").getAsString());
				}
			}
			if(jsonObject.has("ignorNBT") && jsonObject.get("ignorNBT").isJsonPrimitive() && jsonObject.get("ignorNBT").getAsJsonPrimitive().isBoolean()){
				ignorNBT = jsonObject.get("ignorNBT").getAsBoolean();
			}

			if(module != null && material != null){
				return new ModuleContainer(module, properties, stack, material, tooltip, ignorNBT);
			}
			return null;
		}

	}
}
