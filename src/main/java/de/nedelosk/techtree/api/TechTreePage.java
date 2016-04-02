package de.nedelosk.techtree.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class TechTreePage {

	public static enum PageType {
		TEXT, TEXT_CONCEALED, IMAGE, NORMAL_CRAFTING,
		// PRODUCER_CRAFTINT,
		// MODULAR_CRAFTING,
		SMELTING;

		public PageType nextType() {
			if (ordinal() == values().length - 1) {
				return TEXT;
			}
			return values()[ordinal() + 1];
		}
	}

	public PageType type = PageType.TEXT;
	public String text = null;
	public String entry = null;
	public ResourceLocation image = null;
	public Object recipe = null;
	public ItemStack[] recipeInputs = null;
	public ItemStack recipeOutput = null;
	public final int ID;

	public TechTreePage(int ID) {
		this.ID = ID;
	}

	/**
	 * @param text
	 *            this can (but does not have to) be a reference to a
	 *            localization variable, not the actual text.
	 */
	public TechTreePage(String text, int ID) {
		this.type = PageType.TEXT;
		this.text = text;
		this.ID = ID;
	}

	/**
	 * @param recipe
	 *            a vanilla crafting recipe.
	 */
	public TechTreePage(IRecipe recipe, int ID) {
		this.type = PageType.NORMAL_CRAFTING;
		this.recipe = recipe;
		this.recipeOutput = recipe.getRecipeOutput();
		this.ID = ID;
	}

	public TechTreePage(String entry, String text, int ID) {
		this.type = PageType.TEXT_CONCEALED;
		this.entry = entry;
		this.text = text;
		this.ID = ID;
	}

	/**
	 * @param recipe
	 *            a collection of vanilla crafting recipes.
	 */
	public TechTreePage(IRecipe[] recipe, int ID) {
		this.type = PageType.NORMAL_CRAFTING;
		this.recipe = recipe;
		this.ID = ID;
	}

	/**
	 * @param recipe
	 *            a collection of producer recipes.
	 */
	/*
	 * public
	 * TechTreePage(nedelosk.modularmachines.api.basic.modular.module.recipes.
	 * IRecipe[] recipe, int ID) { this.type = PageType.PRODUCER_CRAFTINT;
	 * this.recipe = recipe; this.ID = ID; }
	 */
	/**
	 * @param recipe
	 *            a furnace smelting crafting recipe.
	 */
	public TechTreePage(ItemStack input, int ID) {
		this.type = PageType.SMELTING;
		this.recipe = input;
		this.recipeOutput = FurnaceRecipes.smelting().getSmeltingResult(input);
		this.ID = ID;
	}

	/**
	 * @param image
	 * @param caption
	 *            this can (but does not have to) be a reference to a
	 *            localization variable, not the actual text.
	 */
	public TechTreePage(ResourceLocation image, String caption, int ID) {
		this.type = PageType.IMAGE;
		this.image = image;
		this.text = caption;
		this.ID = ID;
	}

	/**
	 * returns a localized text of the text field (if one exists). Returns the
	 * text field itself otherwise.
	 * 
	 * @return
	 */
	public String getTranslatedText(TechTreeEntry entry) {
		String ret = "";
		if (text != null) {
			TechTreeApi.languageManager.checkLanguage();
			ret = TechTreeApi.languageManager.getTranslateData(entry).getPages()[ID];
			if (ret.isEmpty()) {
				ret = text;
			}
		}
		return ret;
	}
}
