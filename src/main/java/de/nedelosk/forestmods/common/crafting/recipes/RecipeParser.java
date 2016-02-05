package de.nedelosk.forestmods.common.crafting.recipes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.IRecipeHandler;
import de.nedelosk.forestmods.api.recipes.Recipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.JsonUtils;
import de.nedelosk.forestmods.common.crafting.recipes.RecipeManager.RecipeEntry;

public class RecipeParser implements JsonDeserializer<RecipeEntry> {

	@Override
	public RecipeEntry deserialize(JsonElement elementJson, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject json = elementJson.getAsJsonObject();
		String recipeName;
		String recipeCategory;
		int speedModifier;
		int materialModifier;
		RecipeItem[] inputs;
		RecipeItem[] outputs;
		Object[] craftingModifiers;
		boolean isActive;
		if (json.has("isActive")) {
			if (!json.isJsonPrimitive()) {
				isActive = false;
			}
			JsonPrimitive jsonRecipe = json.get("isActive").getAsJsonPrimitive();
			if (!jsonRecipe.isBoolean()) {
				isActive = false;
			}
			isActive = jsonRecipe.getAsBoolean();
		} else {
			isActive = false;
		}
		if (json.has("RecipeCategory")) {
			if (!json.isJsonPrimitive()) {
				recipeCategory = "";
			}
			JsonPrimitive jsonRecipe = json.get("RecipeCategory").getAsJsonPrimitive();
			if (!jsonRecipe.isString()) {
				recipeCategory = "";
			}
			recipeCategory = jsonRecipe.getAsString();
		} else {
			recipeCategory = "";
		}
		if (json.has("RecipeName")) {
			if (!json.isJsonPrimitive()) {
				recipeName = null;
			}
			JsonPrimitive jsonRecipe = json.get("RecipeName").getAsJsonPrimitive();
			if (!jsonRecipe.isString()) {
				recipeName = null;
			}
			recipeName = jsonRecipe.getAsString();
		} else {
			recipeName = null;
		}
		if (json.has("MaterialModifier")) {
			if (!json.isJsonPrimitive()) {
				materialModifier = 0;
			}
			JsonPrimitive jsonRecipe = json.get("MaterialModifier").getAsJsonPrimitive();
			if (!jsonRecipe.isNumber()) {
				materialModifier = 0;
			}
			materialModifier = jsonRecipe.getAsInt();
		} else {
			materialModifier = 0;
		}
		if (json.has("SpeedModifier")) {
			if (!json.isJsonPrimitive()) {
				speedModifier = 0;
			}
			JsonPrimitive jsonRecipe = json.get("SpeedModifier").getAsJsonPrimitive();
			if (!jsonRecipe.isNumber()) {
				speedModifier = 0;
			}
			speedModifier = jsonRecipe.getAsInt();
		} else {
			speedModifier = 0;
		}
		if (json.has("Inputs")) {
			if (!json.isJsonArray()) {
				inputs = null;
			}
			JsonArray jsonRecipe = json.get("Inputs").getAsJsonArray();
			inputs = JsonUtils.parseRecipeItem(jsonRecipe);
		} else {
			inputs = null;
		}
		if (json.has("Outputs")) {
			if (!json.isJsonArray()) {
				outputs = null;
			}
			JsonArray jsonRecipe = json.get("Outputs").getAsJsonArray();
			outputs = JsonUtils.parseRecipeItem(jsonRecipe);
		} else {
			outputs = null;
		}
		if (json.has("CraftingModifiers")) {
			craftingModifiers = null;
		} else {
			craftingModifiers = null;
		}
		if (recipeCategory.equals("") || speedModifier == 0 || materialModifier == 0 || inputs == null || outputs == null) {
			return null;
		}
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler(recipeCategory);
		if (handler != null && json.has("CraftingModifiers") && json.get("CraftingModifiers").isJsonObject()) {
			craftingModifiers = handler.parseCraftingModifiers(json.get("CraftingModifiers").getAsJsonObject());
		}
		IRecipe recipe;
		if (handler != null && handler.getRecipeClass() != null && !handler.getRecipeClass().isInterface()) {
			try {
				Constructor<? extends IRecipe> c = handler.getRecipeClass().getConstructor(String.class, RecipeItem[].class, RecipeItem[].class, int.class,
						int.class, String.class, Object[].class);
				recipe = c.newInstance(recipeName, inputs, outputs, speedModifier, materialModifier, recipeCategory, craftingModifiers);
			} catch (Exception e) {
				e.printStackTrace();
				recipe = new Recipe(recipeName, inputs, outputs, speedModifier, materialModifier, recipeCategory, craftingModifiers);
			}
		} else {
			recipe = new Recipe(recipeName, inputs, outputs, speedModifier, materialModifier, recipeCategory, craftingModifiers);
		}
		return new RecipeEntry(recipeName, isActive, recipe);
	}
}
