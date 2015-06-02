package nedelosk.nedeloskcore.client.gui.book;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mojang.authlib.GameProfile;

import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.api.book.Knowledge;
import nedelosk.nedeloskcore.client.gui.book.button.GuiButtonBookEntry;
import nedelosk.nedeloskcore.client.gui.book.button.GuiButtonBookmark;
import nedelosk.nedeloskcore.common.book.BookData;
import nedelosk.nedeloskcore.common.book.BookManager;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiBookIndex extends GuiBook {
	
	List<BookEntry> entriesToDisplay = new ArrayList();
	String category;
	
	public GuiBookIndex(ResourceLocation guiTextures, GuiBook parent, String category, BookData bookData, GameProfile player, World world) {
		super(guiTextures, bookData, player, world);
		this.category = category;
		this.parent = parent;
		this.pages = 1 + (bookData.getCategory(category).entrys.size() / 12);
	}
	
	protected void buildEntries() {
		entriesToDisplay.clear();
		if(bookData.getCategory(category) != null && bookData.getCategory(category).entrys != null)
		{
			Collection c = bookData.getCategory(category).entrys.keySet();
			for (Object obj : c) {
				if (BookManager.isEntryUnlock(player.getName(), (String) obj)) {
					entriesToDisplay.add(bookData.getCategory(category).entrys.get((String) obj));
				}
			}
		}
	}
	
	@Override
	public void initGui() {
		super.initGui();
			
		
		buildEntries();
		
		updateIndex();
		
	}
	
	public void updateIndex()
	{
		
		for(int i = page * 12; i < (page + 1) * 12; i++) {
			GuiButtonBookEntry button = (GuiButtonBookEntry) buttonList.get(i - page * 12);
			BookEntry entry = i >= entriesToDisplay.size() ? null : entriesToDisplay.get(i);
			if(entry != null) {
				button.displayString = StatCollector.translateToLocal(entry.getUnlocalizedName());
				button.entry = entry;
			} 
			else
			{
				button.displayString = "";
			}

		}
	}
	
	@Override
	public void drawScreen(int arg0, int arg1, float arg2) {
		super.drawScreen(arg0, arg1, arg2);
		
		RenderUtils.glDrawScaledString(Minecraft.getMinecraft().fontRenderer,EnumChatFormatting.ITALIC + (StatCollector.translateToLocal(bookData.getCategoryName(category))), this.left + 30, this.top + 10, 0.8F, Color.gray.getRGB());
		
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		
		if(par1GuiButton instanceof GuiButtonBookmark)
		{
			if(parent instanceof GuiBookIndex)
			{
			mc.displayGuiScreen(new GuiBookIndex(bookGuiTextures, this, ((GuiButtonBookmark) par1GuiButton).category, this.bookData, player, world));
			}
		}
		if(par1GuiButton.id == 12)
		{
		page--;
		updateButton();
		}
		if(par1GuiButton.id == 13)
		{
		page++;
		updateButton();
		}
		if(par1GuiButton.id == 14)
		{
		mc.displayGuiScreen(parent);
		}
		if(par1GuiButton instanceof GuiButtonBookEntry)
		{
			int index = par1GuiButton.id + page * 12;
			openEntry(index);
		}
	}
	
	void openEntry(int index) {
		if(index >= entriesToDisplay.size())
			return;

		BookEntry entry = entriesToDisplay.get(index);
		for(Knowledge knowledge : entry.knowledges)
		{
			if(!BookManager.isKnowledgeUnlock(player.getName(), knowledge.unlocalizedName))
			{
				return;
			}
		}
		mc.displayGuiScreen(new GuiBookEntry(bookGuiTextures, this, entry, bookData, player, world));
	}
	
	@Override
	public boolean isIndex()
	{
		return true;
	}
	
	@Override
	public boolean isBasePage()
	{
		return false;
	}
	
}
