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
import modularmachines.common.items.ModuleItems;
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
		addProcessCategory();
		addIntegrationCategory();
		return BOOK;
	}
	
	private void addModuleCategory() {
		final String keyBase = "guide." + Constants.MOD_ID + ".entry.basic.";
		CategoryAbstract category = new CategoryItemStack(keyBase + "title", new ItemStack(ModItems.itemCasings, 1, 0)).withKeyBase(Constants.MOD_ID);
		
		category.addEntry("intro", new Entry(keyBase + "intro", true));
		category.getEntry("intro").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "intro.info.0")));
		category.getEntry("intro").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "intro.info.1")));
		
		category.addEntry("casings", new Entry(keyBase + "casings", true));
		category.getEntry("casings").addPage(getCraftingPage("casing.bronze"));
		category.getEntry("casings").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "casings.info"), 370));
		category.getEntry("casings").addPage(getCraftingPage("casing.iron"));
		category.getEntry("casings").addPage(getCraftingPage("casing.steel"));
		
		category.addEntry("module_racks", new Entry(keyBase + "module_racks", true));
		category.getEntry("module_racks").addPage(getCraftingPage("module_rack.bricks"));
		category.getEntry("module_racks").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "module_racks.info"), 370));
		category.getEntry("module_racks").addPage(getCraftingPage("module_rack.bronze"));
		category.getEntry("module_racks").addPage(getCraftingPage("module_rack.iron"));
		category.getEntry("module_racks").addPage(getCraftingPage("module_rack.steel"));
		
		category.addEntry("io_system", new Entry(keyBase + "io_system", true));
		category.getEntry("io_system").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "io_system.info.0"), 370));
		category.getEntry("io_system").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "io_system.info.1"), 370));
		category.getEntry("io_system").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "io_system.info.2"), 370));
		
		category.addEntry("water_intake", new Entry(keyBase + "water_intake", true));
		category.getEntry("water_intake").addPage(getCraftingPage("water_intake"));
		category.getEntry("water_intake").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "water_intake.info"), 370));
		
		category.entries.values().forEach(e -> e.pageList.stream().filter(p -> p instanceof PageText).forEach(p -> ((PageText) p).setUnicodeFlag(true)));
		BOOK.addCategory(category);
	}
	
	private void addProcessCategory() {
		final String keyBase = "guide." + Constants.MOD_ID + ".entry.process.";
		CategoryAbstract category = new CategoryItemStack(keyBase + "title", ModuleItems.FIREBOX.get()).withKeyBase(Constants.MOD_ID);
		
		category.addEntry("intro", new Entry(keyBase + "intro", true));
		category.getEntry("intro").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "intro.info")));
		
		category.addEntry("firebox", new Entry(keyBase + "firebox", true));
		//	category.getEntry("boiler").addPage(getCraftingPage("boiler"));
		category.getEntry("firebox").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "firebox.info"), 370));
		
		category.addEntry("boiler", new Entry(keyBase + "boiler", true));
		//	category.getEntry("boiler").addPage(getCraftingPage("boiler"));
		category.getEntry("boiler").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "boiler.info"), 370));
		
		category.addEntry("engine_steam", new Entry(keyBase + "engine_steam", true));
		category.getEntry("engine_steam").addPage(getCraftingPage("engine.steam.bronze"));
		category.getEntry("engine_steam").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "engine_steam.info"), 370));
		category.getEntry("engine_steam").addPage(getCraftingPage("engine.steam.iron"));
		category.getEntry("engine_steam").addPage(getCraftingPage("engine.steam.steel"));
		
		category.entries.values().forEach(e -> e.pageList.stream().filter(p -> p instanceof PageText).forEach(p -> ((PageText) p).setUnicodeFlag(true)));
		BOOK.addCategory(category);
	}
	
	private void addIntegrationCategory() {
		final String keyBase = "guide." + Constants.MOD_ID + ".entry.integration.";
		CategoryAbstract category = new CategoryItemStack(keyBase + "title", new ItemStack(ModItems.screwdriver, 1, 0)).withKeyBase(Constants.MOD_ID);
		
		category.addEntry("intro", new Entry(keyBase + "intro", true));
		category.getEntry("intro").addPageList(PageHelper.pagesForLongText(Translator.translateToLocal(keyBase + "intro.info")));
		
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
