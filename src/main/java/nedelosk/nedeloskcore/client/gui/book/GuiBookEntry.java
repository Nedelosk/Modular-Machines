package nedelosk.nedeloskcore.client.gui.book;

import com.mojang.authlib.GameProfile;

import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.common.book.BookData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiBookEntry extends GuiBook {

	BookEntry entry;
	
	public GuiBookEntry(ResourceLocation guiTextures, GuiBook parent, BookEntry entry, BookData bookData, GameProfile player, World world) {
		super(guiTextures, bookData, player, world);
		this.parent = parent;
		this.entry = entry;
		this.pages = (entry.notes.length != 0) ? entry.notes.length : 0;
		
	}
	
	@Override
	public void drawScreen(int mx, int my, float arg2) {
		super.drawScreen(mx, my, arg2);
		
		entry.notes[page].renderScreen(this, mx, my);
	}
	
	public BookEntry getEntry() {
		return entry;
	}
	
	@Override
	public boolean isBasePage()
	{
		return false;
	}

}
