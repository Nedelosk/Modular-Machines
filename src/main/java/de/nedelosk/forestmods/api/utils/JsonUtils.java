package de.nedelosk.forestmods.api.utils;

import org.apache.logging.log4j.Level;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.utils.Log;
import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class JsonUtils {

	public static RecipeItem[] parseRecipeItem(JsonArray jsonRecipe) {
		RecipeItem[] item;
		if (jsonRecipe.size() == 0) {
			item = null;
		}
		item = new RecipeItem[jsonRecipe.size()];
		for ( int i = 0; i < jsonRecipe.size(); i++ ) {
			try {
				JsonElement recipeElement = jsonRecipe.get(i);
				if (!recipeElement.isJsonObject()) {
					item = null;
				}
				JsonObject recipeObject = recipeElement.getAsJsonObject();
				if (!recipeObject.has("Type") || !recipeObject.get("Type").isJsonPrimitive() || !recipeObject.get("Type").getAsJsonPrimitive().isString()) {
					item = null;
				}
				if (!recipeObject.has("Item") || !recipeObject.get("Item").isJsonPrimitive() || !recipeObject.get("Item").getAsJsonPrimitive().isString()) {
					item = null;
				}
				if (!recipeObject.has("Amount") || !recipeObject.get("Amount").isJsonPrimitive()
						|| !recipeObject.get("Amount").getAsJsonPrimitive().isNumber()) {
					item = null;
				}
				String type = recipeObject.get("Type").getAsJsonPrimitive().getAsString();
				String itemName = recipeObject.get("Item").getAsJsonPrimitive().getAsString();
				int amount = recipeObject.get("Amount").getAsJsonPrimitive().getAsInt();
				if (type.equals("Ore")) {
					item[i] = new RecipeItem(i, new OreStack(itemName, amount));
				} else if (type.equals("Item")) {
					item[i] = new RecipeItem(i, parseItem(itemName, amount));
				} else if (type.equals("Fluid")) {
					item[i] = new RecipeItem(i, parseFluid(itemName, amount));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return item;
	}

	public static JsonArray writeRecipeItem(RecipeItem[] items) {
		JsonArray array = new JsonArray();
		for ( RecipeItem item : items ) {
			JsonObject json = new JsonObject();
			if (item.isItem()) {
				json.addProperty("Type", "Item");
				json.addProperty("Item", writeItem(item.item));
				json.addProperty("Amount", item.item.stackSize);
			} else if (item.isFluid()) {
				json.addProperty("Type", "Fluid");
				json.addProperty("Item", writeFluid(item.fluid));
				json.addProperty("Amount", item.fluid.amount);
			} else if (item.isOre()) {
				json.addProperty("Type", "Ore");
				json.addProperty("Item", item.ore.getOreDict());
				json.addProperty("Amount", item.ore.stackSize);
			}
			array.add(json);
		}
		return array;
	}

	public static ItemStack parseItem(String itemName, int amount) {
		String[] names = itemName.split(":", 4);
		Item item = GameRegistry.findItem(names[0], names[1]);
		int meta = (names.length >= 3 ? Integer.parseInt(names[2]) : 0);
		ItemStack stack = new ItemStack(item, amount, meta);
		if (names.length == 4) {
			try {
				stack.stackTagCompound = (NBTTagCompound) JsonToNBT.func_150315_a(names[3]);
			} catch (Exception e) {
				Log.log("NedeloskCore", Level.ERROR, "Fail to parse : " + itemName);
			}
		}
		return stack;
	}

	public static FluidStack parseFluid(String itemName, int amount) {
		String[] names = itemName.split(":", 2);
		Fluid fluid = FluidRegistry.getFluid(names[0]);
		FluidStack stack = new FluidStack(fluid, amount);
		if (names.length == 2) {
			try {
				stack.tag = (NBTTagCompound) JsonToNBT.func_150315_a(names[1]);
			} catch (Exception e) {
				Log.log("NedeloskCore", Level.ERROR, "Fail to parse : " + itemName);
			}
		}
		return stack;
	}

	public static String writeItem(ItemStack item) {
		String itemName = GameData.getItemRegistry().getNameForObject(item.getItem()) + ":" + item.getItemDamage();
		if (item.hasTagCompound()) {
			itemName += ":" + item.getTagCompound().toString();
		}
		return itemName;
	}

	public static String writeFluid(FluidStack fluid) {
		String fluidName = fluid.getFluid().getName();
		if (fluid.tag != null) {
			fluidName += ":" + fluid.tag.toString();
		}
		return fluidName;
	}

	public static ResourceLocation parseLocation(JsonElement json, String name) {
		String[] location = json.getAsJsonObject().get(name).getAsString().split(":");
		return new ResourceLocation(location[0], "textures/" + location[1]);
	}

	public static ItemStack parseItem(JsonElement json, String itemName) {
		String[] names = json.getAsJsonObject().get(itemName).getAsString().split(":", 4);
		Item item = GameRegistry.findItem(names[0], names[1]);
		int meta = (names.length >= 3 ? Integer.parseInt(names[2]) : 0);
		ItemStack stack = new ItemStack(item, 1, meta);
		if (names.length == 4) {
			try {
				stack.setTagCompound((NBTTagCompound) JsonToNBT.func_150315_a(names[3]));
			} catch (Exception e) {
				Log.log("NedeloskCore", Level.ERROR, "Fail to parse : " + itemName);
			}
		}
		return stack;
	}

	public static JsonElement writeLocation(ResourceLocation location) {
		return new JsonPrimitive(location.toString().replace("textures/", ""));
	}

	public static JsonElement writeItemElement(ItemStack item) {
		String itemName = GameData.getItemRegistry().getNameForObject(item.getItem()) + ":" + item.getItemDamage();
		if (item.hasTagCompound()) {
			itemName += ":" + item.getTagCompound().toString();
		}
		return new JsonPrimitive(itemName);
	}
}
