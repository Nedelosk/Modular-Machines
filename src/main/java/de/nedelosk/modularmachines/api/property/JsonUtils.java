package de.nedelosk.modularmachines.api.property;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.json.ModuleLoaderRegistry;
import de.nedelosk.modularmachines.api.recipes.OreStack;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class JsonUtils {

	public static RecipeItem[] parseRecipeItem(JsonArray jsonRecipe) {
		RecipeItem[] item;
		if (jsonRecipe.size() == 0) {
			item = null;
		}
		item = new RecipeItem[jsonRecipe.size()];
		for(int i = 0; i < jsonRecipe.size(); i++) {
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
				if (!recipeObject.has("Chance") || !recipeObject.get("Chance").isJsonPrimitive()
						|| !recipeObject.get("Chance").getAsJsonPrimitive().isNumber()) {
					item = null;
				}
				String type = recipeObject.get("Type").getAsJsonPrimitive().getAsString();
				String itemName = recipeObject.get("Item").getAsJsonPrimitive().getAsString();
				int amount = recipeObject.get("Amount").getAsJsonPrimitive().getAsInt();
				int chance =  recipeObject.get("Chance").getAsJsonPrimitive().getAsInt();
				if (type.equals("Ore")) {
					item[i] = new RecipeItem(i, new OreStack(itemName, amount), chance);
				} else if (type.equals("Item")) {
					item[i] = new RecipeItem(i, parseItem(itemName, amount), chance);
				} else if (type.equals("Fluid")) {
					item[i] = new RecipeItem(i, parseFluid(itemName, amount), chance);
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
		for(RecipeItem item : items) {
			JsonObject json = new JsonObject();
			json.addProperty("Chance", item.chance);
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
				stack.setTagCompound(JsonToNBT.getTagFromJson(names[3]));
			} catch (Exception e) {
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
				stack.tag = JsonToNBT.getTagFromJson(names[1]);
			} catch (Exception e) {
			}
		}
		return stack;
	}

	public static String writeItem(ItemStack item) {
		String itemName = ForgeRegistries.ITEMS.getKey(item.getItem()) + ":" + item.getItemDamage();
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

	public static Item parseItem(JsonElement json, String itemName) {
		try{
			String[] names = json.getAsJsonObject().get(itemName).getAsString().split(":", 2);
			String modID = names.length == 1 ? "minecraft" : names[0];
			String name = names.length == 1 ? names[0] : names[1];
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modID, name));
			return item;
		}catch(Exception e){	
		}
		return null;
	}

	public static ItemStack parseItemStack(JsonElement json, String itemName) {
		try{
			String[] names = json.getAsJsonObject().get(itemName).getAsString().split(":", 4);
			Item item = parseItem(json, itemName);
			int meta = (names.length >= 3 ? Integer.parseInt(names[2]) : 0);
			ItemStack stack = new ItemStack(item, 1, meta);
			if (names.length == 4) {
				try {
					stack.setTagCompound(JsonToNBT.getTagFromJson(names[3]));
				} catch (Exception e) {
				}
			}
			return stack;
		}catch(Exception e){	
		}
		return null;
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

	public static EnumModuleSizes getSize(JsonObject json){
		int size = getInt(json.get("size"));
		if(size >= EnumModuleSizes.VALUES.length){
			size = EnumModuleSizes.VALUES.length - 1;
		}else if(size < 1){
			size = 1;
		}
		return EnumModuleSizes.values()[size];
	}

	public static IModuleContainer getContainer(JsonObject json){
		IModule module = null;
		IModuleProperties properties = null;
		String moduleName = JsonUtils.getString(json.get("module"));
		if(moduleName != null){
			module = ModuleManager.MODULES.getValue(new ResourceLocation(moduleName));
		}
		if(json.has("properties") && json.get("properties").isJsonObject()){
			properties = ModuleLoaderRegistry.loadPropertiesFromJson(json.get("properties").getAsJsonObject());
		}
		if(module != null){
			return new ModuleContainer(module, properties);
		}
		return null;
	}

	public static JsonArray getArray(JsonElement json){
		if(json != null && json.isJsonArray()){
			return json.getAsJsonArray();
		}
		return null;
	}

	public static String getString(JsonElement json){
		if(json != null && json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()){
			return json.getAsJsonPrimitive().getAsString();
		}
		return null;
	}

	public static int getInt(JsonElement json){
		if(json != null && json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()){
			return json.getAsJsonPrimitive().getAsInt();
		}
		return 0;
	}

	public static double getDouble(JsonElement json){
		if(json != null && json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()){
			return json.getAsJsonPrimitive().getAsDouble();
		}
		return 0.0D;
	}

	public static float getFloat(JsonElement json){
		if(json != null && json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()){
			return json.getAsJsonPrimitive().getAsFloat();
		}
		return 0.0F;
	}
}
