package de.nedelosk.techtree.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.nedelosk.techtree.api.crafting.IModularCraftingRecipe;
import de.nedelosk.techtree.api.language.ILanguageManager;
import net.minecraft.item.ItemStack;

public class TechTreeApi {

	public static String currentLanguage;
	private static ArrayList<IModularCraftingRecipe> recipes = Lists.newArrayList();
	public final static HashMap<List, TechPointStack[]> techpoinedItems = Maps.newHashMap();
	public static ILanguageManager languageManager;

	public static void addTechPointsToItem(ItemStack stack, TechPointStack... stacks) {
		techpoinedItems.put(Arrays.asList(new Object[] { stack.getItem(), Integer.valueOf(stack.getItemDamage()) }), stacks);
	}

	public static TechPointStack[] getTechPointsFromItem(ItemStack stack) {
		return techpoinedItems.get(Arrays.asList(new Object[] { stack.getItem(), Integer.valueOf(stack.getItemDamage()) }));
	}

	public static ArrayList<IModularCraftingRecipe> getModularRecipes() {
		return recipes;
	}

	public static void registerRecipe(IModularCraftingRecipe recipe) {
		recipes.add(recipe);
	}
}
