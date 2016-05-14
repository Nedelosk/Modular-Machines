package de.nedelosk.forestmods.common.modules.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import de.nedelosk.forestmods.common.crafting.recipes.RecipeManager.RecipeEntry;
import de.nedelosk.forestmods.common.modules.json.ModuleLoadManager.ModuleEntry;

public class ModuleParser implements JsonDeserializer<ModuleEntry> {

	@Override
	public ModuleEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return null;
	}
}
