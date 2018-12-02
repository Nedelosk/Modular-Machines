package modularmachines.common.modules.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;

import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IComponentFactory;
import modularmachines.api.modules.components.IComponentParser;
import modularmachines.common.core.Constants;

public class ModuleBlock {
	public final Data data;
	public final IComponentFactory[] components;
	public final ItemStack[] types;
	
	public ModuleBlock(Data data, IComponentFactory[] components, ItemStack[] types) {
		this.data = data;
		this.components = components;
		this.types = types;
	}
	
	public static class Deserializer implements JsonDeserializer<ModuleBlock> {
		@Override
		public ModuleBlock deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			if (!json.isJsonObject()) {
				return null;
			}
			JsonObject object = json.getAsJsonObject();
			Data data = context.deserialize(JsonUtils.getJsonObject(object, "data"), Data.class);
			IComponentFactory[] components = parseComponents(JsonUtils.getJsonArray(object, "components"));
			ItemStack[] types = parseTypes(JsonUtils.getJsonArray(object, "types"));
			return new ModuleBlock(data, components, types);
		}
		
		private IComponentFactory[] parseComponents(JsonArray jsonElements) {
			IComponentFactory[] components = new IComponentFactory[jsonElements.size()];
			for (int i = 0; i < jsonElements.size(); i++) {
				JsonElement element = jsonElements.get(i);
				components[i] = parseComponent(element);
			}
			return components;
		}
		
		private IComponentFactory parseComponent(JsonElement element) {
			if (element.isJsonNull()) {
				throw new JsonSyntaxException("Json cannot be null");
			}
			if (!element.isJsonObject()) {
				throw new JsonSyntaxException("Expected component to be a object ");
			}
			JsonObject object = element.getAsJsonObject();
			String componentType = JsonUtils.getString(object, "type", "modularmachines:boundingbox");
			IComponentParser parser = ModuleManager.registry.getParser(new ResourceLocation(componentType));
			if (parser == null) {
				throw new JsonSyntaxException("Unknown component parser type: " + componentType);
			}
			return parser.parse(object);
		}
		
		private ItemStack[] parseTypes(JsonArray jsonElements) {
			ItemStack[] types = new ItemStack[jsonElements.size()];
			for (int i = 0; i < jsonElements.size(); i++) {
				JsonElement element = jsonElements.get(i);
				types[i] = parseType(element);
			}
			return types;
		}
		
		private ItemStack parseType(JsonElement element) {
			if (element.isJsonNull()) {
				throw new JsonSyntaxException("Json cannot be null");
			}
			if (!element.isJsonObject()) {
				throw new JsonSyntaxException("Expected type to be a object ");
			}
			JsonObject object = element.getAsJsonObject();
			return CraftingHelper.getItemStack(object, new JsonContext(Constants.MOD_ID));
		}
	}
	
	public static class Data {
		public String name;
		public String registryName;
		public int complexity;
		public String model;
		public String[] positions;
	}
}
