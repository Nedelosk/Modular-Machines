package nedelosk.nedeloskcore.common.book.note;

import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.client.gui.book.GuiBook;
import nedelosk.nedeloskcore.common.book.BookData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class Note {

	public String unlocalizedName;
	public int id;
	public BookData bookData;

	public Note(String unlocalizedName, int id, BookData bookData) {
		this.unlocalizedName = unlocalizedName;
		this.id = id;
		this.bookData = bookData;
	}

	@SideOnly(Side.CLIENT)
	public abstract void renderScreen(GuiBook gui, int mx, int my);

	@SideOnly(Side.CLIENT)
	public void updateScreen() {
	}

	@SideOnly(Side.CLIENT)
	public void updateScreen(BookEntry gui) {
		updateScreen();
	}
	
	public void onNoteAdded(BookEntry entry, int index) {
		
	}

	public String getUnlocalizedName() {
		return "nc.book." + bookData.tag + "." + unlocalizedName + ".note." + id;
	}
	
}
