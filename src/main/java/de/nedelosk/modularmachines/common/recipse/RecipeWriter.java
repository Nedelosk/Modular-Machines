package de.nedelosk.modularmachines.common.recipse;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.common.recipse.RecipeJsonManager.RecipeEntry;

public class RecipeWriter implements JsonSerializer<RecipeEntry> {

	@Override
	public JsonElement serialize(RecipeEntry obj, Type typeOfSrc, JsonSerializationContext context) {
		IRecipe src = obj.recipe;
		JsonObject json = src.writeToJson();
		json.addProperty("isActive", obj.isActive);
		json.addProperty("RecipeName", src.getRecipeName());
		json.addProperty("RecipeCategory", src.getRecipeCategory());
		return json;
	}
}
