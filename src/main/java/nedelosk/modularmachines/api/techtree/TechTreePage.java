package nedelosk.modularmachines.api.techtree;

import java.util.Map;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.client.techtree.pages.PageData;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class TechTreePage {
	public static enum PageType
    {
        TEXT,
        IMAGE,
        NORMAL_CRAFTING,
        PRODUCER_CRAFTINT,
        MODULAR_CRAFTING,
        SMELTING,
        TEXT_CONCEALED
    }
	
	public PageType type = PageType.TEXT;
	
	public String text=null;
	public String entry=null;
	public ResourceLocation image=null;
	public Object recipe=null;
	public ItemStack recipeOutput=null;
	
	/**
	 * @param text this can (but does not have to) be a reference to a localization variable, not the actual text.
	 */
	public TechTreePage(String text) {
		this.type = PageType.TEXT;
		this.text = text;
	}
	
	/**
	 * @param recipe a vanilla crafting recipe.
	 */
	public TechTreePage(IRecipe recipe) {
		this.type = PageType.NORMAL_CRAFTING;
		this.recipe = recipe;
		this.recipeOutput = recipe.getRecipeOutput();
	}
	
	public TechTreePage(String entry, String text) {
		this.type = PageType.TEXT_CONCEALED;
		this.entry = entry;
		this.text = text;
	}
	
	/**
	 * @param recipe a collection of vanilla crafting recipes.
	 */
	public TechTreePage(IRecipe[] recipe) {
		this.type = PageType.NORMAL_CRAFTING;
		this.recipe = recipe;
	}
	
	/**
	 * @param recipe a collection of producer recipes.
	 */
	public TechTreePage(nedelosk.modularmachines.api.modular.module.recipes.IRecipe[] recipe) {
		this.type = PageType.PRODUCER_CRAFTINT;
		this.recipe = recipe;
	}
	
	/**
	 * @param recipe a furnace smelting crafting recipe.
	 */
	public TechTreePage(ItemStack input) {
		this.type = PageType.SMELTING;
		this.recipe = input;
		this.recipeOutput = FurnaceRecipes.smelting().getSmeltingResult(input);
	}
	
	/**
	 * @param image
	 * @param caption this can (but does not have to) be a reference to a localization variable, not the actual text.
	 */
	public TechTreePage(ResourceLocation image, String caption) {
		this.type = PageType.IMAGE;
		this.image = image;
		this.text = caption;
	}
	
	/**
	 * returns a localized text of the text field (if one exists). Returns the text field itself otherwise.
	 * @return
	 */
	public String getTranslatedText() {
		String ret="";
		if (text != null) {
			if(!Minecraft.getMinecraft().gameSettings.language.equals(ModularMachinesApi.currentLanguage))
			{
				ModularMachinesApi.currentLanguage = Minecraft.getMinecraft().gameSettings.language;
				for(Map.Entry<String, TechTreeCategoryList> entry : TechTreeCategories.entryCategories.entrySet())
					PageData.readDocument(entry.getKey(), Minecraft.getMinecraft().gameSettings.language);
			}
			ret = PageData.pages.get(text);
			if (ret.isEmpty()) ret = text;
		}
		return ret;
	}
	
	
}
