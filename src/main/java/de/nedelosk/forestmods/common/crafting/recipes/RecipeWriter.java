package de.nedelosk.forestmods.common.crafting.recipes;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.IRecipeHandler;
import de.nedelosk.forestmods.api.recipes.IRecipeJsonSerializer;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.JsonUtils;
import de.nedelosk.forestmods.common.crafting.recipes.RecipeManager.RecipeEntry;

public class RecipeWriter implements JsonSerializer<RecipeEntry> {

	@Override
	public JsonElement serialize(RecipeEntry obj, Type typeOfSrc, JsonSerializationContext context) {
		IRecipe src = obj.recipe;
		JsonObject json = new JsonObject();
		json.addProperty("isActive", obj.isActive);
		json.addProperty("RecipeName", src.getRecipeName());
		json.addProperty("RecipeCategory", src.getRecipeCategory());
		json.addProperty("MaterialModifier", src.getRequiredMaterial());
		json.addProperty("SpeedModifier", src.getRequiredSpeedModifier());
		json.add("Inputs", JsonUtils.writeRecipeItem(src.getInputs()));
		json.add("Outputs", JsonUtils.writeRecipeItem(src.getOutputs()));
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler(src.getRecipeCategory());
		if (handler != null) {
			IRecipeJsonSerializer serializer = handler.getJsonSerialize();
			if (serializer != null) {
				JsonObject craftingModifiers = serializer.serializeJson(src.getModifiers());
				if (craftingModifiers != null) {
					json.add("CraftingModifiers", craftingModifiers);
				}
			}
		}
		return json;
	}
}
