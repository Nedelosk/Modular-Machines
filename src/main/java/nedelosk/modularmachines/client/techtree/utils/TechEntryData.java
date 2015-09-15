package nedelosk.modularmachines.client.techtree.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import org.apache.logging.log4j.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategoryList;
import nedelosk.modularmachines.api.basic.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.basic.techtree.TechTreeEntryLanguageData;
import nedelosk.modularmachines.api.basic.techtree.TechTreePage;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.nedeloskcore.common.core.Log;
import net.minecraft.client.Minecraft;

public class TechEntryData {
	
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(TechTreeEntry.class, new TechTreeEntry.Deserializer()).registerTypeAdapter(TechTreeEntry.class, new TechTreeEntry.Serializer()).registerTypeAdapter(TechTreeCategoryList.class, new TechTreeCategoryList.Deserializer()).registerTypeAdapter(TechTreeCategoryList.class, new TechTreeCategoryList.Serializer()).registerTypeAdapter(TechTreeEntryLanguageData.class, new TechTreeEntryLanguageData.Serializer()).registerTypeAdapter(TechTreeEntryLanguageData.class, new TechTreeEntryLanguageData.Deserializer()).create();
	
	public static HashMap<String, String> pages = new HashMap<String, String>();
	public static LinkedHashMap<String, TechTreeEntryLanguageData> languageData = new LinkedHashMap<String, TechTreeEntryLanguageData>();
	
	public static void checkJsonData()
	{
		File techTreeFile = new File(ModularMachines.configFolder, "techtree");
		File categorysFile = new File(techTreeFile, "categorys");
		try{
		if(!categorysFile.exists()){
			categorysFile.mkdirs();
			categorysFile.createNewFile();
		}
		checkAndWriteJsonData(categorysFile);
		checkJsonData(categorysFile);
		}catch(Exception e){
		}
	}
	
	private static void checkJsonData(File categorysFile) throws IOException{
		File[] files = categorysFile.listFiles();
		for(File file : files){
			if(file.getName().toLowerCase(Locale.ENGLISH).contains(".json")){
				TechTreeCategoryList category = readCategory(file);
				if(category != null){
					File entryFile = new File(categorysFile, category.key);
					if(!entryFile.exists())
						checkAndWriteJsonData(categorysFile);
					readEntrys(entryFile);
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
					readEntry(entryFile);
			}catch(Exception e){
				Log.log("ModularMachines", Level.ERROR, "Fail to parse : " + file.getName() +  " / " + entryFile.getName());
				continue;
			}
		}
	}
	
	private static void readEntry(File file) throws IOException{
		JsonReader reader = new JsonReader(new FileReader(file));
		TechTreeEntry entry = GSON.fromJson(reader, TechTreeEntry.class);
		TechTreeCategories.addEntry(entry);
		reader.close();
	}
	
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
			writeEntryJsons(category, categoryFolder);
		}
	}
	
	private static void writeCategoryList(TechTreeCategoryList category, File categoryFile) throws IOException{
		BufferedWriter writerCategory = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(categoryFile)));
		writerCategory.write(GSON.toJson(category));
		writerCategory.close();
	}
	
	private static void writeEntryJsons(TechTreeCategoryList category, File categoryFolder) throws IOException{
		for(TechTreeEntry entry : category.entrys.values()){
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
	
	@SideOnly(Side.CLIENT)
	public static TechTreeEntryLanguageData getTranslateData(TechTreeEntry entry){
		return getTranslateData(entry.key);
	}
	
	@SideOnly(Side.CLIENT)
	public static TechTreeEntryLanguageData getTranslateData(String key){
		return languageData.get(key);
	}
	
	@SideOnly(Side.CLIENT)
	public static void checkLanguage(){
		if(!Minecraft.getMinecraft().gameSettings.language.equals(ModularMachinesApi.currentLanguage))
		{
			ModularMachinesApi.currentLanguage = Minecraft.getMinecraft().gameSettings.language;
			updateLanguage();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void updateLanguage(){
		readLanguageData();
	}
	
	@SideOnly(Side.CLIENT)
	private static void readLanguageData(){
		File techTreeFile = new File(ModularMachines.configFolder, "techtree");
		File categorysFile = new File(techTreeFile, "categorys");
		try{
			readCategorysLanguageData(categorysFile);
		}catch(Exception e){
			Log.log("ModularMachines", Level.ERROR, "Fail To Load Language Data from the Tech Tree Entrys");
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void readCategorysLanguageData(File categorysFile) throws IOException{
		for(TechTreeCategoryList category : TechTreeCategories.entryCategoriesTemp.values()){
			readCategoryLanguageData(category, categorysFile);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void readCategoryLanguageData(TechTreeCategoryList category, File categorysFile) throws IOException{
		File categoryFile = new File(categorysFile, category.key);
		for(TechTreeEntry entry : category.entrys.values()){
			readEntryLanguageData(entry, categoryFile);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static TechTreeEntryLanguageData readEntryLanguageData(TechTreeEntry entry, File categoryFile) throws IOException{
		File entryFile = new File(categoryFile, entry.key.toLowerCase(Locale.ENGLISH) + "_language.json");
		JsonReader reader = new JsonReader(new FileReader(entryFile));
		TechTreeEntryLanguageData data = GSON.fromJson(reader, TechTreeEntryLanguageData.class);
		reader.close();
		languageData.put(entry.key, data);
		return data;
	}
	
	@SideOnly(Side.CLIENT)
	public static void writeLanguageData(){
		File techTreeFile = new File(ModularMachines.configFolder, "techtree");
		File categorysFile = new File(techTreeFile, "categorys");
		try{
			writeCategorysLanguageData(categorysFile);
		}catch(Exception e){
			Log.log("ModularMachines", Level.ERROR, "Fail To Write Language Data");
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void writeCategorysLanguageData(File categorysFile)throws IOException {
		if(!categorysFile.exists()){
			categorysFile.mkdirs();
			categorysFile.createNewFile();
		}
		for(TechTreeCategoryList category : TechTreeCategories.entryCategoriesTemp.values()){
			try{
			writeCategoryLanguageData(category, categorysFile);
			}catch(Exception e){
				Log.log("ModularMachines", Level.ERROR, "Fail To Write Language Data : " + category.key);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void writeCategoryLanguageData(TechTreeCategoryList category, File categorysFile) throws IOException{
		File categoryFile = new File(categorysFile, category.key);
		if(!categoryFile.exists()){
			categoryFile.mkdirs();
			categoryFile.createNewFile();
		}
		for(TechTreeEntry entry : category.entrys.values()){
			try{
			writeEntryLanguageData(entry, categoryFile);
			}catch(Exception e){
				Log.log("ModularMachines", Level.ERROR, "Fail To Write Language Data : " + category.key + " / " + entry.key);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void writeEntryLanguageData(TechTreeEntry entry, File categoryFile) throws IOException{
		File entryFile = new File(categoryFile, entry.key.toLowerCase(Locale.ENGLISH) + "_language.json");
		boolean exists = false;
		if(!entryFile.exists()){
			entryFile.createNewFile();
		}
		else
			exists = true;
		ArrayList<String> pages = new ArrayList<String>();
		if(entry.getPages() != null){
			for(TechTreePage page : entry.getPages())
				pages.add(page.text);
		}
		if(exists){
			TechTreeEntryLanguageData data = readEntryLanguageData(entry, categoryFile);
			if(data == null){
				Log.log("ModularMachines", Level.ERROR, "Fail To Write Language Data : " + entry.category + " / " + entry.key);
				return;
			}
			if(data.pages.length == pages.size())
				return;
		}
		BufferedWriter writerEntry = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entryFile)));
		writerEntry.write(GSON.toJson(new TechTreeEntryLanguageData(entry.getName(), entry.getText(), pages.isEmpty() ? null : pages.toArray(new String[pages.size()]))));
		writerEntry.close();
	}
	
}
