package nedelosk.nedeloskcore.client.gui.book.button;

import java.util.Arrays;

import nedelosk.nedeloskcore.api.book.BookCategory;
import nedelosk.nedeloskcore.client.gui.book.GuiBook;
import nedelosk.nedeloskcore.common.book.BookData;
import nedelosk.nedeloskcore.common.book.BookDatas;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiButtonBookmark extends GuiButtonBook {

	public String category;
	public GuiBook gui;
	public BookData data;
	public boolean right;

	public GuiButtonBookmark(int id, int x, int y, BookData data, String category, GuiBook gui, boolean right) {
		super(id, x, y, 21, 18, "");
		this.category = category;
		this.data = data;
		this.gui = gui;
		this.right = right;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		
		boolean inside = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;
		int i = (inside) ? 22 : 0;
		int I = (right) ? 0 : 18;
		int iI = (right) ? 0 : 13;
		GL11.glPushMatrix();
		//mc.renderEngine.bindTexture((inside) ? new ResourceLocation("nedeloskcore", "textures/gui/book/category/bookmark_active.png") : new ResourceLocation("nedeloskcore", "textures/gui/book/category/bookmark.png"));
		RenderUtils.bindTexture(gui.bookGuiTextures);
		RenderUtils.drawTexturedModalRect(xPosition, yPosition, zLevel * 2, 55 + i, 183 + I, 21, 18, 1F / 260F, 1F / 260F);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		mc.renderEngine.bindTexture(data.getCategory(category).getIcon());
		RenderUtils.drawTexturedModalRect(xPosition * 2 + 5 + iI, yPosition * 2 + 10, zLevel * 2, 0, 0, 16, 16, 1F / 16F, 1F / 16F);
		GL11.glPopMatrix();
		
		if(inside)
			RenderUtils.renderTooltip(mx, my, Arrays.asList(StatCollector.translateToLocal(getTooltipText())));
	}

	String getTooltipText() {
		return data.getCategoryName(category);
	}

	public BookCategory getCategory() {
		return data.getCategory(category);
	}

}
