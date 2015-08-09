package nedelosk.nedeloskcore.common.book.note;

import java.util.ArrayList;
import java.util.List;

import nedelosk.nedeloskcore.client.gui.book.GuiBook;
import nedelosk.nedeloskcore.common.book.BookData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NoteText extends Note {

	public NoteText(String unlocalizedName, int id, BookData data) {
		super(unlocalizedName, id, data);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderScreen(GuiBook gui, int mx, int my) {
		int width = gui.getGuiWidth() - 30;
		int x = gui.getLeft() + 16;
		int y = gui.getTop() + 2;

		renderText(x, y, width, gui.getGuiHeight(), getUnlocalizedName());
	}

	@SideOnly(Side.CLIENT)
	public static void renderText(int x, int y, int width, int height, String unlocalizedText) {
		x += 2;
		y += 10;
		width -= 4;

		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		boolean unicode = font.getUnicodeFlag();
		font.setUnicodeFlag(true);
		String text =  StatCollector.translateToLocal(unlocalizedText).replaceAll("&", "\u00a7");
		String[] textEntries = text.split("<br>");

		List<List<String>> lines = new ArrayList();

		String controlCodes = "";
		for(String s : textEntries) {
			List<String> words = new ArrayList();
			String lineStr = "";
			String[] tokens = s.split(" ");
			for(String token : tokens) {
				String prev = lineStr;
				String spaced = token + " ";
				lineStr += spaced;
				if(font.getStringWidth(lineStr) > width) {
					lines.add(words);
					lineStr = spaced;
					words = new ArrayList();
				}
				controlCodes = getControlCodes(prev);
				words.add(toControlCodes(controlCodes) + token);
			}

			if(!lineStr.isEmpty())
				lines.add(words);
			lines.add(new ArrayList());
		}

		int i = 0;
		for(List<String> words : lines) {
			words.size();
			int xi = x;
			int spacing = 4;
			int wcount = words.size();
			int compensationSpaces = 0;

			for(String s : words) {
				int extra = 0;
				if(compensationSpaces > 0) {
					compensationSpaces--;
					extra++;
				}
				font.drawString(s, xi, y, 0);
				xi += font.getStringWidth(s) + spacing + extra;
			}

			y += 10;
			i++;
		}

		font.setUnicodeFlag(unicode);
	}

	public static String getControlCodes(String s) {
		String controls = s.replaceAll("(?<!\u00a7)(.)", "");
		String wiped = controls.replaceAll(".*r", "r");
		return wiped;
	}

	public static String toControlCodes(String s) {
		return s.replaceAll(".", "\u00a7$0");
	}

}
