package nedelosk.nedeloskcore.client.gui.book.button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.api.book.Knowledge;
import nedelosk.nedeloskcore.client.gui.book.GuiBook;
import nedelosk.nedeloskcore.common.book.BookManager;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiButtonBookEntry extends GuiButtonBook {

	public BookEntry entry;
	public String playerName;

	public GuiButtonBookEntry(int par1, int par2, int par3, int par4, int par5,
			String par6Str, String playerName) {
		super(par1, par2, par3, par4, par5, par6Str);
		this.playerName = playerName;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int mx, int my) {

		boolean inside = mx >= xPosition && my >= yPosition
				&& mx < xPosition + width && my < yPosition + height;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glDisable(GL11.GL_ALPHA_TEST);

		GL11.glEnable(GL11.GL_ALPHA_TEST);

		boolean unicode = par1Minecraft.fontRenderer.getUnicodeFlag();
		par1Minecraft.fontRenderer.setUnicodeFlag(true);
		par1Minecraft.fontRenderer.drawString(displayString, xPosition,
				yPosition + (height - 8) / 2, 0);
		par1Minecraft.fontRenderer.setUnicodeFlag(unicode);

		GL11.glPopMatrix();

		if (entry != null) {
			ArrayList tooltip = new ArrayList();

			tooltip.add( entry.level.color + displayString);
			tooltip.add( EnumChatFormatting.GRAY + StatCollector.translateToLocal("nc.level."
							+ entry.level.unlocalizedName));
			int tooltipY = (tooltip.size() - 1) * 10;
			for(Knowledge knowledge : entry.knowledges)
			{
				boolean isUnlock = BookManager.isKnowledgeUnlock(playerName, knowledge.getUnlocalizedName());
				tooltip.add((isUnlock) ? EnumChatFormatting.GREEN : EnumChatFormatting.GREEN + StatCollector.translateToLocal(knowledge.getUnlocalizedName()));
			}

			if (inside)
				RenderUtils.renderTooltip(mx, my + tooltipY, tooltip);
		}
	}

}
