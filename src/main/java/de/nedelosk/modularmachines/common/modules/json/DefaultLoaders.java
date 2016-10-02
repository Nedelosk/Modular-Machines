package de.nedelosk.modularmachines.common.modules.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.material.MaterialRegistry;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.ModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.json.ICustomLoader;
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
			EnumModuleSizes size = JsonUtils.getSize(jsonObject);
			IMaterial material = null;
			ItemStack stack = null;
			boolean ignorNBT = false;
			List<String> tooltip = new ArrayList<>();
			IModuleContainer[] containers = null;

			JsonArray containerArray = JsonUtils.getArray(jsonObject.get("containers"));
			if(containerArray != null){
				List<IModuleContainer> containerList = new ArrayList<>();
				for(JsonElement ele : containerArray){
					if(ele instanceof JsonObject) {
						IModuleContainer container = JsonUtils.getContainer(ele.getAsJsonObject());
						if(container != null){
							containerList.add(container);
						}
					}
				}
				if(!containerList.isEmpty()){
					containers = containerList.toArray(new IModuleContainer[containerList.size()]);
				}
			}else{
				IModuleContainer container = JsonUtils.getContainer(jsonObject);
				if(container != null){
					containers = new IModuleContainer[]{container};
				}
			}
			material = MaterialRegistry.getMaterial(JsonUtils.getString(jsonObject.get("material")));
			if(JsonUtils.getString(jsonObject.get("item")) != null){
				stack = JsonUtils.parseItemStack(jsonObject, "item");
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

			if(containers != null && material != null){
				return new ModuleItemContainer(stack, material, size, tooltip, ignorNBT, containers);
			}
			return null;
		}

	}
}
