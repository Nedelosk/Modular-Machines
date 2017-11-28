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

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;

@GuideBook(priority = EventPriority.HIGHEST)
public class ModularMachinesGuideAPIPlugin implements IGuideBook {
	public static final Book BOOK = new Book();
	
	@Override
	public Book buildBook() {
		BOOK.setTitle("guide.modularmachines.title");
		BOOK.setDisplayName("guide.modularmachines.display");
		BOOK.setWelcomeMessage("guide.modularmachines.welcome");
		BOOK.setAuthor("guide.modularmachines.author");
		BOOK.setRegistryName(new ResourceLocation(Constants.MOD_ID, "guide"));
		BOOK.setColor(Color.BLUE);
		return BOOK;
	}
	
	@Override
	public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
		return new ShapelessOreRecipe(new ResourceLocation(Constants.MOD_ID, "guide"), GuideAPI.getStackFromBook(BOOK), new ItemStack(Items.BOOK), "blockGlass", "feather").setRegistryName("guide");
	}
}
