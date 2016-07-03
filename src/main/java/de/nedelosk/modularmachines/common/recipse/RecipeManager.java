package de.nedelosk.modularmachines.common.recipse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.common.core.ModularMachines;

public class RecipeManager {

	public static final RecipeWriter writer = new RecipeWriter();
	public static final RecipeParser parser = new RecipeParser();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(RecipeEntry.class, writer)
			.registerTypeAdapter(RecipeEntry.class, parser).create();

	public static void checkRecipes() {
		writeRecipes();
		parseRecipes();
	}

	private static void parseRecipes() {
		File file = new File(ModularMachines.configFolder, "recipes");
		for(String recipeName : RecipeRegistry.getRecipes().keySet()) {
			try {
				File recipeFile = new File(file, recipeName.toLowerCase(Locale.ENGLISH) + "_recipes.json");
				if (!recipeFile.exists()) {
					continue;
				}
				parseRecipes(recipeFile, recipeName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void parseRecipes(File recipeFile, String recipeName) throws IOException {
		BufferedReader bReader = new BufferedReader(new FileReader(recipeFile));
		JsonReader reader = new JsonReader(bReader);
		JsonElement element = Streams.parse(reader);
		Map<String, RecipeGroup> groups = getGoups(element);
		ArrayList<IRecipe> recipes = getActiveRecipes(groups);
		RecipeRegistry.getRecipes().put(recipeName, recipes);
		reader.close();
	}

	private static void writeRecipes() {
		File file = new File(ModularMachines.configFolder, "recipes");
		if (!file.exists()) {
			file.mkdirs();
		}
		for(Entry<String, ArrayList<IRecipe>> recipeEntry : RecipeRegistry.getRecipes().entrySet()) {
			try {
				File recipeFile = new File(file, recipeEntry.getKey().toLowerCase(Locale.ENGLISH) + "_recipes.json");
				if (!recipeFile.exists()) {
					try {
						writeAllRecipes(recipeEntry, recipeFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						writeMissingEntrys(recipeFile, recipeEntry);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void writeMissingEntrys(File recipeFile, Entry<String, ArrayList<IRecipe>> recipeEntry) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(recipeFile));
		JsonReader jsonReader = new JsonReader(reader);
		JsonElement element = Streams.parse(jsonReader);
		Map<String, RecipeGroup> groups = getGoups(element);
		RecipeGroup groupDefault = groups.get("Default");
		if (groupDefault == null) {
			try {
				writeAllRecipes(recipeEntry, recipeFile);
				jsonReader.close();
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<RecipeEntry> newEntrys = new ArrayList<>();
		dafault: for(IRecipe r : recipeEntry.getValue()) {
			Iterator<RecipeEntry> entrys = groupDefault.recipes.iterator();
			while(entrys.hasNext()) {
				RecipeEntry entry = entrys.next();
				if (r.getRecipeName().equals(entry.name)) {
					newEntrys.add(new RecipeEntry(r.getRecipeName(), entry.isActive, r));
					entrys.remove();
					continue dafault;
				}
				continue;
			}
			newEntrys.add(new RecipeEntry(r.getRecipeName(), true, r));
		}
		groupDefault.recipes.addAll(newEntrys);
		jsonReader.close();
		JsonObject object = new JsonObject();
		for(Entry<String, RecipeGroup> group : groups.entrySet()) {
			object.add(group.getKey(), GSON.toJsonTree(group.getValue()));
		}
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(recipeFile)));
		writer.write(GSON.toJson(object));
		writer.close();
	}

	private static Map<String, RecipeGroup> getGoups(JsonElement element) {
		Map<String, RecipeGroup> groups = Maps.newHashMap();
		JsonObject object = element.getAsJsonObject();
		for(Entry<String, JsonElement> entry : object.entrySet()){
			JsonElement ele = entry.getValue();
			if (ele != null && ele != JsonNull.INSTANCE) {
				try {
					RecipeGroup group = GSON.fromJson(ele, RecipeGroup.class);
					groups.put(group.name, group);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return groups;
	}

	private static ArrayList<IRecipe> getActiveRecipes(Map<String, RecipeGroup> groups) {
		ArrayList<IRecipe> recipes = new ArrayList();
		for(RecipeGroup group : groups.values()) {
			for(RecipeEntry recipe : group.recipes) {
				if (recipe.isActive) {
					recipes.add(recipe.recipe);
				}
			}
		}
		return recipes;
	}

	private static void writeAllRecipes(Entry<String, ArrayList<IRecipe>> recipe, File recipeFile) throws IOException {
		Map<String, RecipeGroup> groups;
		if (!recipeFile.exists()) {
			recipeFile.createNewFile();
			groups = new HashMap();
		}else{
			BufferedReader reader = new BufferedReader(new FileReader(recipeFile));
			JsonReader jsonReader = new JsonReader(reader);
			JsonElement element = Streams.parse(jsonReader);
			groups = getGoups(element);
		}
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(recipeFile)));
			groups.put("Default", new RecipeGroup(recipe.getValue(), "Default"));
			writer.write(GSON.toJson(groups));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class RecipeGroup {

		public RecipeGroup(boolean isActive, ArrayList<RecipeEntry> recipes, String name) {
			this.isActive = isActive;
			this.recipes = recipes;
			this.name = name;
		}

		public RecipeGroup(ArrayList<IRecipe> recipes, String name) {
			this.isActive = true;
			this.recipes = new ArrayList();
			for(IRecipe recipe : recipes) {
				this.recipes.add(new RecipeEntry(recipe.getRecipeName(), true, recipe));
			}
			this.name = name;
		}

		public String name;
		public boolean isActive;
		public ArrayList<RecipeEntry> recipes;
	}

	public static class RecipeEntry {

		/* For Default Recipes */
		public String name;

		public RecipeEntry(String name, boolean isActive, IRecipe recipe) {
			this.name = name;
			this.isActive = isActive;
			this.recipe = recipe;
		}

		public boolean isActive;
		public IRecipe recipe;
	}
}
