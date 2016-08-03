package de.nedelosk.modularmachines.common.modules.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.material.MaterialRegistry;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.json.IModuleLoader;
import de.nedelosk.modularmachines.api.property.JsonUtils;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import de.nedelosk.modularmachines.common.items.ItemModule;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class ModuleLoader implements IModuleLoader {

	@Override
	public IModule loadModuleFromJson(JsonObject jsonObject, Type typeOfT, JsonDeserializationContext context) {
		return null;
	}

	@Override
	public List<IModuleContainer> loadContainerFromJson(JsonObject jsonObject, Type typeOfT, JsonDeserializationContext context) {
		if(jsonObject.isJsonArray()){
			List<IModuleContainer> containers = new ArrayList<>();
			JsonArray array = jsonObject.getAsJsonArray();
			for(JsonElement entry : array){
				if(entry.isJsonObject()){
					IModuleContainer container = readContainerFromJson(jsonObject);
					if(container != null){
						if(containers == null){
							containers = new ArrayList<>();
						}
						containers.add(container);
					}
				}
			}
			return containers;
		}else{
			IModuleContainer container = readContainerFromJson(jsonObject);
			if(container != null){
				return Collections.singletonList(container);
			}
		}
		return null;
	}
	
	private static IModuleContainer readContainerFromJson(JsonObject jsonObject){
		IModule module = null;
		IMaterial material = null;
		ItemStack stack = null;
		boolean ignorNBT = false;
		List<String> tooltip = null;
		
		if(jsonObject.has("module") && jsonObject.get("module").isJsonPrimitive() && jsonObject.get("module").getAsJsonPrimitive().isString()){
			String moduleName = jsonObject.get("module").getAsString();
			if(moduleName != null){
				module = ModularMachines.iModuleRegistry.getValue(new ResourceLocation(moduleName));
			}
		}
		if(jsonObject.has("material") && jsonObject.get("material").isJsonPrimitive() && jsonObject.get("material").getAsJsonPrimitive().isString()){
			material = MaterialRegistry.getMaterial(jsonObject.get("material").getAsString());
		}
		if(jsonObject.has("item") && jsonObject.get("item").isJsonPrimitive() && jsonObject.get("item").getAsJsonPrimitive().isString()){
			String item = jsonObject.get("item").getAsString();
			if(item.equals("default")){
				if(module != null && material != null){
					stack = ItemModule.registerAndCreateStack(module, material);
				}
			}else{
				stack = JsonUtils.parseItem(jsonObject, "item");
			}
		}
		if(jsonObject.has("tooltip")){
			if(jsonObject.get("tooltip").isJsonArray()){
				JsonArray array = jsonObject.get("tooltip").getAsJsonArray();
				for(JsonElement entry : array){
					if(entry.isJsonPrimitive() && entry.getAsJsonPrimitive().isString()){
						if(tooltip == null){
							tooltip = new ArrayList<>();
						}
						tooltip.add(entry.getAsString());
					}
				}
			}else if(jsonObject.get("tooltip").isJsonPrimitive() && jsonObject.get("tooltip").getAsJsonPrimitive().isString()){
				tooltip = Collections.singletonList(jsonObject.get("tooltip").getAsString());
			}
		}
		if(jsonObject.has("ignorNBT") && jsonObject.get("ignorNBT").isJsonPrimitive() && jsonObject.get("ignorNBT").getAsJsonPrimitive().isBoolean()){
			ignorNBT = jsonObject.get("ignorNBT").getAsBoolean();
		}
		
		if(module != null && material != null && stack != null){
			return new ModuleContainer(module, stack, material, tooltip, ignorNBT);
		}
		return null;
	}

}
