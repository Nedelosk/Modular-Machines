package de.nedelosk.modularmachines.common.recipse;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipeHandler;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.common.recipse.RecipeJsonManager.RecipeEntry;

public class RecipeParser implements JsonDeserializer<RecipeEntry> {

	@Override
	public RecipeEntry deserialize(JsonElement elementJson, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject json = elementJson.getAsJsonObject();
		String recipeName;
		String recipeCategory;
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
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler(recipeCategory);
		if (handler == null) {
			return null;
		}
		IRecipe recipe = handler.buildDefault();
		recipe.readFromJson(json);
		return new RecipeEntry(recipeName, isActive, recipe);
	}
}
