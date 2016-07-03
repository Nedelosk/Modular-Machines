package de.nedelosk.modularmachines.common.modules.tools.recipe;

import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.recipes.IMachineMode;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipeHandler;
import de.nedelosk.modularmachines.api.recipes.IRecipeJsonSerializer;
import de.nedelosk.modularmachines.api.recipes.IRecipeNBTSerializer;
import net.minecraft.nbt.NBTTagCompound;

public class RecipeAdvancedHandler implements IRecipeHandler, IRecipeJsonSerializer, IRecipeNBTSerializer {

	public Class<? extends IMachineMode> modeClass;
	public String recipeCategory;

	public RecipeAdvancedHandler(Class<? extends IMachineMode> modeClass, String recipeCategory) {
		this.modeClass = modeClass;
		this.recipeCategory = recipeCategory;
	}

	@Override
	public void serializeNBT(NBTTagCompound nbt, Object[] craftingModifiers) {
		IMachineMode mode = (IMachineMode) craftingModifiers[0];
		NBTTagCompound nbtCrafting = new NBTTagCompound();
		nbtCrafting.setInteger("Mode", mode.ordinal());
		nbt.setTag("Crafting", nbtCrafting);
	}

	@Override
	public Object[] deserializeNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtCrafting = nbt.getCompoundTag("Crafting");
		IMachineMode mode = modeClass.getEnumConstants()[nbtCrafting.getInteger("Mode")];
		return new Object[] { mode };
	}

	@Override
	public Object[] deserializeJson(JsonObject object) {
		if (object.has("Mode") && object.get("Mode").isJsonPrimitive()) {
			return new Object[] { modeClass.getEnumConstants()[object.get("Mode").getAsInt()] };
		}
		return null;
	}

	@Override
	public JsonObject serializeJson(Object[] objects) {
		JsonObject object = new JsonObject();
		object.addProperty("Mode", ((IMachineMode) objects[0]).ordinal());
		return object;
	}

	@Override
	public boolean matches(IRecipe recipe, Object[] craftingModifiers) {
		if (recipe.getModifiers() == null || recipe.getModifiers().length == 0 || craftingModifiers == null || craftingModifiers.length == 0) {
			return false;
		}
		if (craftingModifiers[0] instanceof IMachineMode) {
			IMachineMode mode = (IMachineMode) craftingModifiers[0];
			if (mode == recipe.getModifiers()[0]) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IRecipeJsonSerializer getJsonSerialize() {
		return this;
	}

	@Override
	public IRecipeNBTSerializer getNBTSerialize() {
		return this;
	}

	@Override
	public String getRecipeCategory() {
		return recipeCategory;
	}

}
