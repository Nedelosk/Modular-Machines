package de.nedelosk.forestmods.api.recipes;

import com.google.gson.JsonObject;

public interface IRecipeJsonSerializer {

	JsonObject serializeJson(Object[] objects);

	Object[] deserializeJson(JsonObject object);
}
