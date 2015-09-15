package nedelosk.modularmachines.api.basic.techtree;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import nedelosk.nedeloskcore.utils.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class TechTreeCategoryList {
	
    public int minDisplayColumn;

    public int minDisplayRow;

    public int maxDisplayColumn;

    public int maxDisplayRow;
    
    public ResourceLocation icon;
    public ResourceLocation background;
    public String key;
	
	public TechTreeCategoryList(String key, ResourceLocation icon, ResourceLocation background) {
		this.key = key;
		this.icon = icon;
		this.background = background;
	}

	public Map<String, TechTreeEntry> entrys = new HashMap<String, TechTreeEntry>();
	
	public static class Deserializer implements JsonDeserializer<TechTreeCategoryList>{

		public TechTreeCategoryList parseCategory(JsonElement json){
			String key = json.getAsJsonObject().get("key").getAsString();
			ResourceLocation icon = JsonUtils.parseLocation(json, "icon");
			ResourceLocation background = JsonUtils.parseLocation(json, "background");
			return new TechTreeCategoryList(key, icon, background);
		}
		
		@Override
		public TechTreeCategoryList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return parseCategory(json);
		}
		
	}
	
	public static class Serializer implements JsonSerializer<TechTreeCategoryList>{
		
		@Override
		public JsonElement serialize(TechTreeCategoryList src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject object = new JsonObject();
			object.addProperty("key", src.key);
			object.addProperty("icon", src.icon.toString().replace("textures/", ""));
			object.addProperty("background", src.background.toString().replace("textures/", ""));
			return object;
		}
	}
}
