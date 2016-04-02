package de.nedelosk.techtree.utils.language;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.utils.Log;
import de.nedelosk.techtree.api.TechTreeApi;
import de.nedelosk.techtree.api.TechTreeCategories;
import de.nedelosk.techtree.api.TechTreeCategoryList;
import de.nedelosk.techtree.api.TechTreeEntry;
import de.nedelosk.techtree.api.TechTreePage;
import de.nedelosk.techtree.api.language.ILanguageCategoryData;
import de.nedelosk.techtree.api.language.ILanguageData;
import de.nedelosk.techtree.api.language.ILanguageEntryData;
import de.nedelosk.techtree.api.language.ILanguageManager;
import de.nedelosk.techtree.common.TechTree;
import de.nedelosk.techtree.common.config.TechTreeConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class LanguageManager implements ILanguageManager {

	public static LanguageManager instance;
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
			.registerTypeAdapter(TechTreeEntryLanguageData.class, new TechTreeEntryLanguage.Serializer())
			.registerTypeAdapter(TechTreeEntryLanguageData.class, new TechTreeEntryLanguage.Deserializer())
			.registerTypeAdapter(TechTreeCategoryLanguageData.class, new TechTreeCategoryLanguage.Serializer())
			.registerTypeAdapter(TechTreeCategoryLanguageData.class, new TechTreeCategoryLanguage.Deserializer()).create();
	public static LinkedHashMap<String, ILanguageData> languageData = new LinkedHashMap<String, ILanguageData>();

	public static void init() {
		if (instance == null) {
			instance = new LanguageManager();
		}
		TechTreeApi.languageManager = instance;
	}

	@Override
	public ILanguageEntryData getTranslateData(TechTreeEntry entry) {
		return (TechTreeEntryLanguageData) getTranslateData(entry.key);
	}

	@Override
	public ILanguageData getTranslateData(String key) {
		return languageData.get(key);
	}

	@Override
	public ILanguageCategoryData getTranslateData(TechTreeCategoryList category) {
		return (TechTreeCategoryLanguageData) getTranslateData(category.key);
	}

	@Override
	public void checkLanguage() {
		if (!Minecraft.getMinecraft().gameSettings.language.equals(TechTreeApi.currentLanguage)) {
			TechTreeApi.currentLanguage = Minecraft.getMinecraft().gameSettings.language;
			updateLanguage();
		}
	}

	@Override
	public void updateLanguage() {
		readLanguageData();
	}

	@Override
	public void readLanguageData() {
		File techTreeFile = new File(TechTree.configFolder, "techtree");
		File categorysFile = new File(techTreeFile, "categorys");
		try {
			readCategorysLanguageData(categorysFile);
		} catch (Exception e) {
			Log.log("ModularMachines", Level.ERROR, "Fail To Load Language Data from the Tech Tree Entrys");
		}
	}

	@Override
	public void readCategorysLanguageData(File categorysFile) throws IOException {
		for ( TechTreeCategoryList category : TechTreeCategories.entryCategoriesTemp.values() ) {
			readCategoryLanguageData(category, categorysFile);
		}
	}

	@Override
	public void readCategoryLanguageData(TechTreeCategoryList category, File categorysFile) throws IOException {
		File categoryFile = new File(categorysFile, category.key);
		for ( TechTreeEntry entry : category.entrys.values() ) {
			readEntryLanguageData(entry, categoryFile);
		}
		File categoryLanguageFile = new File(categorysFile, category.key.toLowerCase(Locale.ENGLISH) + "_language.json");
		JsonReader reader = new JsonReader(new FileReader(categoryLanguageFile));
		TechTreeCategoryLanguageData data = GSON.fromJson(reader, TechTreeCategoryLanguageData.class);
		reader.close();
		languageData.put(category.key, data);
	}

	@Override
	public ILanguageEntryData readEntryLanguageData(TechTreeEntry entry, File categoryFile) throws IOException {
		File entryFile = new File(categoryFile, entry.key.toLowerCase(Locale.ENGLISH) + "_language.json");
		JsonReader reader = new JsonReader(new FileReader(entryFile));
		TechTreeEntryLanguageData data = GSON.fromJson(reader, TechTreeEntryLanguageData.class);
		reader.close();
		languageData.put(entry.key, data);
		return data;
	}

	@Override
	public void writeLanguageData() {
		File techTreeFile = new File(TechTree.configFolder, "techtree");
		File categorysFile = new File(techTreeFile, "categorys");
		try {
			writeCategorysLanguageData(categorysFile);
		} catch (Exception e) {
			Log.log("ModularMachines", Level.ERROR, "Fail To Write Language Data");
		}
	}

	@Override
	public void writeCategorysLanguageData(File categorysFile) throws IOException {
		if (!categorysFile.exists()) {
			categorysFile.mkdirs();
			categorysFile.createNewFile();
		}
		for ( TechTreeCategoryList category : TechTreeCategories.entryCategoriesTemp.values() ) {
			try {
				writeCategoryListLanguageData(category, categorysFile);
			} catch (Exception e) {
				Log.log("ModularMachines", Level.ERROR, "Fail To Write Language Data : " + category.key);
			}
		}
	}

	@Override
	public void writeCategoryListLanguageData(TechTreeCategoryList category, File categorysFile) throws IOException {
		File categoryFile = new File(categorysFile, category.key);
		if (!categoryFile.exists()) {
			categoryFile.mkdirs();
			categoryFile.createNewFile();
		}
		for ( TechTreeEntry entry : category.entrys.values() ) {
			try {
				writeEntryLanguageData(entry, categoryFile);
			} catch (Exception e) {
				Log.log("ModularMachines", Level.ERROR, "Fail To Write Language Data : " + category.key + " / " + entry.key);
			}
		}
		writeCategoryLanguageData(category, categorysFile);
	}

	@Override
	public void writeCategoryLanguageData(TechTreeCategoryList category, File categorysFile) throws IOException {
		File languageFile = new File(categorysFile, category.key.toLowerCase(Locale.ENGLISH) + "_language.json");
		if (!languageFile.exists()) {
			languageFile.createNewFile();
			BufferedWriter writerCategory = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(languageFile)));
			writerCategory.write(GSON.toJson(new TechTreeCategoryLanguageData("mm.entry_category." + category.key)));
			writerCategory.close();
		}
	}

	@Override
	public void writeEntryLanguageData(TechTreeEntry entry, File categoryFile) throws IOException {
		File entryFile = new File(categoryFile, entry.key.toLowerCase(Locale.ENGLISH) + "_language.json");
		boolean exists = false;
		if (!entryFile.exists()) {
			entryFile.createNewFile();
		} else {
			exists = true;
		}
		ArrayList<String> pages = new ArrayList<String>();
		if (entry.getPages() != null) {
			for ( TechTreePage page : entry.getPages() ) {
				pages.add(page.text);
			}
		}
		if (exists) {
			ILanguageEntryData data = readEntryLanguageData(entry, categoryFile);
			if (data == null) {
				Log.log("ModularMachines", Level.ERROR, "Fail To Write Language Data : " + entry.category + " / " + entry.key);
				return;
			}
			if (data.getPages().length == pages.size()) {
				return;
			}
		}
		BufferedWriter writerEntry = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entryFile)));
		writerEntry.write(
				GSON.toJson(new TechTreeEntryLanguageData(entry.getName(), entry.getText(), pages.isEmpty() ? null : pages.toArray(new String[pages.size()]))));
		writerEntry.close();
	}

	public static LanguageManager getInstance() {
		return instance;
	}

	public static class TechTreeEntryLanguage {

		public static class Serializer implements JsonSerializer<TechTreeEntryLanguageData> {

			public JsonElement writeEntryLanguageData(TechTreeEntryLanguageData src) {
				JsonObject object = new JsonObject();
				for ( Map.Entry<String, Boolean> entry : TechTreeConfigs.activeLanguages.entrySet() ) {
					if (entry.getValue()) {
						JsonObject language = new JsonObject();
						language.addProperty("name", StatCollector.translateToLocal(src.name));
						language.addProperty("text", StatCollector.translateToLocal(src.text));
						if (src.pages != null) {
							JsonArray pages = new JsonArray();
							for ( String page : src.pages ) {
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

		public static class Deserializer implements JsonDeserializer<TechTreeEntryLanguageData> {

			public TechTreeEntryLanguageData parseEntryLanguageData(JsonElement json) {
				String language = TechTreeApi.currentLanguage;
				if (!json.getAsJsonObject().has(language)) {
					language = "en_US";
				}
				JsonObject object = json.getAsJsonObject().get(language).getAsJsonObject();
				JsonObject objectUS = null;
				if (!language.equals("en_US")) {
					objectUS = json.getAsJsonObject().get("en_US").getAsJsonObject();
				}
				String key;
				if (object.get("name").getAsString().contains("mm.techtree_entry_name.") && objectUS != null) {
					key = objectUS.get("name").getAsString();
				} else {
					key = object.get("name").getAsString();
				}
				String text;
				if (object.get("text").getAsString().contains("mm.techtree_entry_text.") && objectUS != null) {
					text = objectUS.get("text").getAsString();
				} else {
					text = object.get("text").getAsString();
				}
				String[] pages = null;
				if (object.has("pages")) {
					JsonArray pagesArray = object.get("pages").getAsJsonArray();
					pages = new String[pagesArray.size()];
					int i = 0;
					Iterator<JsonElement> iterator = pagesArray.iterator();
					while (iterator.hasNext()) {
						JsonElement element = iterator.next();
						if (element.getAsString().contains("mm.techtree_page.") && objectUS != null) {
							pages[i] = objectUS.get("pages").getAsJsonArray().get(i).getAsString();
						} else {
							pages[i] = element.getAsString();
						}
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

	public static class TechTreeCategoryLanguage {

		public static class Serializer implements JsonSerializer<TechTreeCategoryLanguageData> {

			public JsonElement writeCatogoryLanguageData(TechTreeCategoryLanguageData src) {
				JsonObject object = new JsonObject();
				for ( Map.Entry<String, Boolean> entry : TechTreeConfigs.activeLanguages.entrySet() ) {
					if (entry.getValue()) {
						JsonObject language = new JsonObject();
						language.addProperty("catogory", StatCollector.translateToLocal(src.catogory));
						object.add(entry.getKey(), language);
					}
				}
				return object;
			}

			@Override
			public JsonElement serialize(TechTreeCategoryLanguageData src, Type typeOfSrc, JsonSerializationContext context) {
				return writeCatogoryLanguageData(src);
			}
		}

		public static class Deserializer implements JsonDeserializer<TechTreeCategoryLanguageData> {

			public TechTreeCategoryLanguageData parseCatogoryLanguageData(JsonElement json) {
				String language = TechTreeApi.currentLanguage;
				if (!json.getAsJsonObject().has(language)) {
					language = "en_US";
				}
				JsonObject object = json.getAsJsonObject().get(language).getAsJsonObject();
				JsonObject objectUS = null;
				if (!language.equals("en_US")) {
					objectUS = json.getAsJsonObject().get("en_US").getAsJsonObject();
				}
				String catogory;
				if (object.get("catogory").getAsString().contains("mm.entry_category.") && objectUS != null) {
					catogory = objectUS.get("catogory").getAsString();
				} else {
					catogory = object.get("catogory").getAsString();
				}
				return new TechTreeCategoryLanguageData(catogory);
			}

			@Override
			public TechTreeCategoryLanguageData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				return parseCatogoryLanguageData(json);
			}
		}
	}
}
