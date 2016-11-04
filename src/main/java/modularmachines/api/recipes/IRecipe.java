package modularmachines.api.recipes;

import com.google.gson.JsonObject;

import modularmachines.api.property.IPropertyProvider;

public interface IRecipe extends IPropertyProvider {

	RecipeItem[] getInputs();

	RecipeItem[] getOutputs();

	String getRecipeName();

	String getRecipeCategory();

	int getSpeed();

	JsonObject writeToJson();

	void readFromJson(JsonObject jsonObject);
}
