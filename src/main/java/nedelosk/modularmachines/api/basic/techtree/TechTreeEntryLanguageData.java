package nedelosk.modularmachines.api.basic.techtree;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.common.config.TechTreeConfigs;
import net.minecraft.util.StatCollector;

public class TechTreeEntryLanguageData {

	public TechTreeEntryLanguageData(String key, String text, String... pages) {
		this.name = key;
		this.text = text;
		this.pages = pages;
	}
	
	public String name;
	public String text;
	
	public String[] pages;
	
	public static class Serializer implements JsonSerializer<TechTreeEntryLanguageData>{

		public JsonElement writeEntryLanguageData(TechTreeEntryLanguageData src){
			JsonObject object = new JsonObject();
			for(Map.Entry<String, Boolean> entry : TechTreeConfigs.activeLanguages.entrySet()){
				if(entry.getValue()){
					JsonObject language = new JsonObject();
					language.addProperty("name", StatCollector.translateToLocal(src.name));
					language.addProperty("text", StatCollector.translateToLocal(src.text));
					if(src.pages != null)
					{
						JsonArray pages = new JsonArray();
						for(String page : src.pages){
							pages.add(new JsonPrimitive(page));
						}
						language.add("pages", pages);
					}
					object.add(entry.getKey(), language);
				}
			}
			
			return object;
		}
		
		@Override
		public JsonElement serialize(TechTreeEntryLanguageData src, Type typeOfSrc, JsonSerializationContext context) {
			return writeEntryLanguageData(src);
		}
		
	}
	
	public static class Deserializer implements JsonDeserializer<TechTreeEntryLanguageData>{

		public TechTreeEntryLanguageData parseEntryLanguageData(JsonElement json){
			JsonObject object = json.getAsJsonObject().get(ModularMachinesApi.currentLanguage).getAsJsonObject();
			String key = object.get("name").getAsString();
			String text = object.get("text").getAsString();
			String[] pages = null;
			if(object.has("pages")){
			JsonArray pagesArray = object.get("pages").getAsJsonArray();
			pages = new String[pagesArray.size()];
			int i = 0;
			Iterator<JsonElement> iterator = pagesArray.iterator();
			while(iterator.hasNext()){
				JsonElement element = iterator.next();
				pages[i] = element.getAsString();
				i++;
			}
			}
			return new TechTreeEntryLanguageData(key, text, pages);
		}
		
		@Override
		public TechTreeEntryLanguageData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return parseEntryLanguageData(json);
		}
		
	}
}
