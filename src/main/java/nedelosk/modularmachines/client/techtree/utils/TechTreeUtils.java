package nedelosk.modularmachines.client.techtree.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
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
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.basic.techtree.TechPointStack;
import nedelosk.modularmachines.api.basic.techtree.TechPointTypes;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategoryList;
import nedelosk.modularmachines.api.basic.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.basic.techtree.TechTreePage;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.nedeloskcore.api.Log;
import nedelosk.nedeloskcore.utils.JsonUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class TechTreeUtils {
	
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(TechTreeEntry.class, new TechTreeEntryUtils.Deserializer()).registerTypeAdapter(TechTreeEntry.class, new TechTreeEntryUtils.Serializer()).registerTypeAdapter(TechTreeCategoryList.class, new TechTreeCategoryUtils.Deserializer()).registerTypeAdapter(TechTreeCategoryList.class, new TechTreeCategoryUtils.Serializer()).create();
	
	public static HashMap<String, String> pages = new HashMap<String, String>();
	
	public static void readTechPoints(){
		try{
			File file = new File(ModularMachines.configFolder + "/techtree", "techpoints.json");
			if(!file.exists()){
				file.createNewFile();
			}
			JsonReader reader = new JsonReader( new FileReader(file) );
			JsonObject object = Streams.parse(reader).getAsJsonObject();
			if(object.has("points") && object.get("points").isJsonArray()){
				JsonArray array = object.get("points").getAsJsonArray();
				Iterator<JsonElement> iterator = array.iterator();
				while(iterator.hasNext()){
					JsonElement element = iterator.next();
					ItemStack stack = JsonUtils.parseItem(element, "item");
					JsonArray types = null;
					ArrayList<TechPointStack> typeList = new ArrayList<TechPointStack>();
					if(element.getAsJsonObject().has("types") && element.getAsJsonObject().get("types").isJsonArray()){
						types = element.getAsJsonObject().get("types").getAsJsonArray();
						Iterator<JsonElement> iteratorTypes = array.iterator();
						while(iterator.hasNext()){
							JsonElement type = iteratorTypes.next();
							int typeID = 0;
							if(type.getAsJsonObject().has("type")){
								typeID = type.getAsJsonObject().get("type").getAsInt();
							}
							int points = 0;
							if(type.getAsJsonObject().has("points")){
								points = type.getAsJsonObject().get("points").getAsInt();
							}
							TechPointTypes techType = TechPointTypes.values()[typeID];
							typeList.add(new TechPointStack(points, techType));
						}
					}
					ModularMachinesApi.addTechPointsToItem(stack, typeList.toArray(new TechPointStack[typeList.size()]));
				}
			}
		}catch(Exception e){
			
		}
	}
	
	public static void checkJsonData()
	{
		File techTreeFile = new File(ModularMachines.configFolder, "techtree");
		File categorysFile = new File(techTreeFile, "categorys");
		try{
		if(!categorysFile.exists()){
			categorysFile.mkdirs();
			categorysFile.createNewFile();
		}
			Writer.checkAndWriteJsonData(categorysFile);
			Reader.checkAndReadJsonData(categorysFile);
		}catch(Exception e){
			e.printStackTrace();
			Log.log("ModularMachines", Level.ERROR, "Fail to parse entrys");
		}
	}
	
	public static class Writer{
		
		private static void checkAndWriteJsonData(File categorysFile) throws IOException{
			for(TechTreeCategoryList category : TechTreeCategories.entryCategoriesTemp.values()){
				File categoryFile = new File(categorysFile, category.key.toLowerCase(Locale.ENGLISH) + ".json");
				if(!categoryFile.exists())
				{
					categoryFile.createNewFile();
				}
				writeCategoryList(category, categoryFile);
				File categoryFolder = new File(categorysFile, category.key);
				if(!categoryFolder.exists())
				{
					categoryFolder.mkdirs();
					categoryFolder.createNewFile();
				}
				writeEntrys(category, categoryFolder);
			}
		}
		
		public static void writeCategoryList(TechTreeCategoryList category, File categoryFile) throws IOException{
			BufferedWriter writerCategory = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(categoryFile)));
			writerCategory.write(GSON.toJson(category));
			writerCategory.close();
		}
		
		private static void writeEntrys(TechTreeCategoryList category, File categoryFolder) throws IOException{
			for(TechTreeEntry entry : category.entrys.values()){
				writeEntry(entry, categoryFolder);
			}
		}
		
		public static void writeEntry(TechTreeEntry entry, File categoryFolder) throws IOException{
			File entryFile = new File(categoryFolder, entry.key.toLowerCase(Locale.ENGLISH) + ".json");
			if(!entryFile.exists())
			{
				entryFile.createNewFile();
				BufferedWriter writerEntry = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entryFile)));
				String s = GSON.toJson(entry);
				writerEntry.write(GSON.toJson(entry));
				writerEntry.close();
			}
		}
	}
	
	public static class Reader{
		
		private static void checkAndReadJsonData(File categorysFile) throws IOException{
			File[] files = categorysFile.listFiles();
			for(File file : files){
				if(file.getName().toLowerCase(Locale.ENGLISH).contains(".json") && !file.getName().toLowerCase(Locale.ENGLISH).contains("_language")){
					TechTreeCategoryList category = Reader.readCategory(file);
					if(category != null){
						File entryFile = new File(categorysFile, category.key);
						if(!entryFile.exists())
							Writer.checkAndWriteJsonData(categorysFile);
						Reader.readEntrys(entryFile);
					}
				}
			}
		}
		
		private static TechTreeCategoryList readCategory(File file) throws IOException{
			JsonReader reader = new JsonReader( new FileReader(file) );
			TechTreeCategoryList category = GSON.fromJson(reader, TechTreeCategoryList.class);
			reader.close();
			for(TechTreeCategoryList categoryExisting : TechTreeCategories.entryCategoriesTemp.values()){
				if(category.key.equals(categoryExisting.key))
				{
					return categoryExisting;
				}
			}
			TechTreeCategories.registerCategory(category.key, category.icon, category.background);
			return category;
		}
		
		private static void readEntrys(File file){
			File[] files = file.listFiles();
			for(File entryFile : files){
				try{
					if(!entryFile.getName().contains("_language"))
						TechTreeCategories.addEntry(readEntry(entryFile));
				}catch(Exception e){
					Log.log("ModularMachines", Level.ERROR, "Fail to parse : " + file.getName() +  " / " + entryFile.getName());
					continue;
				}
			}
		}
		
		public static TechTreeEntry readEntry(File file) throws IOException{
			JsonReader reader = new JsonReader(new FileReader(file));
			TechTreeEntry entry = GSON.fromJson(reader, TechTreeEntry.class);
			reader.close();
			return entry;
		}
		
	}
	
	public static class TechTreeCategoryUtils{
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
	
	public static class TechTreeEntryUtils{
		
		public static class Deserializer implements JsonDeserializer<TechTreeEntry>{
			
			public TechTreeEntry parseEntry(JsonElement json){
				JsonObject object = json.getAsJsonObject();
				String key = object.get("key").getAsString();
				String category = object.get("category").getAsString();
				String[] parents = null;
				if(json.getAsJsonObject().has("parents"))
				{
					parents = new String[object.get("parents").getAsJsonArray().size()];
					Iterator<JsonElement> iterator = object.get("parents").getAsJsonArray().iterator();
					for(int i = 0;i < parents.length;i++)
					{
						JsonElement element = iterator.next();
						parents[i] = element.getAsString();
					}
				}
				String[] siblings = null;
				if(json.getAsJsonObject().has("siblings"))
				{
					siblings = new String[object.get("siblings").getAsJsonArray().size()];
					for(int i = 0;i < siblings.length;i++)
					{
						JsonElement element = object.get("siblings").getAsJsonArray().iterator().next();
						siblings[i] = element.getAsString();
					}
				}
				int displayColumn = json.getAsJsonObject().get("column").getAsInt();
				int displayRow = json.getAsJsonObject().get("row").getAsInt();
				TechTreePage[] pages = null;
				if(json.getAsJsonObject().has("pages"))
				{
					pages = parsePages(json);
				}
				ItemStack iconItem = null;
				ResourceLocation icon = null;
				if(json.getAsJsonObject().has("iconItem"))
				{
					iconItem = JsonUtils.parseItem(json, "iconItem");
				}
				else if(json.getAsJsonObject().has("icon"))
				{
					icon = JsonUtils.parseLocation(json, "icon");
				}
				int techPoints = 0;
				if(json.getAsJsonObject().has("techpoints"))
				{
					techPoints = json.getAsJsonObject().get("techpoints").getAsInt();
				}
				TechPointTypes techPointType = null;
				if(json.getAsJsonObject().has("techpointTypes"))
				{
					techPoints = json.getAsJsonObject().get("techpoints").getAsInt();
				}
				if(techPointType == null)
				{
					techPointType = TechPointTypes.EASY;
				}
				boolean isAutoUnlock;
				if(json.getAsJsonObject().has("isAutoUnlock"))
				{
					isAutoUnlock = json.getAsJsonObject().get("isAutoUnlock").getAsBoolean();
				}
				else
					isAutoUnlock = false;
				boolean isConcealed;
				if(json.getAsJsonObject().has("isConcealed"))
				{
					isConcealed = json.getAsJsonObject().get("isConcealed").getAsBoolean();
				}
				else
					isConcealed = false;
				TechTreeEntry entry = new TechTreeEntry(key, category, techPoints, techPointType, displayColumn, displayRow, icon);
				if(isAutoUnlock)
					entry.isAutoUnlock();
				if(isConcealed)
					entry.isConcealed();
				if(iconItem != null)
					entry.icon_item = iconItem;
				if(pages != null)
					entry.setPages(pages);
				if(parents != null)
					entry.setParents(parents);
				if(siblings != null)
					entry.setSiblings(siblings);
				return entry;
			}
			
			public TechPointTypes parseTechPointType(JsonElement json)
			{
				for(int i = 0;i < TechPointTypes.values().length;i++)
				{
					TechPointTypes type = TechPointTypes.values()[i];
					if(type.name().toLowerCase().equals(json.getAsJsonObject().get("techpointTypes").getAsString()))
						return type;
				}
				return null;
			}
			
			public TechTreePage[] parsePages(JsonElement json){
				JsonObject object = json.getAsJsonObject();
				if(object.get("pages").isJsonArray())
				{
					JsonArray array = object.get("pages").getAsJsonArray();
					TechTreePage[] pages = new TechTreePage[array.size()];
					Iterator<JsonElement> iterator = array.iterator();
					for(int i = 0;i < pages.length;i++)
					{
						JsonElement element = iterator.next();
						pages[i] = parsePage(element, i);
					}
					return pages;
				}
				else
					return null;
			}
			
			public TechTreePage parsePage(JsonElement json, int pages){
				JsonObject object = json.getAsJsonObject();
				switch (object.get("type").getAsString()) {
				case "text":
					return new TechTreePage(object.get("text").getAsString(), pages);
				case "image":
					return new TechTreePage(JsonUtils.parseLocation(json, "image"), object.get("caption").getAsString(), pages);
				case "text_concealed":
					return new TechTreePage(object.get("entry").getAsString(), object.get("text").getAsString(), pages);
				case "smelting":
					return new TechTreePage(JsonUtils.parseItem(json, "input"), pages);
				case "crafting":
					return new TechTreePage(parseRecipe(json, "output"), pages);
				default:
					return null;
				}
			}
			
			public IRecipe parseRecipe(JsonElement json, String itemName){
	            for (Object craft : CraftingManager.getInstance().getRecipeList()) {
	                if (craft instanceof IRecipe) {
	                    IRecipe theCraft = (IRecipe) craft;
	                    if (theCraft.getRecipeOutput() != null && areEqual(theCraft.getRecipeOutput(), JsonUtils.parseItem(json, itemName))) {
	                        return theCraft;
	                    }
	                }
	            }
	            return null;
			}
			
		    public static boolean areEqual(ItemStack stack, ItemStack stack2) {
		        if (stack == null || stack2 == null) return false;
		    	return stack.isItemEqual(stack2);
		    }
			
			@Override
			public TechTreeEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				return parseEntry(json);
			}
		}
		
		public static class Serializer implements JsonSerializer<TechTreeEntry>{
			
			public JsonElement writeEntry(TechTreeEntry entry){
				JsonObject object = new JsonObject();
				object.addProperty("key", entry.key);
				object.addProperty("category", entry.category);
				if(entry.parents != null){
					JsonArray parents = new JsonArray();
					for(String parent : entry.parents){
						parents.add(new JsonPrimitive(parent));
					}
					object.add("parents", parents);
				}
				if(entry.siblings != null){
				JsonArray siblings = new JsonArray();
				for(String sibling : entry.siblings){
					siblings.add(new JsonPrimitive(sibling));
				}
				object.add("siblings", siblings);
				}
				object.addProperty("column", entry.displayColumn);
				object.addProperty("row", entry.displayRow);
				if(entry.pages != null)
					object.add("pages", writePages(entry.pages));
				if(entry.icon_item != null){
					object.add("iconItem", JsonUtils.writeItem(entry.icon_item));
				}
				else if(entry.icon_resource != null){
					object.add("icon", JsonUtils.writeLocation(entry.icon_resource));
				}
				object.addProperty("techpoints", entry.getTechPoints());
				object.add("techpointTypes", writeTechPointType(entry.getTechPointType()));
				object.addProperty("isAutoUnlock", entry.isAutoUnlock() ? true : false);
				object.addProperty("isConcealed", entry.isConcealed() ? true : false);
				return object;
			}
			
			public JsonElement writeTechPointType(TechPointTypes type)
			{
				return new JsonPrimitive(type.name().toLowerCase());
			}
			
			public JsonElement parseRecipe(IRecipe recipe){
	            return JsonUtils.writeItem(recipe.getRecipeOutput());
	            
			}
			
			public JsonArray writePages(TechTreePage[] pages){
				JsonArray array = new JsonArray();
				for(TechTreePage page : pages)
					array.add(writePage(page));
				return array;
			}
			
			public JsonElement writePage(TechTreePage page){
				JsonObject object = new JsonObject();
				String type;
				switch (page.type) {
				case TEXT:
					object.addProperty("text", page.text);
					type = "text";
					break;
				case IMAGE:
					object.add("text", JsonUtils.writeLocation(page.image));
					type = "image";
					break;
				case TEXT_CONCEALED:
					object.addProperty("text", page.text);
					type = "text_concealed";
					break;
				case SMELTING:
					object.add("input", JsonUtils.writeItem((ItemStack) page.recipe));
					type = "smelting";
					break;
				case NORMAL_CRAFTING:
					object.add("output", JsonUtils.writeItem(page.recipeOutput));
					type = "crafting";
					break;
				default:
					object.addProperty("text", page.text);
					type = "text";
					break;
				}
				JsonPrimitive primitive = new JsonPrimitive(type);
				object.add("type", primitive);
				return object;
			}
			
			@Override
			public JsonElement serialize(TechTreeEntry src, Type typeOfSrc, JsonSerializationContext context) {
				return writeEntry(src);
			}
		}
	}
	
}
