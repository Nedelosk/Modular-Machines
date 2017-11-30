package modularmachines.common.compat.guideapi;

import javax.annotation.Nonnull;
import java.awt.Color;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.oredict.ShapelessOreRecipe;

import net.minecraftforge.fml.common.eventhandler.EventPriority;

import modularmachines.common.core.Constants;
import modularmachines.common.core.managers.ModItems;
import modularmachines.common.utils.Translator;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.page.PageJsonRecipe;
import amerifrance.guideapi.page.PageText;

@GuideBook(priority = EventPriority.HIGHEST)
public class ModularMachinesGuideAPIPlugin implements IGuideBook {
	private static final Book BOOK = new Book();
	
	@Override
	public Book buildBook() {
		BOOK.setTitle("guide.modularmachines.title");
		BOOK.setDisplayName("guide.modularmachines.display");
		BOOK.setWelcomeMessage("guide.modularmachines.welcome");
		BOOK.setAuthor("guide.modularmachines.author");
		BOOK.setRegistryName(new ResourceLocation(Constants.MOD_ID, "guide"));
		BOOK.setColor(Color.BLUE);
		addModuleCategory();
		return BOOK;
	}
	
	private void addModuleCategory() {
		final String keyBase = "guide." + Constants.MOD_ID + ".entry.basic.";
		CategoryAbstract category = new CategoryItemStack(keyBase + "tile", new ItemStack(ModItems.itemCasings, 1, 0)).withKeyBase(Constants.MOD_ID);
		category.addEntry("intro", new Entry(keyBase + "intro", true));
		category.getEntry("intro").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "intro.info.0")));
		category.getEntry("intro").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "intro.info.1")));
		
		category.addEntry("casings", new Entry(keyBase + "casings", true));
		category.getEntry("casings").addPage(getCraftingPage("casing.bronze"));
		category.getEntry("casings").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "casings.info"), 370));
		category.getEntry("casings").addPage(getCraftingPage("casing.iron"));
		category.getEntry("casings").addPage(getCraftingPage("casing.steel"));
		category.getEntry("casings").addPage(getCraftingPage("casing.magmarium"));
		
		category.entries.values().forEach(e -> e.pageList.stream().filter(p -> p instanceof PageText).forEach(p -> ((PageText) p).setUnicodeFlag(true)));
		BOOK.addCategory(category);
	}
	
	private static PageJsonRecipe getCraftingPage(String name) {
		return new PageJsonRecipe(new ResourceLocation(Constants.MOD_ID, name));
	}
	
	@Override
	public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
		return new ShapelessOreRecipe(null, GuideAPI.getStackFromBook(BOOK), new ItemStack(Items.BOOK), "blockGlass", "feather").setRegistryName("guide");
	}
}
