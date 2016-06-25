package de.nedelosk.modularmachines.common.modules.tools.recipe;

import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipeHandler;
import de.nedelosk.modularmachines.api.recipes.IRecipeJsonSerializer;
import de.nedelosk.modularmachines.api.recipes.IRecipeNBTSerializer;
import net.minecraft.nbt.NBTTagCompound;

public class RecipeHandlerBoiler implements IRecipeHandler, IRecipeJsonSerializer, IRecipeNBTSerializer {

	@Override
	public IRecipeJsonSerializer getJsonSerialize() {
		return this;
	}

	@Override
	public IRecipeNBTSerializer getNBTSerialize() {
		return this;
	}

	@Override
	public boolean matches(IRecipe recipe, Object[] craftingModifiers) {
		int modularHeat = (int) craftingModifiers[0];
		int heat = (int) recipe.getModifiers()[0];
		return modularHeat >= heat;
	}

	@Override
	public String getRecipeCategory() {
		return "Boiler";
	}

	@Override
	public void serializeNBT(NBTTagCompound nbt, Object[] craftingModifiers) {
		nbt.setInteger("Heat", (int) craftingModifiers[0]);
	}

	@Override
	public Object[] deserializeNBT(NBTTagCompound nbt) {
		return new Object[]{nbt.getInteger("Heat")};
	}

	@Override
	public JsonObject serializeJson(Object[] objects) {
		JsonObject object = new JsonObject();
		object.addProperty("Heat", (int)objects[0]);
		return object;
	}

	@Override
	public Object[] deserializeJson(JsonObject object) {
		return new Object[]{object.get("Heat").getAsInt()};
	}
}
