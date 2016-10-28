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
import de.nedelosk.modularmachines.api.recipes.IRecipeHandler;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.common.core.ModularMachines;

public class RecipeJsonManager {

	public static final File recipeFile = new File(ModularMachines.configFolder, "recipes");
	public static final RecipeWriter writer = new RecipeWriter();
	public static final RecipeParser parser = new RecipeParser();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(RecipeEntry.class, writer).registerTypeAdapter(RecipeEntry.class, parser).create();

	public static void checkRecipes() {
		writeRecipesFiles();
		parseRecipesFiles();
	}

	private static void parseRecipesFiles() {
		for(IRecipeHandler handler : RecipeRegistry.getHandlers().values()) {
			String recipeCategory = handler.getRecipeCategory();
			try {
				File categoryFile = new File(recipeFile, recipeCategory.toLowerCase(Locale.ENGLISH) + "_recipes.json");
				if (!categoryFile.exists()) {
					continue;
				}
				parseRecipeFile(categoryFile, recipeCategory);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static List<IRecipe> parseRecipeFile(File recipeFile, String recipeName) throws IOException {
		BufferedReader bReader = new BufferedReader(new FileReader(recipeFile));
		JsonReader reader = new JsonReader(bReader);
		JsonElement element = Streams.parse(reader);
		reader.close();
		return getActiveRecipes(getGoups(element));
	}

	private static void writeRecipesFiles() {
		if (!recipeFile.exists()) {
			recipeFile.mkdirs();
		}
		for(IRecipeHandler handler : RecipeRegistry.getHandlers().values()) {
			try {
				File categoryFile = new File(recipeFile, handler.getRecipeCategory().toLowerCase(Locale.ENGLISH) + "_recipes.json");
				List<IRecipe> recipes = handler.getRecipes();
				if (!categoryFile.exists()) {
					writeRecipes(categoryFile, recipes);
				} else {
					updateEntrys(categoryFile, recipes);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void updateEntrys(File recipeFile, List<IRecipe> recipes) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(recipeFile));
		JsonReader jsonReader = new JsonReader(reader);
		JsonElement element = Streams.parse(jsonReader);
		Map<String, RecipeGroup> groups = getGoups(element);
		RecipeGroup groupDefault = groups.get("Default");
		if (groupDefault == null) {
			try {
				writeRecipes(recipeFile, recipes);
				jsonReader.close();
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<RecipeEntry> entrys = new ArrayList<>();
		for(IRecipe recipe : recipes) {
			Iterator<RecipeEntry> oldEntrys = groupDefault.recipes.iterator();
			boolean isActive = true;
			while (oldEntrys.hasNext()) {
				RecipeEntry entry = oldEntrys.next();
				if (!entry.isActive && recipe.getRecipeName().equals(entry.name)) {
					isActive = false;
					oldEntrys.remove();
					break;
				}
				continue;
			}
			entrys.add(new RecipeEntry(recipe.getRecipeName(), isActive, recipe));
		}
		groupDefault.recipes.clear();
		groupDefault.recipes.addAll(entrys);
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
		for(Entry<String, JsonElement> groupEntry : object.entrySet()) {
			JsonElement groupElement = groupEntry.getValue();
			if (groupElement != null && groupElement != JsonNull.INSTANCE) {
				try {
					RecipeGroup group = GSON.fromJson(groupElement, RecipeGroup.class);
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

	private static void writeRecipes(File categoryFile, List<IRecipe> recipes) throws IOException {
		Map<String, RecipeGroup> groups;
		if (!categoryFile.exists()) {
			categoryFile.createNewFile();
			groups = new HashMap();
		} else {
			JsonReader jsonReader = new JsonReader(new BufferedReader(new FileReader(categoryFile)));
			JsonElement element = Streams.parse(jsonReader);
			groups = getGoups(element);
			jsonReader.close();
		}
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(categoryFile)));
			groups.put("Default", new RecipeGroup(recipes, "Default"));
			writer.write(GSON.toJson(groups));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class RecipeGroup {

		public RecipeGroup(boolean isActive, List<RecipeEntry> recipes, String name) {
			this.isActive = isActive;
			this.recipes = recipes;
			this.name = name;
		}

		public RecipeGroup(List<IRecipe> recipes, String name) {
			this.isActive = true;
			this.recipes = new ArrayList();
			for(IRecipe recipe : recipes) {
				this.recipes.add(new RecipeEntry(recipe.getRecipeName(), true, recipe));
			}
			this.name = name;
		}

		public String name;
		public boolean isActive;
		public List<RecipeEntry> recipes;
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
