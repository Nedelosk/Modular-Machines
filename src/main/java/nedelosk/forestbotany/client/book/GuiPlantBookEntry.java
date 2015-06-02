package nedelosk.forestbotany.client.book;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestbotany.api.botany.book.BookPlantEntry;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantCrop;
import nedelosk.forestbotany.client.renderers.items.ItemSeedRenderer;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.templates.crop.CropChromosome;
import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.client.gui.book.GuiBook;
import nedelosk.nedeloskcore.client.gui.book.GuiBookEntry;
import nedelosk.nedeloskcore.common.book.BookData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

public class GuiPlantBookEntry extends GuiPlantBook {

	BookPlantEntry entry;
	
	public GuiPlantBookEntry(GuiBook parent, BookPlantEntry entry, BookData bookData, GameProfile player, World world) {
		super(bookData, player, world);
		this.parent = parent;
		this.entry = entry;
        NodeList nList = entry.doc.getElementsByTagName("Specie");

        Node node = nList.item(((IAllelePlant)entry.plant.getGenome().getActiveAllele(CropChromosome.PLANT)).getDefinitionID());
        
		this.pages = ((Element) node).getElementsByTagName("Page").getLength();
		
	}
	
	@Override
	public void drawScreen(int mx, int my, float arg2) {
		
		super.drawScreen(mx, my, arg2);
		
		int width = getGuiWidth() - 30;
		int x = getLeft() - 18;
		int y = getTop() + 2;
		
		
        NodeList nList = entry.doc.getElementsByTagName("Specie");

        Node node = nList.item(((IAllelePlant)entry.plant.getGenome().getActiveAllele(CropChromosome.PLANT)).getDefinitionID());
        
        Node nodeText = ((Element) node).getElementsByTagName("Page").item(page);
        
		renderText(x, y, width, getGuiHeight(), ((Element) node).getElementsByTagName("Text").item(0).getTextContent());
		
		//ForgeHooksClient.renderInventoryItem(null, Minecraft.getMinecraft().renderEngine, PlantManager.cropManager.getMemberStack(entry.plant,0), false, zLevel +3.0F, (float) width + 173, (float)getGuiHeight() + -120);
	}
	
	public BookPlantEntry getEntry() {
		return entry;
	}
	
	@Override
	public boolean isBasePage()
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public static void renderText(int x, int y, int width, int height, String unlocalizedText) {
		x += 2;
		y += 10;
		width -= 4;

		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		boolean unicode = font.getUnicodeFlag();
		font.setUnicodeFlag(true);
		String text =  unlocalizedText;
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
