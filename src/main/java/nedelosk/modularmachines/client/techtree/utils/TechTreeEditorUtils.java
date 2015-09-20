package nedelosk.modularmachines.client.techtree.utils;

import java.lang.reflect.Type;
import java.util.HashMap;
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

import nedelosk.modularmachines.client.techtree.utils.language.TechTreeEntryLanguageData;
import nedelosk.modularmachines.client.techtree.utils.language.editor.TechTreeEntryEditorLanguageData;
import nedelosk.modularmachines.common.config.TechTreeConfigs;
import net.minecraft.util.StatCollector;

public class TechTreeEditorUtils {

	public static class TechTreeEditorLanguage{
		public static class Serializer implements JsonSerializer<TechTreeEntryEditorLanguageData>{

			public JsonElement writeEntryLanguageData(TechTreeEntryEditorLanguageData src){
				JsonObject object = new JsonObject();
				for(Map.Entry<String, TechTreeEntryLanguageData> entry : src.languageData.entrySet()){
					JsonObject languageO = new JsonObject();
					if(entry.getValue().name == null)
						languageO.addProperty("name", StatCollector.translateToLocal(src.defaultData.name));
					else
						languageO.addProperty("name", StatCollector.translateToLocal(entry.getValue().name));
					if(entry.getValue().text == null)
						languageO.addProperty("text", StatCollector.translateToLocal(src.defaultData.text));
					else
						languageO.addProperty("text", StatCollector.translateToLocal(entry.getValue().text));
					if(src.defaultData.pages != null)
					{
						JsonArray pages = new JsonArray();
						for(int i = 0;i < src.defaultData.pages.length;i++){
							String page = entry.getValue().pages[i];
							if(page == null)
								page = src.defaultData.pages[i];
							pages.add(new JsonPrimitive(page));
						}
						languageO.add("pages", pages);
					}
					object.add(entry.getKey(), languageO);
				}
				
				return object;
			}
			
			@Override
			public JsonElement serialize(TechTreeEntryEditorLanguageData src, Type typeOfSrc, JsonSerializationContext context) {
				return writeEntryLanguageData(src);
			}
			
		}
		
		public static class Deserializer implements JsonDeserializer<TechTreeEntryEditorLanguageData>{

			public TechTreeEntryEditorLanguageData parseEntryLanguageData(JsonElement json){
					HashMap<String, TechTreeEntryLanguageData> languageData = new HashMap<String, TechTreeEntryLanguageData>();
					for(Map.Entry<String, Boolean> entry : TechTreeConfigs.activeLanguages.entrySet()){
						if(!entry.getValue())
							continue;
						String language  = entry.getKey();
						if(!json.getAsJsonObject().has(language)){
							language = "en_US";
						}	
						JsonObject object = json.getAsJsonObject().get(language).getAsJsonObject();
						JsonObject objectUS = null;
						if(!language.equals("en_US"))
							objectUS = json.getAsJsonObject().get("en_US").getAsJsonObject();
						String key;
						if(object.get("name").getAsString().contains("mm.techtree_entry_name.") && objectUS != null){
							key = objectUS.get("name").getAsString();
						}
						else{
							key = object.get("name").getAsString();
						}
						String text;
						if(object.get("text").getAsString().contains("mm.techtree_entry_text.") && objectUS != null){
							text = objectUS.get("text").getAsString();
						}
						else{
							text = object.get("text").getAsString();
						}
						String[] pages = null;
						if(object.has("pages")){
						JsonArray pagesArray = object.get("pages").getAsJsonArray();
						pages = new String[pagesArray.size()];
						int i = 0;
						Iterator<JsonElement> iterator = pagesArray.iterator();
						while(iterator.hasNext()){
							JsonElement element = iterator.next();
							if(element.getAsString().contains("mm.techtree_page.") && objectUS != null)
							{
								pages[i] = objectUS.get("pages").getAsJsonArray().get(i).getAsString();
							}
							else{
								pages[i] = element.getAsString();
							}
							i++;
						}
					}
						languageData.put(language, new TechTreeEntryLanguageData(key, text, pages));
				}
				return new TechTreeEntryEditorLanguageData(null, languageData);
			}
			
			@Override
			public TechTreeEntryEditorLanguageData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				return parseEntryLanguageData(json);
			}
			
		}
	}
	
}
