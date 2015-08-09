package nedelosk.nedeloskcore.client.gui.book;

import java.util.Collection;

import nedelosk.nedeloskcore.api.book.BookCategory;
import nedelosk.nedeloskcore.client.gui.book.button.GuiButtonBookBack;
import nedelosk.nedeloskcore.client.gui.book.button.GuiButtonBookEntry;
import nedelosk.nedeloskcore.client.gui.book.button.GuiButtonBookPage;
import nedelosk.nedeloskcore.client.gui.book.button.GuiButtonBookmark;
import nedelosk.nedeloskcore.common.book.BookData;
import nedelosk.nedeloskcore.common.book.note.NoteText;
import nedelosk.nedeloskcore.common.core.NedeloskCore;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

public class GuiBook extends GuiScreen {
	
    public ResourceLocation bookGuiTextures;
    public BookData bookData;
    protected int page = 0;
    protected int pages = 1;
    protected GuiBook parent;
    protected GameProfile player = null;
    protected World world;
    protected boolean guiRender;
    
    protected GuiButton pageLeft, pageRight, pageBack;
	
	public GuiBook(ResourceLocation guiTextures, BookData data, GameProfile player, World world) {
		bookGuiTextures = guiTextures;
		this.bookData = data;
		this.player = player;
		this.world = world;
		guiRender = true;
	}
	
	protected int guiWidth = 146;
	protected int guiHeight = 190;
	protected int left;
	protected int top;
    
    @Override
    public void initGui() {
    	super.initGui();
    	
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
		if(isIndex())
		{
		int x = 18;
		for(int i = 0; i < 12; i++) {
			int y = 16 + i * 12;
			buttonList.add(new GuiButtonBookEntry(i, left + x, top + y, 110, 10, "", player.getName()));
		}
		}
		
		buttonList.add(pageLeft = new GuiButtonBookPage(12, left, top + guiHeight - 7, false, this));
		buttonList.add(pageRight = new GuiButtonBookPage(13, left + guiWidth - 18, top + guiHeight - 7, true, this));
		buttonList.add(pageBack = new GuiButtonBookBack(14, left + guiWidth / 2 - 8, top + guiHeight + 2, this));
		
		int id = 0;
		Collection cat = bookData.map.keySet();
		for(Object obj : cat)
		{
			for(BookCategory category : bookData.map.values())
			{
				if(NedeloskCore.proxy.getPlayerData().getEntrys(player.getName()) != null && NedeloskCore.proxy.getPlayerData().getEntrys(player.getName()).get(0) != null)
				{
				for(String entry : NedeloskCore.proxy.getPlayerData().getEntrys(player.getName()))
				{
					if(category.entrys.containsKey(entry))
					{
						if(id >=16)
						{
							break;
						}
						if(id < 8)
						{
							buttonList.add(new GuiButtonBookmark(15 + id, left - 20,  top + 15 + (20 * id), bookData, (String)obj, this, false));
						}
						else if(id < 16)
						{
							buttonList.add(new GuiButtonBookmark(15 + id, left + 145,  top + 15 + (20 * id), bookData, (String)obj, this, true));
						}
						id++;
						break;
					}
				}
				}
			}
		}
		
		updateButton();
    }
    
	@Override
	public void drawScreen(int arg0, int arg1, float arg2) {
		
		if(guiRender)
		{
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(bookGuiTextures);
        this.drawTexturedModalRect(left, top, 0, 0, 146, 180);
		}
        
		super.drawScreen(arg0, arg1, arg2);
		
        if(isBasePage())
        {
        	new NoteText("base.text", 0, this.bookData).renderScreen(this, arg0, arg1);
        }
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton instanceof GuiButtonBookmark)
		{
			mc.displayGuiScreen(new GuiBookIndex(bookGuiTextures, this, ((GuiButtonBookmark) par1GuiButton).category, this.bookData, player, world));
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
	}
	
	public boolean isIndex()
	{
		return false;
	}
	
	public void updateButton()
	{
		pageLeft.enabled = page != 0;
		pageRight.enabled = page + 1  != pages;
		pageBack.enabled = parent != null;
	}
	
	public int getGuiHeight() {
		return guiHeight;
	}
	
	public int getGuiWidth() {
		return guiWidth;
	}
	
	public int getTop() {
		return top;
	}
	
	public int getLeft() {
		return left;
	}
	
	public boolean isBasePage()
	{
		return true;
	}
	
}
